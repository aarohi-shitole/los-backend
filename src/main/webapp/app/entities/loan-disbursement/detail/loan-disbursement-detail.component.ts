import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILoanDisbursement } from '../loan-disbursement.model';

@Component({
  selector: 'jhi-loan-disbursement-detail',
  templateUrl: './loan-disbursement-detail.component.html',
})
export class LoanDisbursementDetailComponent implements OnInit {
  loanDisbursement: ILoanDisbursement | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ loanDisbursement }) => {
      this.loanDisbursement = loanDisbursement;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
