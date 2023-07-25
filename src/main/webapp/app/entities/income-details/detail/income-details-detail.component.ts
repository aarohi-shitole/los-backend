import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIncomeDetails } from '../income-details.model';

@Component({
  selector: 'jhi-income-details-detail',
  templateUrl: './income-details-detail.component.html',
})
export class IncomeDetailsDetailComponent implements OnInit {
  incomeDetails: IIncomeDetails | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ incomeDetails }) => {
      this.incomeDetails = incomeDetails;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
