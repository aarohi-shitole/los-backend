import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IProduct, NewProduct } from '../product.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProduct for edit and NewProductFormGroupInput for create.
 */
type ProductFormGroupInput = IProduct | PartialWithRequiredKeyOf<NewProduct>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IProduct | NewProduct> = Omit<T, 'lastModified'> & {
  lastModified?: string | null;
};

type ProductFormRawValue = FormValueOf<IProduct>;

type NewProductFormRawValue = FormValueOf<NewProduct>;

type ProductFormDefaults = Pick<NewProduct, 'id' | 'isDeleted' | 'lastModified'>;

type ProductFormGroupContent = {
  id: FormControl<ProductFormRawValue['id'] | NewProduct['id']>;
  prodCode: FormControl<ProductFormRawValue['prodCode']>;
  prodName: FormControl<ProductFormRawValue['prodName']>;
  bpiTreatmentFlag: FormControl<ProductFormRawValue['bpiTreatmentFlag']>;
  amortizationMethod: FormControl<ProductFormRawValue['amortizationMethod']>;
  amortizationType: FormControl<ProductFormRawValue['amortizationType']>;
  compoundingFreq: FormControl<ProductFormRawValue['compoundingFreq']>;
  emiRounding: FormControl<ProductFormRawValue['emiRounding']>;
  lastRowEMIThreshold: FormControl<ProductFormRawValue['lastRowEMIThreshold']>;
  graceDays: FormControl<ProductFormRawValue['graceDays']>;
  reschLockinPeriod: FormControl<ProductFormRawValue['reschLockinPeriod']>;
  prepayAfterInstNo: FormControl<ProductFormRawValue['prepayAfterInstNo']>;
  prepayBeforeInstNo: FormControl<ProductFormRawValue['prepayBeforeInstNo']>;
  minInstallmentGapBetPrepay: FormControl<ProductFormRawValue['minInstallmentGapBetPrepay']>;
  minPrepayAmount: FormControl<ProductFormRawValue['minPrepayAmount']>;
  forecloseAfterInstNo: FormControl<ProductFormRawValue['forecloseAfterInstNo']>;
  forecloseBeforeInstaNo: FormControl<ProductFormRawValue['forecloseBeforeInstaNo']>;
  minTenor: FormControl<ProductFormRawValue['minTenor']>;
  maxTenor: FormControl<ProductFormRawValue['maxTenor']>;
  minInstallmentAmount: FormControl<ProductFormRawValue['minInstallmentAmount']>;
  maxInstallmentAmount: FormControl<ProductFormRawValue['maxInstallmentAmount']>;
  dropLineAmount: FormControl<ProductFormRawValue['dropLineAmount']>;
  dropLineODYN: FormControl<ProductFormRawValue['dropLineODYN']>;
  dropLinePerc: FormControl<ProductFormRawValue['dropLinePerc']>;
  dropMode: FormControl<ProductFormRawValue['dropMode']>;
  dropLIneFreq: FormControl<ProductFormRawValue['dropLIneFreq']>;
  dropLineCycleDay: FormControl<ProductFormRawValue['dropLineCycleDay']>;
  roi: FormControl<ProductFormRawValue['roi']>;
  isDeleted: FormControl<ProductFormRawValue['isDeleted']>;
  lastModified: FormControl<ProductFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<ProductFormRawValue['lastModifiedBy']>;
  freeField1: FormControl<ProductFormRawValue['freeField1']>;
  freeField2: FormControl<ProductFormRawValue['freeField2']>;
  freeField3: FormControl<ProductFormRawValue['freeField3']>;
  freeField4: FormControl<ProductFormRawValue['freeField4']>;
  freeField5: FormControl<ProductFormRawValue['freeField5']>;
  freeField6: FormControl<ProductFormRawValue['freeField6']>;
  loanCatagory: FormControl<ProductFormRawValue['loanCatagory']>;
  organisation: FormControl<ProductFormRawValue['organisation']>;
  ledgerAccounts: FormControl<ProductFormRawValue['ledgerAccounts']>;
};

