import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEpay } from '../epay.model';

@Component({
  selector: 'jhi-epay-detail',
  templateUrl: './epay-detail.component.html',
})
export class EpayDetailComponent implements OnInit {
  epay: IEpay | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ epay }) => {
      this.epay = epay;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
