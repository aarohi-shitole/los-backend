import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILoanRepaymentDetails } from '../loan-repayment-details.model';

@Component({
  selector: 'jhi-loan-repayment-details-detail',
  templateUrl: './loan-repayment-details-detail.component.html',
})
export class LoanRepaymentDetailsDetailComponent implements OnInit {
  loanRepaymentDetails: ILoanRepaymentDetails | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ loanRepaymentDetails }) => {
      this.loanRepaymentDetails = loanRepaymentDetails;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
