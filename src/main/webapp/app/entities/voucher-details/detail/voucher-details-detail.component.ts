import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVoucherDetails } from '../voucher-details.model';

@Component({
  selector: 'jhi-voucher-details-detail',
  templateUrl: './voucher-details-detail.component.html',
})
export class VoucherDetailsDetailComponent implements OnInit {
  voucherDetails: IVoucherDetails | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ voucherDetails }) => {
      this.voucherDetails = voucherDetails;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
