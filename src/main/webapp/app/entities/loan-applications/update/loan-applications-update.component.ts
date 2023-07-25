import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { LoanApplicationsFormService, LoanApplicationsFormGroup } from './loan-applications-form.service';
import { ILoanApplications } from '../loan-applications.model';
import { LoanApplicationsService } from '../service/loan-applications.service';
import { IMember } from 'app/entities/member/member.model';
import { MemberService } from 'app/entities/member/service/member.service';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { SecurityUserService } from 'app/entities/security-user/service/security-user.service';
import { IProduct } from 'app/entities/product/product.model';
import { ProductService } from 'app/entities/product/service/product.service';
import { LoanStatus } from 'app/entities/enumerations/loan-status.model';
import { StepperNumber } from 'app/entities/enumerations/stepper-number.model';

@Component({
  selector: 'jhi-loan-applications-update',
  templateUrl: './loan-applications-update.component.html',
})
export class LoanApplicationsUpdateComponent implements OnInit {
  isSaving = false;
  loanApplications: ILoanApplications | null = null;
  loanStatusValues = Object.keys(LoanStatus);
  stepperNumberValues = Object.keys(StepperNumber);

  membersSharedCollection: IMember[] = [];
  securityUsersSharedCollection: ISecurityUser[] = [];
  productsSharedCollection: IProduct[] = [];

  editForm: LoanApplicationsFormGroup = this.loanApplicationsFormService.createLoanApplicationsFormGroup();

  constructor(
    protected loanApplicationsService: LoanApplicationsService,
    protected loanApplicationsFormService: LoanApplicationsFormService,
    protected memberService: MemberService,
    protected securityUserService: SecurityUserService,
    protected productService: ProductService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareMember = (o1: IMember | null, o2: IMember | null): boolean => this.memberService.compareMember(o1, o2);

  compareSecurityUser = (o1: ISecurityUser | null, o2: ISecurityUser | null): boolean =>
    this.securityUserService.compareSecurityUser(o1, o2);

  compareProduct = (o1: IProduct | null, o2: IProduct | null): boolean => this.productService.compareProduct(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ loanApplications }) => {
      this.loanApplications = loanApplications;
      if (loanApplications) {
        this.updateForm(loanApplications);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const loanApplications = this.loanApplicationsFormService.getLoanApplications(this.editForm);
    if (loanApplications.id !== null) {
      this.subscribeToSaveResponse(this.loanApplicationsService.update(loanApplications));
    } else {
      this.subscribeToSaveResponse(this.loanApplicationsService.create(loanApplications));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILoanApplications>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(loanApplications: ILoanApplications): void {
    this.loanApplications = loanApplications;
    this.loanApplicationsFormService.resetForm(this.editForm, loanApplications);

    this.membersSharedCollection = this.memberService.addMemberToCollectionIfMissing<IMember>(
      this.membersSharedCollection,
      loanApplications.member
    );
    this.securityUsersSharedCollection = this.securityUserService.addSecurityUserToCollectionIfMissing<ISecurityUser>(
      this.securityUsersSharedCollection,
      loanApplications.securityUser
    );
    this.productsSharedCollection = this.productService.addProductToCollectionIfMissing<IProduct>(
      this.productsSharedCollection,
      loanApplications.product
    );
  }

  protected loadRelationshipsOptions(): void {
    this.memberService
      .query()
      .pipe(map((res: HttpResponse<IMember[]>) => res.body ?? []))
      .pipe(map((members: IMember[]) => this.memberService.addMemberToCollectionIfMissing<IMember>(members, this.loanApplications?.member)))
      .subscribe((members: IMember[]) => (this.membersSharedCollection = members));

    this.securityUserService
      .query()
      .pipe(map((res: HttpResponse<ISecurityUser[]>) => res.body ?? []))
      .pipe(
        map((securityUsers: ISecurityUser[]) =>
          this.securityUserService.addSecurityUserToCollectionIfMissing<ISecurityUser>(securityUsers, this.loanApplications?.securityUser)
        )
      )
      .subscribe((securityUsers: ISecurityUser[]) => (this.securityUsersSharedCollection = securityUsers));

    this.productService
      .query()
      .pipe(map((res: HttpResponse<IProduct[]>) => res.body ?? []))
      .pipe(
        map((products: IProduct[]) =>
          this.productService.addProductToCollectionIfMissing<IProduct>(products, this.loanApplications?.product)
        )
      )
      .subscribe((products: IProduct[]) => (this.productsSharedCollection = products));
  }
}
