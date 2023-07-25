import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { VouchersHistoryFormService, VouchersHistoryFormGroup } from './vouchers-history-form.service';
import { IVouchersHistory } from '../vouchers-history.model';
import { VouchersHistoryService } from '../service/vouchers-history.service';
import { VoucherCode } from 'app/entities/enumerations/voucher-code.model';

@Component({
  selector: 'jhi-vouchers-history-update',
  templateUrl: './vouchers-history-update.component.html',
})
export class VouchersHistoryUpdateComponent implements OnInit {
  isSaving = false;
  vouchersHistory: IVouchersHistory | null = null;
  voucherCodeValues = Object.keys(VoucherCode);

  editForm: VouchersHistoryFormGroup = this.vouchersHistoryFormService.createVouchersHistoryFormGroup();

  constructor(
    protected vouchersHistoryService: VouchersHistoryService,
    protected vouchersHistoryFormService: VouchersHistoryFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vouchersHistory }) => {
      this.vouchersHistory = vouchersHistory;
      if (vouchersHistory) {
        this.updateForm(vouchersHistory);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const vouchersHistory = this.vouchersHistoryFormService.getVouchersHistory(this.editForm);
    if (vouchersHistory.id !== null) {
      this.subscribeToSaveResponse(this.vouchersHistoryService.update(vouchersHistory));
    } else {
      this.subscribeToSaveResponse(this.vouchersHistoryService.create(vouchersHistory));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVouchersHistory>>): void {
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

  protected updateForm(vouchersHistory: IVouchersHistory): void {
    this.vouchersHistory = vouchersHistory;
    this.vouchersHistoryFormService.resetForm(this.editForm, vouchersHistory);
  }
}
