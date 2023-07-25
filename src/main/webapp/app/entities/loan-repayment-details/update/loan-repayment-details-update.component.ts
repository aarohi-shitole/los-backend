import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { LoanRepaymentDetailsFormService, LoanRepaymentDetailsFormGroup } from './loan-repayment-details-form.service';
import { ILoanRepaymentDetails } from '../loan-repayment-details.model';
import { LoanRepaymentDetailsService } from '../service/loan-repayment-details.service';
import { ILoanAccount } from 'app/entities/loan-account/loan-account.model';
import { LoanAccountService } from 'app/entities/loan-account/service/loan-account.service';
import { PaymentMode } from 'app/entities/enumerations/payment-mode.model';

@Component({
  selector: 'jhi-loan-repayment-details-update',
  templateUrl: './loan-repayment-details-update.component.html',
})
export class LoanRepaymentDetailsUpdateComponent implements OnInit {
  isSaving = false;
  loanRepaymentDetails: ILoanRepaymentDetails | null = null;
  paymentModeValues = Object.keys(PaymentMode);

  loanAccountsSharedCollection: ILoanAccount[] = [];

  editForm: LoanRepaymentDetailsFormGroup = this.loanRepaymentDetailsFormService.createLoanRepaymentDetailsFormGroup();

  constructor(
    protected loanRepaymentDetailsService: LoanRepaymentDetailsService,
    protected loanRepaymentDetailsFormService: LoanRepaymentDetailsFormService,
    protected loanAccountService: LoanAccountService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareLoanAccount = (o1: ILoanAccount | null, o2: ILoanAccount | null): boolean => this.loanAccountService.compareLoanAccount(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ loanRepaymentDetails }) => {
      this.loanRepaymentDetails = loanRepaymentDetails;
      if (loanRepaymentDetails) {
        this.updateForm(loanRepaymentDetails);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const loanRepaymentDetails = this.loanRepaymentDetailsFormService.getLoanRepaymentDetails(this.editForm);
    if (loanRepaymentDetails.id !== null) {
      this.subscribeToSaveResponse(this.loanRepaymentDetailsService.update(loanRepaymentDetails));
    } else {
      this.subscribeToSaveResponse(this.loanRepaymentDetailsService.create(loanRepaymentDetails));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILoanRepaymentDetails>>): void {
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

  protected updateForm(loanRepaymentDetails: ILoanRepaymentDetails): void {
    this.loanRepaymentDetails = loanRepaymentDetails;
    this.loanRepaymentDetailsFormService.resetForm(this.editForm, loanRepaymentDetails);

    this.loanAccountsSharedCollection = this.loanAccountService.addLoanAccountToCollectionIfMissing<ILoanAccount>(
      this.loanAccountsSharedCollection,
      loanRepaymentDetails.loanAccount
    );
  }

  protected loadRelationshipsOptions(): void {
    this.loanAccountService
      .query()
      .pipe(map((res: HttpResponse<ILoanAccount[]>) => res.body ?? []))
      .pipe(
        map((loanAccounts: ILoanAccount[]) =>
          this.loanAccountService.addLoanAccountToCollectionIfMissing<ILoanAccount>(loanAccounts, this.loanRepaymentDetails?.loanAccount)
        )
      )
      .subscribe((loanAccounts: ILoanAccount[]) => (this.loanAccountsSharedCollection = loanAccounts));
  }
}
