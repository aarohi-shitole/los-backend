import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILoanApplications } from '../loan-applications.model';

@Component({
  selector: 'jhi-loan-applications-detail',
  templateUrl: './loan-applications-detail.component.html',
})
export class LoanApplicationsDetailComponent implements OnInit {
  loanApplications: ILoanApplications | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ loanApplications }) => {
      this.loanApplications = loanApplications;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
