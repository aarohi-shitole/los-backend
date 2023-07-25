import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILedgerAccounts } from '../ledger-accounts.model';

@Component({
  selector: 'jhi-ledger-accounts-detail',
  templateUrl: './ledger-accounts-detail.component.html',
})
export class LedgerAccountsDetailComponent implements OnInit {
  ledgerAccounts: ILedgerAccounts | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ledgerAccounts }) => {
      this.ledgerAccounts = ledgerAccounts;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
