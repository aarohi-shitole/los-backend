import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVouchers } from '../vouchers.model';

@Component({
  selector: 'jhi-vouchers-detail',
  templateUrl: './vouchers-detail.component.html',
})
export class VouchersDetailComponent implements OnInit {
  vouchers: IVouchers | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vouchers }) => {
      this.vouchers = vouchers;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
