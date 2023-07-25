import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { RegionFormService, RegionFormGroup } from './region-form.service';
import { IRegion } from '../region.model';
import { RegionService } from '../service/region.service';
import { IOrganisation } from 'app/entities/organisation/organisation.model';
import { OrganisationService } from 'app/entities/organisation/service/organisation.service';

@Component({
  selector: 'jhi-region-update',
  templateUrl: './region-update.component.html',
})
export class RegionUpdateComponent implements OnInit {
  isSaving = false;
  region: IRegion | null = null;

  organisationsSharedCollection: IOrganisation[] = [];

  editForm: RegionFormGroup = this.regionFormService.createRegionFormGroup();

  constructor(
    protected regionService: RegionService,
    protected regionFormService: RegionFormService,
    protected organisationService: OrganisationService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareOrganisation = (o1: IOrganisation | null, o2: IOrganisation | null): boolean =>
    this.organisationService.compareOrganisation(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ region }) => {
      this.region = region;
      if (region) {
        this.updateForm(region);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const region = this.regionFormService.getRegion(this.editForm);
    if (region.id !== null) {
      this.subscribeToSaveResponse(this.regionService.update(region));
    } else {
      this.subscribeToSaveResponse(this.regionService.create(region));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRegion>>): void {
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

  protected updateForm(region: IRegion): void {
    this.region = region;
    this.regionFormService.resetForm(this.editForm, region);

    this.organisationsSharedCollection = this.organisationService.addOrganisationToCollectionIfMissing<IOrganisation>(
      this.organisationsSharedCollection,
      region.organisation
    );
  }

  protected loadRelationshipsOptions(): void {
    this.organisationService
      .query()
      .pipe(map((res: HttpResponse<IOrganisation[]>) => res.body ?? []))
      .pipe(
        map((organisations: IOrganisation[]) =>
          this.organisationService.addOrganisationToCollectionIfMissing<IOrganisation>(organisations, this.region?.organisation)
        )
      )
      .subscribe((organisations: IOrganisation[]) => (this.organisationsSharedCollection = organisations));
  }
}
