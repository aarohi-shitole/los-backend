import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILoanCatagory } from '../loan-catagory.model';

@Component({
  selector: 'jhi-loan-catagory-detail',
  templateUrl: './loan-catagory-detail.component.html',
})
export class LoanCatagoryDetailComponent implements OnInit {
  loanCatagory: ILoanCatagory | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ loanCatagory }) => {
      this.loanCatagory = loanCatagory;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
