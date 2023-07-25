import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { VouchersFormService, VouchersFormGroup } from './vouchers-form.service';
import { IVouchers } from '../vouchers.model';
import { VouchersService } from '../service/vouchers.service';
import { VoucherCode } from 'app/entities/enumerations/voucher-code.model';
import { PaymentMode } from 'app/entities/enumerations/payment-mode.model';

@Component({
  selector: 'jhi-vouchers-update',
  templateUrl: './vouchers-update.component.html',
})
export class VouchersUpdateComponent implements OnInit {
  isSaving = false;
  vouchers: IVouchers | null = null;
  voucherCodeValues = Object.keys(VoucherCode);
  paymentModeValues = Object.keys(PaymentMode);

  editForm: VouchersFormGroup = this.vouchersFormService.createVouchersFormGroup();

  constructor(
    protected vouchersService: VouchersService,
    protected vouchersFormService: VouchersFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vouchers }) => {
      this.vouchers = vouchers;
      if (vouchers) {
        this.updateForm(vouchers);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const vouchers = this.vouchersFormService.getVouchers(this.editForm);
    if (vouchers.id !== null) {
      this.subscribeToSaveResponse(this.vouchersService.update(vouchers));
    } else {
      this.subscribeToSaveResponse(this.vouchersService.create(vouchers));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVouchers>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(vouchers: IVouchers): void {
    this.vouchers = vouchers;
    this.vouchersFormService.resetForm(this.editForm, vouchers);
  }
}
