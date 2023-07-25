import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILoanAccount } from '../loan-account.model';

@Component({
  selector: 'jhi-loan-account-detail',
  templateUrl: './loan-account-detail.component.html',
})
export class LoanAccountDetailComponent implements OnInit {
  loanAccount: ILoanAccount | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ loanAccount }) => {
      this.loanAccount = loanAccount;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
