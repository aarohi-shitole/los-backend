import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { SchemesDetailsFormService, SchemesDetailsFormGroup } from './schemes-details-form.service';
import { ISchemesDetails } from '../schemes-details.model';
import { SchemesDetailsService } from '../service/schemes-details.service';
import { IOrganisation } from 'app/entities/organisation/organisation.model';
import { OrganisationService } from 'app/entities/organisation/service/organisation.service';

@Component({
  selector: 'jhi-schemes-details-update',
  templateUrl: './schemes-details-update.component.html',
})
export class SchemesDetailsUpdateComponent implements OnInit {
  isSaving = false;
  schemesDetails: ISchemesDetails | null = null;

  organisationsSharedCollection: IOrganisation[] = [];

  editForm: SchemesDetailsFormGroup = this.schemesDetailsFormService.createSchemesDetailsFormGroup();

  constructor(
    protected schemesDetailsService: SchemesDetailsService,
    protected schemesDetailsFormService: SchemesDetailsFormService,
    protected organisationService: OrganisationService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareOrganisation = (o1: IOrganisation | null, o2: IOrganisation | null): boolean =>
    this.organisationService.compareOrganisation(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ schemesDetails }) => {
      this.schemesDetails = schemesDetails;
      if (schemesDetails) {
        this.updateForm(schemesDetails);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const schemesDetails = this.schemesDetailsFormService.getSchemesDetails(this.editForm);
    if (schemesDetails.id !== null) {
      this.subscribeToSaveResponse(this.schemesDetailsService.update(schemesDetails));
    } else {
      this.subscribeToSaveResponse(this.schemesDetailsService.create(schemesDetails));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISchemesDetails>>): void {
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

  protected updateForm(schemesDetails: ISchemesDetails): void {
    this.schemesDetails = schemesDetails;
    this.schemesDetailsFormService.resetForm(this.editForm, schemesDetails);

    this.organisationsSharedCollection = this.organisationService.addOrganisationToCollectionIfMissing<IOrganisation>(
      this.organisationsSharedCollection,
      schemesDetails.organisation
    );
  }

  protected loadRelationshipsOptions(): void {
    this.organisationService
      .query()
      .pipe(map((res: HttpResponse<IOrganisation[]>) => res.body ?? []))
      .pipe(
        map((organisations: IOrganisation[]) =>
          this.organisationService.addOrganisationToCollectionIfMissing<IOrganisation>(organisations, this.schemesDetails?.organisation)
        )
      )
      .subscribe((organisations: IOrganisation[]) => (this.organisationsSharedCollection = organisations));
  }
}
