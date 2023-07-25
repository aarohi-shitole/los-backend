import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { OrgPrerequisiteDocFormService, OrgPrerequisiteDocFormGroup } from './org-prerequisite-doc-form.service';
import { IOrgPrerequisiteDoc } from '../org-prerequisite-doc.model';
import { OrgPrerequisiteDocService } from '../service/org-prerequisite-doc.service';
import { IProduct } from 'app/entities/product/product.model';
import { ProductService } from 'app/entities/product/service/product.service';
import { IOrganisation } from 'app/entities/organisation/organisation.model';
import { OrganisationService } from 'app/entities/organisation/service/organisation.service';

@Component({
  selector: 'jhi-org-prerequisite-doc-update',
  templateUrl: './org-prerequisite-doc-update.component.html',
})
export class OrgPrerequisiteDocUpdateComponent implements OnInit {
  isSaving = false;
  orgPrerequisiteDoc: IOrgPrerequisiteDoc | null = null;

  productsSharedCollection: IProduct[] = [];
  organisationsSharedCollection: IOrganisation[] = [];

  editForm: OrgPrerequisiteDocFormGroup = this.orgPrerequisiteDocFormService.createOrgPrerequisiteDocFormGroup();

  constructor(
    protected orgPrerequisiteDocService: OrgPrerequisiteDocService,
    protected orgPrerequisiteDocFormService: OrgPrerequisiteDocFormService,
    protected productService: ProductService,
    protected organisationService: OrganisationService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareProduct = (o1: IProduct | null, o2: IProduct | null): boolean => this.productService.compareProduct(o1, o2);

  compareOrganisation = (o1: IOrganisation | null, o2: IOrganisation | null): boolean =>
    this.organisationService.compareOrganisation(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orgPrerequisiteDoc }) => {
      this.orgPrerequisiteDoc = orgPrerequisiteDoc;
      if (orgPrerequisiteDoc) {
        this.updateForm(orgPrerequisiteDoc);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const orgPrerequisiteDoc = this.orgPrerequisiteDocFormService.getOrgPrerequisiteDoc(this.editForm);
    if (orgPrerequisiteDoc.id !== null) {
      this.subscribeToSaveResponse(this.orgPrerequisiteDocService.update(orgPrerequisiteDoc));
    } else {
      this.subscribeToSaveResponse(this.orgPrerequisiteDocService.create(orgPrerequisiteDoc));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrgPrerequisiteDoc>>): void {
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

  protected updateForm(orgPrerequisiteDoc: IOrgPrerequisiteDoc): void {
    this.orgPrerequisiteDoc = orgPrerequisiteDoc;
    this.orgPrerequisiteDocFormService.resetForm(this.editForm, orgPrerequisiteDoc);

    this.productsSharedCollection = this.productService.addProductToCollectionIfMissing<IProduct>(
      this.productsSharedCollection,
      orgPrerequisiteDoc.product
    );
    this.organisationsSharedCollection = this.organisationService.addOrganisationToCollectionIfMissing<IOrganisation>(
      this.organisationsSharedCollection,
      orgPrerequisiteDoc.organisation
    );
  }

  protected loadRelationshipsOptions(): void {
    this.productService
      .query()
      .pipe(map((res: HttpResponse<IProduct[]>) => res.body ?? []))
      .pipe(
        map((products: IProduct[]) =>
          this.productService.addProductToCollectionIfMissing<IProduct>(products, this.orgPrerequisiteDoc?.product)
        )
      )
      .subscribe((products: IProduct[]) => (this.productsSharedCollection = products));

    this.organisationService
      .query()
      .pipe(map((res: HttpResponse<IOrganisation[]>) => res.body ?? []))
      .pipe(
        map((organisations: IOrganisation[]) =>
          this.organisationService.addOrganisationToCollectionIfMissing<IOrganisation>(organisations, this.orgPrerequisiteDoc?.organisation)
        )
      )
      .subscribe((organisations: IOrganisation[]) => (this.organisationsSharedCollection = organisations));
  }
}
