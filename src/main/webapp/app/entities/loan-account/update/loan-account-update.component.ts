import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { LoanAccountFormService, LoanAccountFormGroup } from './loan-account-form.service';
import { ILoanAccount } from '../loan-account.model';
import { LoanAccountService } from '../service/loan-account.service';
import { ILoanApplications } from 'app/entities/loan-applications/loan-applications.model';
import { LoanApplicationsService } from 'app/entities/loan-applications/service/loan-applications.service';
import { IMember } from 'app/entities/member/member.model';
import { MemberService } from 'app/entities/member/service/member.service';
import { IProduct } from 'app/entities/product/product.model';
import { ProductService } from 'app/entities/product/service/product.service';
import { LoanStatus } from 'app/entities/enumerations/loan-status.model';
import { NpaClassification } from 'app/entities/enumerations/npa-classification.model';

@Component({
  selector: 'jhi-loan-account-update',
  templateUrl: './loan-account-update.component.html',
})
export class LoanAccountUpdateComponent implements OnInit {
  isSaving = false;
  loanAccount: ILoanAccount | null = null;
  loanStatusValues = Object.keys(LoanStatus);
  npaClassificationValues = Object.keys(NpaClassification);

  loanApplicationsSharedCollection: ILoanApplications[] = [];
  membersSharedCollection: IMember[] = [];
  productsSharedCollection: IProduct[] = [];

  editForm: LoanAccountFormGroup = this.loanAccountFormService.createLoanAccountFormGroup();

  constructor(
    protected loanAccountService: LoanAccountService,
    protected loanAccountFormService: LoanAccountFormService,
    protected loanApplicationsService: LoanApplicationsService,
    protected memberService: MemberService,
    protected productService: ProductService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareLoanApplications = (o1: ILoanApplications | null, o2: ILoanApplications | null): boolean =>
    this.loanApplicationsService.compareLoanApplications(o1, o2);

  compareMember = (o1: IMember | null, o2: IMember | null): boolean => this.memberService.compareMember(o1, o2);

  compareProduct = (o1: IProduct | null, o2: IProduct | null): boolean => this.productService.compareProduct(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ loanAccount }) => {
      this.loanAccount = loanAccount;
      if (loanAccount) {
        this.updateForm(loanAccount);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const loanAccount = this.loanAccountFormService.getLoanAccount(this.editForm);
    if (loanAccount.id !== null) {
      this.subscribeToSaveResponse(this.loanAccountService.update(loanAccount));
    } else {
      this.subscribeToSaveResponse(this.loanAccountService.create(loanAccount));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILoanAccount>>): void {
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

  protected updateForm(loanAccount: ILoanAccount): void {
    this.loanAccount = loanAccount;
    this.loanAccountFormService.resetForm(this.editForm, loanAccount);

    this.loanApplicationsSharedCollection = this.loanApplicationsService.addLoanApplicationsToCollectionIfMissing<ILoanApplications>(
      this.loanApplicationsSharedCollection,
      loanAccount.loanApplications
    );
    this.membersSharedCollection = this.memberService.addMemberToCollectionIfMissing<IMember>(
      this.membersSharedCollection,
      loanAccount.member
    );
    this.productsSharedCollection = this.productService.addProductToCollectionIfMissing<IProduct>(
      this.productsSharedCollection,
      loanAccount.product
    );
  }

  protected loadRelationshipsOptions(): void {
    this.loanApplicationsService
      .query()
      .pipe(map((res: HttpResponse<ILoanApplications[]>) => res.body ?? []))
      .pipe(
        map((loanApplications: ILoanApplications[]) =>
          this.loanApplicationsService.addLoanApplicationsToCollectionIfMissing<ILoanApplications>(
            loanApplications,
            this.loanAccount?.loanApplications
          )
        )
      )
      .subscribe((loanApplications: ILoanApplications[]) => (this.loanApplicationsSharedCollection = loanApplications));

    this.memberService
      .query()
      .pipe(map((res: HttpResponse<IMember[]>) => res.body ?? []))
      .pipe(map((members: IMember[]) => this.memberService.addMemberToCollectionIfMissing<IMember>(members, this.loanAccount?.member)))
      .subscribe((members: IMember[]) => (this.membersSharedCollection = members));

    this.productService
      .query()
      .pipe(map((res: HttpResponse<IProduct[]>) => res.body ?? []))
      .pipe(
        map((products: IProduct[]) => this.productService.addProductToCollectionIfMissing<IProduct>(products, this.loanAccount?.product))
      )
      .subscribe((products: IProduct[]) => (this.productsSharedCollection = products));
  }
}
