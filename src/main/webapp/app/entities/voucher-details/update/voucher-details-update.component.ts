import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { VoucherDetailsFormService, VoucherDetailsFormGroup } from './voucher-details-form.service';
import { IVoucherDetails } from '../voucher-details.model';
import { VoucherDetailsService } from '../service/voucher-details.service';

@Component({
  selector: 'jhi-voucher-details-update',
  templateUrl: './voucher-details-update.component.html',
})
export class VoucherDetailsUpdateComponent implements OnInit {
  isSaving = false;
  voucherDetails: IVoucherDetails | null = null;

  editForm: VoucherDetailsFormGroup = this.voucherDetailsFormService.createVoucherDetailsFormGroup();

  constructor(
    protected voucherDetailsService: VoucherDetailsService,
    protected voucherDetailsFormService: VoucherDetailsFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ voucherDetails }) => {
      this.voucherDetails = voucherDetails;
      if (voucherDetails) {
        this.updateForm(voucherDetails);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const voucherDetails = this.voucherDetailsFormService.getVoucherDetails(this.editForm);
    if (voucherDetails.id !== null) {
      this.subscribeToSaveResponse(this.voucherDetailsService.update(voucherDetails));
    } else {
      this.subscribeToSaveResponse(this.voucherDetailsService.create(voucherDetails));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVoucherDetails>>): void {
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

  protected updateForm(voucherDetails: IVoucherDetails): void {
    this.voucherDetails = voucherDetails;
    this.voucherDetailsFormService.resetForm(this.editForm, voucherDetails);
  }
}
