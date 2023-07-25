import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { LedgerAccountsFormService, LedgerAccountsFormGroup } from './ledger-accounts-form.service';
import { ILedgerAccounts } from '../ledger-accounts.model';
import { LedgerAccountsService } from '../service/ledger-accounts.service';
import { IBranch } from 'app/entities/branch/branch.model';
import { BranchService } from 'app/entities/branch/service/branch.service';
import { LedgerClassification } from 'app/entities/enumerations/ledger-classification.model';

@Component({
  selector: 'jhi-ledger-accounts-update',
  templateUrl: './ledger-accounts-update.component.html',
})
export class LedgerAccountsUpdateComponent implements OnInit {
  isSaving = false;
  ledgerAccounts: ILedgerAccounts | null = null;
  ledgerClassificationValues = Object.keys(LedgerClassification);

  branchesSharedCollection: IBranch[] = [];
  ledgerAccountsSharedCollection: ILedgerAccounts[] = [];

  editForm: LedgerAccountsFormGroup = this.ledgerAccountsFormService.createLedgerAccountsFormGroup();

  constructor(
    protected ledgerAccountsService: LedgerAccountsService,
    protected ledgerAccountsFormService: LedgerAccountsFormService,
    protected branchService: BranchService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareBranch = (o1: IBranch | null, o2: IBranch | null): boolean => this.branchService.compareBranch(o1, o2);

  compareLedgerAccounts = (o1: ILedgerAccounts | null, o2: ILedgerAccounts | null): boolean =>
    this.ledgerAccountsService.compareLedgerAccounts(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ledgerAccounts }) => {
      this.ledgerAccounts = ledgerAccounts;
      if (ledgerAccounts) {
        this.updateForm(ledgerAccounts);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ledgerAccounts = this.ledgerAccountsFormService.getLedgerAccounts(this.editForm);
    if (ledgerAccounts.id !== null) {
      this.subscribeToSaveResponse(this.ledgerAccountsService.update(ledgerAccounts));
    } else {
      this.subscribeToSaveResponse(this.ledgerAccountsService.create(ledgerAccounts));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILedgerAccounts>>): void {
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

  protected updateForm(ledgerAccounts: ILedgerAccounts): void {
    this.ledgerAccounts = ledgerAccounts;
    this.ledgerAccountsFormService.resetForm(this.editForm, ledgerAccounts);

    this.branchesSharedCollection = this.branchService.addBranchToCollectionIfMissing<IBranch>(
      this.branchesSharedCollection,
      ledgerAccounts.branch
    );
    this.ledgerAccountsSharedCollection = this.ledgerAccountsService.addLedgerAccountsToCollectionIfMissing<ILedgerAccounts>(
      this.ledgerAccountsSharedCollection,
      ledgerAccounts.ledgerAccounts
    );
  }

  protected loadRelationshipsOptions(): void {
    this.branchService
      .query()
      .pipe(map((res: HttpResponse<IBranch[]>) => res.body ?? []))
      .pipe(map((branches: IBranch[]) => this.branchService.addBranchToCollectionIfMissing<IBranch>(branches, this.ledgerAccounts?.branch)))
      .subscribe((branches: IBranch[]) => (this.branchesSharedCollection = branches));

    this.ledgerAccountsService
      .query()
      .pipe(map((res: HttpResponse<ILedgerAccounts[]>) => res.body ?? []))
      .pipe(
        map((ledgerAccounts: ILedgerAccounts[]) =>
          this.ledgerAccountsService.addLedgerAccountsToCollectionIfMissing<ILedgerAccounts>(
            ledgerAccounts,
            this.ledgerAccounts?.ledgerAccounts
          )
        )
      )
      .subscribe((ledgerAccounts: ILedgerAccounts[]) => (this.ledgerAccountsSharedCollection = ledgerAccounts));
  }
}
