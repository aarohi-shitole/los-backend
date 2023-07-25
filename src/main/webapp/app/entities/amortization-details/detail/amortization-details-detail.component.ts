import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAmortizationDetails } from '../amortization-details.model';

@Component({
  selector: 'jhi-amortization-details-detail',
  templateUrl: './amortization-details-detail.component.html',
})
export class AmortizationDetailsDetailComponent implements OnInit {
  amortizationDetails: IAmortizationDetails | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ amortizationDetails }) => {
      this.amortizationDetails = amortizationDetails;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
