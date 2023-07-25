import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { AmortizationDetailsFormService, AmortizationDetailsFormGroup } from './amortization-details-form.service';
import { IAmortizationDetails } from '../amortization-details.model';
import { AmortizationDetailsService } from '../service/amortization-details.service';
import { ILoanAccount } from 'app/entities/loan-account/loan-account.model';
import { LoanAccountService } from 'app/entities/loan-account/service/loan-account.service';

@Component({
  selector: 'jhi-amortization-details-update',
  templateUrl: './amortization-details-update.component.html',
})
export class AmortizationDetailsUpdateComponent implements OnInit {
  isSaving = false;
  amortizationDetails: IAmortizationDetails | null = null;

  loanAccountsSharedCollection: ILoanAccount[] = [];

  editForm: AmortizationDetailsFormGroup = this.amortizationDetailsFormService.createAmortizationDetailsFormGroup();

  constructor(
    protected amortizationDetailsService: AmortizationDetailsService,
    protected amortizationDetailsFormService: AmortizationDetailsFormService,
    protected loanAccountService: LoanAccountService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareLoanAccount = (o1: ILoanAccount | null, o2: ILoanAccount | null): boolean => this.loanAccountService.compareLoanAccount(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ amortizationDetails }) => {
      this.amortizationDetails = amortizationDetails;
      if (amortizationDetails) {
        this.updateForm(amortizationDetails);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const amortizationDetails = this.amortizationDetailsFormService.getAmortizationDetails(this.editForm);
    if (amortizationDetails.id !== null) {
      this.subscribeToSaveResponse(this.amortizationDetailsService.update(amortizationDetails));
    } else {
      this.subscribeToSaveResponse(this.amortizationDetailsService.create(amortizationDetails));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAmortizationDetails>>): void {
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

  protected updateForm(amortizationDetails: IAmortizationDetails): void {
    this.amortizationDetails = amortizationDetails;
    this.amortizationDetailsFormService.resetForm(this.editForm, amortizationDetails);

    this.loanAccountsSharedCollection = this.loanAccountService.addLoanAccountToCollectionIfMissing<ILoanAccount>(
      this.loanAccountsSharedCollection,
      amortizationDetails.loanAccount
    );
  }

  protected loadRelationshipsOptions(): void {
    this.loanAccountService
      .query()
      .pipe(map((res: HttpResponse<ILoanAccount[]>) => res.body ?? []))
      .pipe(
        map((loanAccounts: ILoanAccount[]) =>
          this.loanAccountService.addLoanAccountToCollectionIfMissing<ILoanAccount>(loanAccounts, this.amortizationDetails?.loanAccount)
        )
      )
      .subscribe((loanAccounts: ILoanAccount[]) => (this.loanAccountsSharedCollection = loanAccounts));
  }
}
