import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ProductFormService, ProductFormGroup } from './product-form.service';
import { IProduct } from '../product.model';
import { ProductService } from '../service/product.service';
import { ILoanCatagory } from 'app/entities/loan-catagory/loan-catagory.model';
import { LoanCatagoryService } from 'app/entities/loan-catagory/service/loan-catagory.service';
import { IOrganisation } from 'app/entities/organisation/organisation.model';
import { OrganisationService } from 'app/entities/organisation/service/organisation.service';
import { ILedgerAccounts } from 'app/entities/ledger-accounts/ledger-accounts.model';
import { LedgerAccountsService } from 'app/entities/ledger-accounts/service/ledger-accounts.service';

@Component({
  selector: 'jhi-product-update',
  templateUrl: './product-update.component.html',
})
export class ProductUpdateComponent implements OnInit {
  isSaving = false;
  product: IProduct | null = null;

  loanCatagoriesSharedCollection: ILoanCatagory[] = [];
  organisationsSharedCollection: IOrganisation[] = [];
  ledgerAccountsSharedCollection: ILedgerAccounts[] = [];

  editForm: ProductFormGroup = this.productFormService.createProductFormGroup();

  constructor(
    protected productService: ProductService,
    protected productFormService: ProductFormService,
    protected loanCatagoryService: LoanCatagoryService,
    protected organisationService: OrganisationService,
    protected ledgerAccountsService: LedgerAccountsService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareLoanCatagory = (o1: ILoanCatagory | null, o2: ILoanCatagory | null): boolean =>
    this.loanCatagoryService.compareLoanCatagory(o1, o2);

  compareOrganisation = (o1: IOrganisation | null, o2: IOrganisation | null): boolean =>
    this.organisationService.compareOrganisation(o1, o2);

  compareLedgerAccounts = (o1: ILedgerAccounts | null, o2: ILedgerAccounts | null): boolean =>
    this.ledgerAccountsService.compareLedgerAccounts(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ product }) => {
      this.product = product;
      if (product) {
        this.updateForm(product);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const product = this.productFormService.getProduct(this.editForm);
    if (product.id !== null) {
      this.subscribeToSaveResponse(this.productService.update(product));
    } else {
      this.subscribeToSaveResponse(this.productService.create(product));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduct>>): void {
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

  protected updateForm(product: IProduct): void {
    this.product = product;
    this.productFormService.resetForm(this.editForm, product);

    this.loanCatagoriesSharedCollection = this.loanCatagoryService.addLoanCatagoryToCollectionIfMissing<ILoanCatagory>(
      this.loanCatagoriesSharedCollection,
      product.loanCatagory
    );
    this.organisationsSharedCollection = this.organisationService.addOrganisationToCollectionIfMissing<IOrganisation>(
      this.organisationsSharedCollection,
      product.organisation
    );
    this.ledgerAccountsSharedCollection = this.ledgerAccountsService.addLedgerAccountsToCollectionIfMissing<ILedgerAccounts>(
      this.ledgerAccountsSharedCollection,
      product.ledgerAccounts
    );
  }

  protected loadRelationshipsOptions(): void {
    this.loanCatagoryService
      .query()
      .pipe(map((res: HttpResponse<ILoanCatagory[]>) => res.body ?? []))
      .pipe(
        map((loanCatagories: ILoanCatagory[]) =>
          this.loanCatagoryService.addLoanCatagoryToCollectionIfMissing<ILoanCatagory>(loanCatagories, this.product?.loanCatagory)
        )
      )
      .subscribe((loanCatagories: ILoanCatagory[]) => (this.loanCatagoriesSharedCollection = loanCatagories));

    this.organisationService
      .query()
      .pipe(map((res: HttpResponse<IOrganisation[]>) => res.body ?? []))
      .pipe(
        map((organisations: IOrganisation[]) =>
          this.organisationService.addOrganisationToCollectionIfMissing<IOrganisation>(organisations, this.product?.organisation)
        )
      )
      .subscribe((organisations: IOrganisation[]) => (this.organisationsSharedCollection = organisations));

    this.ledgerAccountsService
      .query()
      .pipe(map((res: HttpResponse<ILedgerAccounts[]>) => res.body ?? []))
      .pipe(
        map((ledgerAccounts: ILedgerAccounts[]) =>
          this.ledgerAccountsService.addLedgerAccountsToCollectionIfMissing<ILedgerAccounts>(ledgerAccounts, this.product?.ledgerAccounts)
        )
      )
      .subscribe((ledgerAccounts: ILedgerAccounts[]) => (this.ledgerAccountsSharedCollection = ledgerAccounts));
  }
}
