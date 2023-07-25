import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { LoanCatagoryFormService, LoanCatagoryFormGroup } from './loan-catagory-form.service';
import { ILoanCatagory } from '../loan-catagory.model';
import { LoanCatagoryService } from '../service/loan-catagory.service';

@Component({
  selector: 'jhi-loan-catagory-update',
  templateUrl: './loan-catagory-update.component.html',
})
export class LoanCatagoryUpdateComponent implements OnInit {
  isSaving = false;
  loanCatagory: ILoanCatagory | null = null;

  editForm: LoanCatagoryFormGroup = this.loanCatagoryFormService.createLoanCatagoryFormGroup();

  constructor(
    protected loanCatagoryService: LoanCatagoryService,
    protected loanCatagoryFormService: LoanCatagoryFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ loanCatagory }) => {
      this.loanCatagory = loanCatagory;
      if (loanCatagory) {
        this.updateForm(loanCatagory);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const loanCatagory = this.loanCatagoryFormService.getLoanCatagory(this.editForm);
    if (loanCatagory.id !== null) {
      this.subscribeToSaveResponse(this.loanCatagoryService.update(loanCatagory));
    } else {
      this.subscribeToSaveResponse(this.loanCatagoryService.create(loanCatagory));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILoanCatagory>>): void {
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

  protected updateForm(loanCatagory: ILoanCatagory): void {
    this.loanCatagory = loanCatagory;
    this.loanCatagoryFormService.resetForm(this.editForm, loanCatagory);
  }
}
