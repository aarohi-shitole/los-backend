import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { BranchFormService, BranchFormGroup } from './branch-form.service';
import { IBranch } from '../branch.model';
import { BranchService } from '../service/branch.service';
import { IAddress } from 'app/entities/address/address.model';
import { AddressService } from 'app/entities/address/service/address.service';
import { IOrganisation } from 'app/entities/organisation/organisation.model';
import { OrganisationService } from 'app/entities/organisation/service/organisation.service';
import { IRegion } from 'app/entities/region/region.model';
import { RegionService } from 'app/entities/region/service/region.service';
import { BranchType } from 'app/entities/enumerations/branch-type.model';

@Component({
  selector: 'jhi-branch-update',
  templateUrl: './branch-update.component.html',
})
export class BranchUpdateComponent implements OnInit {
  isSaving = false;
  branch: IBranch | null = null;
  branchTypeValues = Object.keys(BranchType);

  addressesSharedCollection: IAddress[] = [];
  organisationsSharedCollection: IOrganisation[] = [];
  regionsSharedCollection: IRegion[] = [];

  editForm: BranchFormGroup = this.branchFormService.createBranchFormGroup();

  constructor(
    protected branchService: BranchService,
    protected branchFormService: BranchFormService,
    protected addressService: AddressService,
    protected organisationService: OrganisationService,
    protected regionService: RegionService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareAddress = (o1: IAddress | null, o2: IAddress | null): boolean => this.addressService.compareAddress(o1, o2);

  compareOrganisation = (o1: IOrganisation | null, o2: IOrganisation | null): boolean =>
    this.organisationService.compareOrganisation(o1, o2);

  compareRegion = (o1: IRegion | null, o2: IRegion | null): boolean => this.regionService.compareRegion(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ branch }) => {
      this.branch = branch;
      if (branch) {
        this.updateForm(branch);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const branch = this.branchFormService.getBranch(this.editForm);
    if (branch.id !== null) {
      this.subscribeToSaveResponse(this.branchService.update(branch));
    } else {
      this.subscribeToSaveResponse(this.branchService.create(branch));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBranch>>): void {
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

  protected updateForm(branch: IBranch): void {
    this.branch = branch;
    this.branchFormService.resetForm(this.editForm, branch);

    this.addressesSharedCollection = this.addressService.addAddressToCollectionIfMissing<IAddress>(
      this.addressesSharedCollection,
      branch.address
    );
    this.organisationsSharedCollection = this.organisationService.addOrganisationToCollectionIfMissing<IOrganisation>(
      this.organisationsSharedCollection,
      branch.organisation
    );
    this.regionsSharedCollection = this.regionService.addRegionToCollectionIfMissing<IRegion>(this.regionsSharedCollection, branch.branch);
  }

  protected loadRelationshipsOptions(): void {
    this.addressService
      .query()
      .pipe(map((res: HttpResponse<IAddress[]>) => res.body ?? []))
      .pipe(map((addresses: IAddress[]) => this.addressService.addAddressToCollectionIfMissing<IAddress>(addresses, this.branch?.address)))
      .subscribe((addresses: IAddress[]) => (this.addressesSharedCollection = addresses));

    this.organisationService
      .query()
      .pipe(map((res: HttpResponse<IOrganisation[]>) => res.body ?? []))
      .pipe(
        map((organisations: IOrganisation[]) =>
          this.organisationService.addOrganisationToCollectionIfMissing<IOrganisation>(organisations, this.branch?.organisation)
        )
      )
      .subscribe((organisations: IOrganisation[]) => (this.organisationsSharedCollection = organisations));

    this.regionService
      .query()
      .pipe(map((res: HttpResponse<IRegion[]>) => res.body ?? []))
      .pipe(map((regions: IRegion[]) => this.regionService.addRegionToCollectionIfMissing<IRegion>(regions, this.branch?.branch)))
      .subscribe((regions: IRegion[]) => (this.regionsSharedCollection = regions));
  }
}
