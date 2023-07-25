import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { LoanDisbursementFormService, LoanDisbursementFormGroup } from './loan-disbursement-form.service';
import { ILoanDisbursement } from '../loan-disbursement.model';
import { LoanDisbursementService } from '../service/loan-disbursement.service';
import { IProduct } from 'app/entities/product/product.model';
import { ProductService } from 'app/entities/product/service/product.service';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { SecurityUserService } from 'app/entities/security-user/service/security-user.service';
import { ILoanAccount } from 'app/entities/loan-account/loan-account.model';
import { LoanAccountService } from 'app/entities/loan-account/service/loan-account.service';
import { PaymentMode } from 'app/entities/enumerations/payment-mode.model';

@Component({
  selector: 'jhi-loan-disbursement-update',
  templateUrl: './loan-disbursement-update.component.html',
})
export class LoanDisbursementUpdateComponent implements OnInit {
  isSaving = false;
  loanDisbursement: ILoanDisbursement | null = null;
  paymentModeValues = Object.keys(PaymentMode);

  productsSharedCollection: IProduct[] = [];
  securityUsersSharedCollection: ISecurityUser[] = [];
  loanAccountsSharedCollection: ILoanAccount[] = [];

  editForm: LoanDisbursementFormGroup = this.loanDisbursementFormService.createLoanDisbursementFormGroup();

  constructor(
    protected loanDisbursementService: LoanDisbursementService,
    protected loanDisbursementFormService: LoanDisbursementFormService,
    protected productService: ProductService,
    protected securityUserService: SecurityUserService,
    protected loanAccountService: LoanAccountService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareProduct = (o1: IProduct | null, o2: IProduct | null): boolean => this.productService.compareProduct(o1, o2);

  compareSecurityUser = (o1: ISecurityUser | null, o2: ISecurityUser | null): boolean =>
    this.securityUserService.compareSecurityUser(o1, o2);

  compareLoanAccount = (o1: ILoanAccount | null, o2: ILoanAccount | null): boolean => this.loanAccountService.compareLoanAccount(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ loanDisbursement }) => {
      this.loanDisbursement = loanDisbursement;
      if (loanDisbursement) {
        this.updateForm(loanDisbursement);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const loanDisbursement = this.loanDisbursementFormService.getLoanDisbursement(this.editForm);
    if (loanDisbursement.id !== null) {
      this.subscribeToSaveResponse(this.loanDisbursementService.update(loanDisbursement));
    } else {
      this.subscribeToSaveResponse(this.loanDisbursementService.create(loanDisbursement));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILoanDisbursement>>): void {
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

  protected updateForm(loanDisbursement: ILoanDisbursement): void {
    this.loanDisbursement = loanDisbursement;
    this.loanDisbursementFormService.resetForm(this.editForm, loanDisbursement);

    this.productsSharedCollection = this.productService.addProductToCollectionIfMissing<IProduct>(
      this.productsSharedCollection,
      loanDisbursement.product
    );
    this.securityUsersSharedCollection = this.securityUserService.addSecurityUserToCollectionIfMissing<ISecurityUser>(
      this.securityUsersSharedCollection,
      loanDisbursement.securityUser
    );
    this.loanAccountsSharedCollection = this.loanAccountService.addLoanAccountToCollectionIfMissing<ILoanAccount>(
      this.loanAccountsSharedCollection,
      loanDisbursement.loanAccount
    );
  }

  protected loadRelationshipsOptions(): void {
    this.productService
      .query()
      .pipe(map((res: HttpResponse<IProduct[]>) => res.body ?? []))
      .pipe(
        map((products: IProduct[]) =>
          this.productService.addProductToCollectionIfMissing<IProduct>(products, this.loanDisbursement?.product)
        )
      )
      .subscribe((products: IProduct[]) => (this.productsSharedCollection = products));

    this.securityUserService
      .query()
      .pipe(map((res: HttpResponse<ISecurityUser[]>) => res.body ?? []))
      .pipe(
        map((securityUsers: ISecurityUser[]) =>
          this.securityUserService.addSecurityUserToCollectionIfMissing<ISecurityUser>(securityUsers, this.loanDisbursement?.securityUser)
        )
      )
      .subscribe((securityUsers: ISecurityUser[]) => (this.securityUsersSharedCollection = securityUsers));

    this.loanAccountService
      .query()
      .pipe(map((res: HttpResponse<ILoanAccount[]>) => res.body ?? []))
      .pipe(
        map((loanAccounts: ILoanAccount[]) =>
          this.loanAccountService.addLoanAccountToCollectionIfMissing<ILoanAccount>(loanAccounts, this.loanDisbursement?.loanAccount)
        )
      )
      .subscribe((loanAccounts: ILoanAccount[]) => (this.loanAccountsSharedCollection = loanAccounts));
  }
}
