import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { NpaSettingFormService, NpaSettingFormGroup } from './npa-setting-form.service';
import { INpaSetting } from '../npa-setting.model';
import { NpaSettingService } from '../service/npa-setting.service';
import { IOrganisation } from 'app/entities/organisation/organisation.model';
import { OrganisationService } from 'app/entities/organisation/service/organisation.service';
import { NpaClassification } from 'app/entities/enumerations/npa-classification.model';

@Component({
  selector: 'jhi-npa-setting-update',
  templateUrl: './npa-setting-update.component.html',
})
export class NpaSettingUpdateComponent implements OnInit {
  isSaving = false;
  npaSetting: INpaSetting | null = null;
  npaClassificationValues = Object.keys(NpaClassification);

  organisationsSharedCollection: IOrganisation[] = [];

  editForm: NpaSettingFormGroup = this.npaSettingFormService.createNpaSettingFormGroup();

  constructor(
    protected npaSettingService: NpaSettingService,
    protected npaSettingFormService: NpaSettingFormService,
    protected organisationService: OrganisationService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareOrganisation = (o1: IOrganisation | null, o2: IOrganisation | null): boolean =>
    this.organisationService.compareOrganisation(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ npaSetting }) => {
      this.npaSetting = npaSetting;
      if (npaSetting) {
        this.updateForm(npaSetting);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const npaSetting = this.npaSettingFormService.getNpaSetting(this.editForm);
    if (npaSetting.id !== null) {
      this.subscribeToSaveResponse(this.npaSettingService.update(npaSetting));
    } else {
      this.subscribeToSaveResponse(this.npaSettingService.create(npaSetting));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INpaSetting>>): void {
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

  protected updateForm(npaSetting: INpaSetting): void {
    this.npaSetting = npaSetting;
    this.npaSettingFormService.resetForm(this.editForm, npaSetting);

    this.organisationsSharedCollection = this.organisationService.addOrganisationToCollectionIfMissing<IOrganisation>(
      this.organisationsSharedCollection,
      npaSetting.organisation
    );
  }

  protected loadRelationshipsOptions(): void {
    this.organisationService
      .query()
      .pipe(map((res: HttpResponse<IOrganisation[]>) => res.body ?? []))
      .pipe(
        map((organisations: IOrganisation[]) =>
          this.organisationService.addOrganisationToCollectionIfMissing<IOrganisation>(organisations, this.npaSetting?.organisation)
        )
      )
      .subscribe((organisations: IOrganisation[]) => (this.organisationsSharedCollection = organisations));
  }
}
