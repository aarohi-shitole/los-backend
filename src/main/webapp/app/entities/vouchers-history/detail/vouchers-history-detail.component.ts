import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVouchersHistory } from '../vouchers-history.model';

@Component({
  selector: 'jhi-vouchers-history-detail',
  templateUrl: './vouchers-history-detail.component.html',
})
export class VouchersHistoryDetailComponent implements OnInit {
  vouchersHistory: IVouchersHistory | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vouchersHistory }) => {
      this.vouchersHistory = vouchersHistory;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
