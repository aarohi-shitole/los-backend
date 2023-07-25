import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ParameterLookupFormService, ParameterLookupFormGroup } from './parameter-lookup-form.service';
import { IParameterLookup } from '../parameter-lookup.model';
import { ParameterLookupService } from '../service/parameter-lookup.service';
import { IOrganisation } from 'app/entities/organisation/organisation.model';
import { OrganisationService } from 'app/entities/organisation/service/organisation.service';
import { ParameterLookupType } from 'app/entities/enumerations/parameter-lookup-type.model';

@Component({
  selector: 'jhi-parameter-lookup-update',
  templateUrl: './parameter-lookup-update.component.html',
})
export class ParameterLookupUpdateComponent implements OnInit {
  isSaving = false;
  parameterLookup: IParameterLookup | null = null;
  parameterLookupTypeValues = Object.keys(ParameterLookupType);

  organisationsSharedCollection: IOrganisation[] = [];

  editForm: ParameterLookupFormGroup = this.parameterLookupFormService.createParameterLookupFormGroup();

  constructor(
    protected parameterLookupService: ParameterLookupService,
    protected parameterLookupFormService: ParameterLookupFormService,
    protected organisationService: OrganisationService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareOrganisation = (o1: IOrganisation | null, o2: IOrganisation | null): boolean =>
    this.organisationService.compareOrganisation(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ parameterLookup }) => {
      this.parameterLookup = parameterLookup;
      if (parameterLookup) {
        this.updateForm(parameterLookup);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const parameterLookup = this.parameterLookupFormService.getParameterLookup(this.editForm);
    if (parameterLookup.id !== null) {
      this.subscribeToSaveResponse(this.parameterLookupService.update(parameterLookup));
    } else {
      this.subscribeToSaveResponse(this.parameterLookupService.create(parameterLookup));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IParameterLookup>>): void {
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

  protected updateForm(parameterLookup: IParameterLookup): void {
    this.parameterLookup = parameterLookup;
    this.parameterLookupFormService.resetForm(this.editForm, parameterLookup);

    this.organisationsSharedCollection = this.organisationService.addOrganisationToCollectionIfMissing<IOrganisation>(
      this.organisationsSharedCollection,
      parameterLookup.organisation
    );
  }

  protected loadRelationshipsOptions(): void {
    this.organisationService
      .query()
      .pipe(map((res: HttpResponse<IOrganisation[]>) => res.body ?? []))
      .pipe(
        map((organisations: IOrganisation[]) =>
          this.organisationService.addOrganisationToCollectionIfMissing<IOrganisation>(organisations, this.parameterLookup?.organisation)
        )
      )
      .subscribe((organisations: IOrganisation[]) => (this.organisationsSharedCollection = organisations));
  }
}
