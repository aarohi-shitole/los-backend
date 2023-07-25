import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { DeclearationFormService, DeclearationFormGroup } from './declearation-form.service';
import { IDeclearation } from '../declearation.model';
import { DeclearationService } from '../service/declearation.service';
import { IOrganisation } from 'app/entities/organisation/organisation.model';
import { OrganisationService } from 'app/entities/organisation/service/organisation.service';
import { DeclearationType } from 'app/entities/enumerations/declearation-type.model';

@Component({
  selector: 'jhi-declearation-update',
  templateUrl: './declearation-update.component.html',
})
export class DeclearationUpdateComponent implements OnInit {
  isSaving = false;
  declearation: IDeclearation | null = null;
  declearationTypeValues = Object.keys(DeclearationType);

  organisationsSharedCollection: IOrganisation[] = [];

  editForm: DeclearationFormGroup = this.declearationFormService.createDeclearationFormGroup();

  constructor(
    protected declearationService: DeclearationService,
    protected declearationFormService: DeclearationFormService,
    protected organisationService: OrganisationService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareOrganisation = (o1: IOrganisation | null, o2: IOrganisation | null): boolean =>
    this.organisationService.compareOrganisation(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ declearation }) => {
      this.declearation = declearation;
      if (declearation) {
        this.updateForm(declearation);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const declearation = this.declearationFormService.getDeclearation(this.editForm);
    if (declearation.id !== null) {
      this.subscribeToSaveResponse(this.declearationService.update(declearation));
    } else {
      this.subscribeToSaveResponse(this.declearationService.create(declearation));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDeclearation>>): void {
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

  protected updateForm(declearation: IDeclearation): void {
    this.declearation = declearation;
    this.declearationFormService.resetForm(this.editForm, declearation);

    this.organisationsSharedCollection = this.organisationService.addOrganisationToCollectionIfMissing<IOrganisation>(
      this.organisationsSharedCollection,
      declearation.organisation
    );
  }

  protected loadRelationshipsOptions(): void {
    this.organisationService
      .query()
      .pipe(map((res: HttpResponse<IOrganisation[]>) => res.body ?? []))
      .pipe(
        map((organisations: IOrganisation[]) =>
          this.organisationService.addOrganisationToCollectionIfMissing<IOrganisation>(organisations, this.declearation?.organisation)
        )
      )
      .subscribe((organisations: IOrganisation[]) => (this.organisationsSharedCollection = organisations));
  }
}
