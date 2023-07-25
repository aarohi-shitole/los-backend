import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { MasterAgreementFormService, MasterAgreementFormGroup } from './master-agreement-form.service';
import { IMasterAgreement } from '../master-agreement.model';
import { MasterAgreementService } from '../service/master-agreement.service';
import { RepaymentFreqency } from 'app/entities/enumerations/repayment-freqency.model';

@Component({
  selector: 'jhi-master-agreement-update',
  templateUrl: './master-agreement-update.component.html',
})
export class MasterAgreementUpdateComponent implements OnInit {
  isSaving = false;
  masterAgreement: IMasterAgreement | null = null;
  repaymentFreqencyValues = Object.keys(RepaymentFreqency);

  editForm: MasterAgreementFormGroup = this.masterAgreementFormService.createMasterAgreementFormGroup();

  constructor(
    protected masterAgreementService: MasterAgreementService,
    protected masterAgreementFormService: MasterAgreementFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ masterAgreement }) => {
      this.masterAgreement = masterAgreement;
      if (masterAgreement) {
        this.updateForm(masterAgreement);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const masterAgreement = this.masterAgreementFormService.getMasterAgreement(this.editForm);
    if (masterAgreement.id !== null) {
      this.subscribeToSaveResponse(this.masterAgreementService.update(masterAgreement));
    } else {
      this.subscribeToSaveResponse(this.masterAgreementService.create(masterAgreement));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMasterAgreement>>): void {
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

  protected updateForm(masterAgreement: IMasterAgreement): void {
    this.masterAgreement = masterAgreement;
    this.masterAgreementFormService.resetForm(this.editForm, masterAgreement);
  }
}
