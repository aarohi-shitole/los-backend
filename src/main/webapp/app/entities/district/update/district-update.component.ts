import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { DistrictFormService, DistrictFormGroup } from './district-form.service';
import { IDistrict } from '../district.model';
import { DistrictService } from '../service/district.service';
import { IState } from 'app/entities/state/state.model';
import { StateService } from 'app/entities/state/service/state.service';

@Component({
  selector: 'jhi-district-update',
  templateUrl: './district-update.component.html',
})
export class DistrictUpdateComponent implements OnInit {
  isSaving = false;
  district: IDistrict | null = null;

  statesSharedCollection: IState[] = [];

  editForm: DistrictFormGroup = this.districtFormService.createDistrictFormGroup();

  constructor(
    protected districtService: DistrictService,
    protected districtFormService: DistrictFormService,
    protected stateService: StateService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareState = (o1: IState | null, o2: IState | null): boolean => this.stateService.compareState(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ district }) => {
      this.district = district;
      if (district) {
        this.updateForm(district);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const district = this.districtFormService.getDistrict(this.editForm);
    if (district.id !== null) {
      this.subscribeToSaveResponse(this.districtService.update(district));
    } else {
      this.subscribeToSaveResponse(this.districtService.create(district));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDistrict>>): void {
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

  protected updateForm(district: IDistrict): void {
    this.district = district;
    this.districtFormService.resetForm(this.editForm, district);

    this.statesSharedCollection = this.stateService.addStateToCollectionIfMissing<IState>(this.statesSharedCollection, district.state);
  }

  protected loadRelationshipsOptions(): void {
    this.stateService
      .query()
      .pipe(map((res: HttpResponse<IState[]>) => res.body ?? []))
      .pipe(map((states: IState[]) => this.stateService.addStateToCollectionIfMissing<IState>(states, this.district?.state)))
      .subscribe((states: IState[]) => (this.statesSharedCollection = states));
  }
}
