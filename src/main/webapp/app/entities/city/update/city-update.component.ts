import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { CityFormService, CityFormGroup } from './city-form.service';
import { ICity } from '../city.model';
import { CityService } from '../service/city.service';
import { ITaluka } from 'app/entities/taluka/taluka.model';
import { TalukaService } from 'app/entities/taluka/service/taluka.service';

@Component({
  selector: 'jhi-city-update',
  templateUrl: './city-update.component.html',
})
export class CityUpdateComponent implements OnInit {
  isSaving = false;
  city: ICity | null = null;

  talukasSharedCollection: ITaluka[] = [];

  editForm: CityFormGroup = this.cityFormService.createCityFormGroup();

  constructor(
    protected cityService: CityService,
    protected cityFormService: CityFormService,
    protected talukaService: TalukaService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareTaluka = (o1: ITaluka | null, o2: ITaluka | null): boolean => this.talukaService.compareTaluka(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ city }) => {
      this.city = city;
      if (city) {
        this.updateForm(city);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const city = this.cityFormService.getCity(this.editForm);
    if (city.id !== null) {
      this.subscribeToSaveResponse(this.cityService.update(city));
    } else {
      this.subscribeToSaveResponse(this.cityService.create(city));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICity>>): void {
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

  protected updateForm(city: ICity): void {
    this.city = city;
    this.cityFormService.resetForm(this.editForm, city);

    this.talukasSharedCollection = this.talukaService.addTalukaToCollectionIfMissing<ITaluka>(this.talukasSharedCollection, city.taluka);
  }

  protected loadRelationshipsOptions(): void {
    this.talukaService
      .query()
      .pipe(map((res: HttpResponse<ITaluka[]>) => res.body ?? []))
      .pipe(map((talukas: ITaluka[]) => this.talukaService.addTalukaToCollectionIfMissing<ITaluka>(talukas, this.city?.taluka)))
      .subscribe((talukas: ITaluka[]) => (this.talukasSharedCollection = talukas));
  }
}
