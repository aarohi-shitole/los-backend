import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { EpayFormService, EpayFormGroup } from './epay-form.service';
import { IEpay } from '../epay.model';
import { EpayService } from '../service/epay.service';
import { RepaymentFreqency } from 'app/entities/enumerations/repayment-freqency.model';

@Component({
  selector: 'jhi-epay-update',
  templateUrl: './epay-update.component.html',
})
export class EpayUpdateComponent implements OnInit {
  isSaving = false;
  epay: IEpay | null = null;
  repaymentFreqencyValues = Object.keys(RepaymentFreqency);

  editForm: EpayFormGroup = this.epayFormService.createEpayFormGroup();

  constructor(protected epayService: EpayService, protected epayFormService: EpayFormService, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ epay }) => {
      this.epay = epay;
      if (epay) {
        this.updateForm(epay);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const epay = this.epayFormService.getEpay(this.editForm);
    if (epay.id !== null) {
      this.subscribeToSaveResponse(this.epayService.update(epay));
    } else {
      this.subscribeToSaveResponse(this.epayService.create(epay));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEpay>>): void {
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

  protected updateForm(epay: IEpay): void {
    this.epay = epay;
    this.epayFormService.resetForm(this.editForm, epay);
  }
}
