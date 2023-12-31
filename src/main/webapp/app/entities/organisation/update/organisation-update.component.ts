import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { OrganisationFormService, OrganisationFormGroup } from './organisation-form.service';
import { IOrganisation } from '../organisation.model';
import { OrganisationService } from '../service/organisation.service';
import { IAddress } from 'app/entities/address/address.model';
import { AddressService } from 'app/entities/address/service/address.service';
import { OrganisationType } from 'app/entities/enumerations/organisation-type.model';

@Component({
  selector: 'jhi-organisation-update',
  templateUrl: './organisation-update.component.html',
})
export class OrganisationUpdateComponent implements OnInit {
  isSaving = false;
  organisation: IOrganisation | null = null;
  organisationTypeValues = Object.keys(OrganisationType);

  addressesSharedCollection: IAddress[] = [];

  editForm: OrganisationFormGroup = this.organisationFormService.createOrganisationFormGroup();

  constructor(
    protected organisationService: OrganisationService,
    protected organisationFormService: OrganisationFormService,
    protected addressService: AddressService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareAddress = (o1: IAddress | null, o2: IAddress | null): boolean => this.addressService.compareAddress(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ organisation }) => {
      this.organisation = organisation;
      if (organisation) {
        this.updateForm(organisation);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const organisation = this.organisationFormService.getOrganisation(this.editForm);
    if (organisation.id !== null) {
      this.subscribeToSaveResponse(this.organisationService.update(organisation));
    } else {
      this.subscribeToSaveResponse(this.organisationService.create(organisation));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrganisation>>): void {
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

  protected updateForm(organisation: IOrganisation): void {
    this.organisation = organisation;
    this.organisationFormService.resetForm(this.editForm, organisation);

    this.addressesSharedCollection = this.addressService.addAddressToCollectionIfMissing<IAddress>(
      this.addressesSharedCollection,
      organisation.address
    );
  }

  protected loadRelationshipsOptions(): void {
    this.addressService
      .query()
      .pipe(map((res: HttpResponse<IAddress[]>) => res.body ?? []))
      .pipe(
        map((addresses: IAddress[]) => this.addressService.addAddressToCollectionIfMissing<IAddress>(addresses, this.organisation?.address))
      )
      .subscribe((addresses: IAddress[]) => (this.addressesSharedCollection = addresses));
  }
}
