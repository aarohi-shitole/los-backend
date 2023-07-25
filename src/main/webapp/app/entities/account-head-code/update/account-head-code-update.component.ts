import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { AccountHeadCodeFormService, AccountHeadCodeFormGroup } from './account-head-code-form.service';
import { IAccountHeadCode } from '../account-head-code.model';
import { AccountHeadCodeService } from '../service/account-head-code.service';
import { ILedgerAccounts } from 'app/entities/ledger-accounts/ledger-accounts.model';
import { LedgerAccountsService } from 'app/entities/ledger-accounts/service/ledger-accounts.service';
import { MappingType } from 'app/entities/enumerations/mapping-type.model';

@Component({
  selector: 'jhi-account-head-code-update',
  templateUrl: './account-head-code-update.component.html',
})
export class AccountHeadCodeUpdateComponent implements OnInit {
  isSaving = false;
  accountHeadCode: IAccountHeadCode | null = null;
  mappingTypeValues = Object.keys(MappingType);

  ledgerAccountsSharedCollection: ILedgerAccounts[] = [];

  editForm: AccountHeadCodeFormGroup = this.accountHeadCodeFormService.createAccountHeadCodeFormGroup();

  constructor(
    protected accountHeadCodeService: AccountHeadCodeService,
    protected accountHeadCodeFormService: AccountHeadCodeFormService,
    protected ledgerAccountsService: LedgerAccountsService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareLedgerAccounts = (o1: ILedgerAccounts | null, o2: ILedgerAccounts | null): boolean =>
    this.ledgerAccountsService.compareLedgerAccounts(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accountHeadCode }) => {
      this.accountHeadCode = accountHeadCode;
      if (accountHeadCode) {
        this.updateForm(accountHeadCode);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const accountHeadCode = this.accountHeadCodeFormService.getAccountHeadCode(this.editForm);
    if (accountHeadCode.id !== null) {
      this.subscribeToSaveResponse(this.accountHeadCodeService.update(accountHeadCode));
    } else {
      this.subscribeToSaveResponse(this.accountHeadCodeService.create(accountHeadCode));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAccountHeadCode>>): void {
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

  protected updateForm(accountHeadCode: IAccountHeadCode): void {
    this.accountHeadCode = accountHeadCode;
    this.accountHeadCodeFormService.resetForm(this.editForm, accountHeadCode);

    this.ledgerAccountsSharedCollection = this.ledgerAccountsService.addLedgerAccountsToCollectionIfMissing<ILedgerAccounts>(
      this.ledgerAccountsSharedCollection,
      accountHeadCode.ledgerAccounts
    );
  }

  protected loadRelationshipsOptions(): void {
    this.ledgerAccountsService
      .query()
      .pipe(map((res: HttpResponse<ILedgerAccounts[]>) => res.body ?? []))
      .pipe(
        map((ledgerAccounts: ILedgerAccounts[]) =>
          this.ledgerAccountsService.addLedgerAccountsToCollectionIfMissing<ILedgerAccounts>(
            ledgerAccounts,
            this.accountHeadCode?.ledgerAccounts
          )
        )
      )
      .subscribe((ledgerAccounts: ILedgerAccounts[]) => (this.ledgerAccountsSharedCollection = ledgerAccounts));
  }
}
