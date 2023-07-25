import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { InterestConfigFormService, InterestConfigFormGroup } from './interest-config-form.service';
import { IInterestConfig } from '../interest-config.model';
import { InterestConfigService } from '../service/interest-config.service';
import { IProduct } from 'app/entities/product/product.model';
import { ProductService } from 'app/entities/product/service/product.service';
import { InterestType } from 'app/entities/enumerations/interest-type.model';
import { RepaymentFreqency } from 'app/entities/enumerations/repayment-freqency.model';

@Component({
  selector: 'jhi-interest-config-update',
  templateUrl: './interest-config-update.component.html',
})
export class InterestConfigUpdateComponent implements OnInit {
  isSaving = false;
  interestConfig: IInterestConfig | null = null;
  interestTypeValues = Object.keys(InterestType);
  repaymentFreqencyValues = Object.keys(RepaymentFreqency);

  productsSharedCollection: IProduct[] = [];

  editForm: InterestConfigFormGroup = this.interestConfigFormService.createInterestConfigFormGroup();

  constructor(
    protected interestConfigService: InterestConfigService,
    protected interestConfigFormService: InterestConfigFormService,
    protected productService: ProductService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareProduct = (o1: IProduct | null, o2: IProduct | null): boolean => this.productService.compareProduct(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ interestConfig }) => {
      this.interestConfig = interestConfig;
      if (interestConfig) {
        this.updateForm(interestConfig);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const interestConfig = this.interestConfigFormService.getInterestConfig(this.editForm);
    if (interestConfig.id !== null) {
      this.subscribeToSaveResponse(this.interestConfigService.update(interestConfig));
    } else {
      this.subscribeToSaveResponse(this.interestConfigService.create(interestConfig));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInterestConfig>>): void {
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

  protected updateForm(interestConfig: IInterestConfig): void {
    this.interestConfig = interestConfig;
    this.interestConfigFormService.resetForm(this.editForm, interestConfig);

    this.productsSharedCollection = this.productService.addProductToCollectionIfMissing<IProduct>(
      this.productsSharedCollection,
      interestConfig.product
    );
  }

  protected loadRelationshipsOptions(): void {
    this.productService
      .query()
      .pipe(map((res: HttpResponse<IProduct[]>) => res.body ?? []))
      .pipe(
        map((products: IProduct[]) => this.productService.addProductToCollectionIfMissing<IProduct>(products, this.interestConfig?.product))
      )
      .subscribe((products: IProduct[]) => (this.productsSharedCollection = products));
  }
}