export type ProductFormGroup = FormGroup<ProductFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProductFormService {
  createProductFormGroup(product: ProductFormGroupInput = { id: null }): ProductFormGroup {
    const productRawValue = this.convertProductToProductRawValue({
      ...this.getFormDefaults(),
      ...product,
    });
    return new FormGroup<ProductFormGroupContent>({
      id: new FormControl(
        { value: productRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      prodCode: new FormControl(productRawValue.prodCode),
      prodName: new FormControl(productRawValue.prodName),
      bpiTreatmentFlag: new FormControl(productRawValue.bpiTreatmentFlag),
      amortizationMethod: new FormControl(productRawValue.amortizationMethod),
      amortizationType: new FormControl(productRawValue.amortizationType),
      compoundingFreq: new FormControl(productRawValue.compoundingFreq),
      emiRounding: new FormControl(productRawValue.emiRounding),
      lastRowEMIThreshold: new FormControl(productRawValue.lastRowEMIThreshold),
      graceDays: new FormControl(productRawValue.graceDays),
      reschLockinPeriod: new FormControl(productRawValue.reschLockinPeriod),
      prepayAfterInstNo: new FormControl(productRawValue.prepayAfterInstNo),
      prepayBeforeInstNo: new FormControl(productRawValue.prepayBeforeInstNo),
      minInstallmentGapBetPrepay: new FormControl(productRawValue.minInstallmentGapBetPrepay),
      minPrepayAmount: new FormControl(productRawValue.minPrepayAmount),
      forecloseAfterInstNo: new FormControl(productRawValue.forecloseAfterInstNo),
      forecloseBeforeInstaNo: new FormControl(productRawValue.forecloseBeforeInstaNo),
      minTenor: new FormControl(productRawValue.minTenor),
      maxTenor: new FormControl(productRawValue.maxTenor),
      minInstallmentAmount: new FormControl(productRawValue.minInstallmentAmount),
      maxInstallmentAmount: new FormControl(productRawValue.maxInstallmentAmount),
      dropLineAmount: new FormControl(productRawValue.dropLineAmount),
      dropLineODYN: new FormControl(productRawValue.dropLineODYN),
      dropLinePerc: new FormControl(productRawValue.dropLinePerc),
      dropMode: new FormControl(productRawValue.dropMode),
      dropLIneFreq: new FormControl(productRawValue.dropLIneFreq),
      dropLineCycleDay: new FormControl(productRawValue.dropLineCycleDay),
      roi: new FormControl(productRawValue.roi),
      isDeleted: new FormControl(productRawValue.isDeleted),
      lastModified: new FormControl(productRawValue.lastModified),
      lastModifiedBy: new FormControl(productRawValue.lastModifiedBy),
      freeField1: new FormControl(productRawValue.freeField1),
      freeField2: new FormControl(productRawValue.freeField2),
      freeField3: new FormControl(productRawValue.freeField3),
      freeField4: new FormControl(productRawValue.freeField4),
      freeField5: new FormControl(productRawValue.freeField5),
      freeField6: new FormControl(productRawValue.freeField6),
      loanCatagory: new FormControl(productRawValue.loanCatagory),
      organisation: new FormControl(productRawValue.organisation),
      ledgerAccounts: new FormControl(productRawValue.ledgerAccounts),
    });
  }

  getProduct(form: ProductFormGroup): IProduct | NewProduct {
    return this.convertProductRawValueToProduct(form.getRawValue() as ProductFormRawValue | NewProductFormRawValue);
  }

  resetForm(form: ProductFormGroup, product: ProductFormGroupInput): void {
    const productRawValue = this.convertProductToProductRawValue({ ...this.getFormDefaults(), ...product });
    form.reset(
      {
        ...productRawValue,
        id: { value: productRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ProductFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      isDeleted: false,
      lastModified: currentTime,
    };
  }

  private convertProductRawValueToProduct(rawProduct: ProductFormRawValue | NewProductFormRawValue): IProduct | NewProduct {
    return {
      ...rawProduct,
      lastModified: dayjs(rawProduct.lastModified, DATE_TIME_FORMAT),
    };
  }

  private convertProductToProductRawValue(
    product: IProduct | (Partial<NewProduct> & ProductFormDefaults)
  ): ProductFormRawValue | PartialWithRequiredKeyOf<NewProductFormRawValue> {
    return {
      ...product,
      lastModified: product.lastModified ? product.lastModified.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
