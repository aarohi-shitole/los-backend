import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { TalukaFormService, TalukaFormGroup } from './taluka-form.service';
import { ITaluka } from '../taluka.model';
import { TalukaService } from '../service/taluka.service';
import { IDistrict } from 'app/entities/district/district.model';
import { DistrictService } from 'app/entities/district/service/district.service';

@Component({
  selector: 'jhi-taluka-update',
  templateUrl: './taluka-update.component.html',
})
export class TalukaUpdateComponent implements OnInit {
  isSaving = false;
  taluka: ITaluka | null = null;

  districtsSharedCollection: IDistrict[] = [];

  editForm: TalukaFormGroup = this.talukaFormService.createTalukaFormGroup();

  constructor(
    protected talukaService: TalukaService,
    protected talukaFormService: TalukaFormService,
    protected districtService: DistrictService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareDistrict = (o1: IDistrict | null, o2: IDistrict | null): boolean => this.districtService.compareDistrict(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taluka }) => {
      this.taluka = taluka;
      if (taluka) {
        this.updateForm(taluka);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const taluka = this.talukaFormService.getTaluka(this.editForm);
    if (taluka.id !== null) {
      this.subscribeToSaveResponse(this.talukaService.update(taluka));
    } else {
      this.subscribeToSaveResponse(this.talukaService.create(taluka));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITaluka>>): void {
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

  protected updateForm(taluka: ITaluka): void {
    this.taluka = taluka;
    this.talukaFormService.resetForm(this.editForm, taluka);

    this.districtsSharedCollection = this.districtService.addDistrictToCollectionIfMissing<IDistrict>(
      this.districtsSharedCollection,
      taluka.district
    );
  }

  protected loadRelationshipsOptions(): void {
    this.districtService
      .query()
      .pipe(map((res: HttpResponse<IDistrict[]>) => res.body ?? []))
      .pipe(
        map((districts: IDistrict[]) => this.districtService.addDistrictToCollectionIfMissing<IDistrict>(districts, this.taluka?.district))
      )
      .subscribe((districts: IDistrict[]) => (this.districtsSharedCollection = districts));
  }
}
