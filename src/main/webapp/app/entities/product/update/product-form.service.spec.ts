import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../product.test-samples';

import { ProductFormService } from './product-form.service';

describe('Product Form Service', () => {
  let service: ProductFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProductFormService);
  });

  describe('Service methods', () => {
    describe('createProductFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProductFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            prodCode: expect.any(Object),
            prodName: expect.any(Object),
            bpiTreatmentFlag: expect.any(Object),
            amortizationMethod: expect.any(Object),
            amortizationType: expect.any(Object),
            compoundingFreq: expect.any(Object),
            emiRounding: expect.any(Object),
            lastRowEMIThreshold: expect.any(Object),
            graceDays: expect.any(Object),
            reschLockinPeriod: expect.any(Object),
            prepayAfterInstNo: expect.any(Object),
            prepayBeforeInstNo: expect.any(Object),
            minInstallmentGapBetPrepay: expect.any(Object),
            minPrepayAmount: expect.any(Object),
            forecloseAfterInstNo: expect.any(Object),
            forecloseBeforeInstaNo: expect.any(Object),
            minTenor: expect.any(Object),
            maxTenor: expect.any(Object),
            minInstallmentAmount: expect.any(Object),
            maxInstallmentAmount: expect.any(Object),
            dropLineAmount: expect.any(Object),
            dropLineODYN: expect.any(Object),
            dropLinePerc: expect.any(Object),
            dropMode: expect.any(Object),
            dropLIneFreq: expect.any(Object),
            dropLineCycleDay: expect.any(Object),
            roi: expect.any(Object),
            isDeleted: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            freeField4: expect.any(Object),
            freeField5: expect.any(Object),
            freeField6: expect.any(Object),
            loanCatagory: expect.any(Object),
            organisation: expect.any(Object),
            ledgerAccounts: expect.any(Object),
          })
        );
      });

      it('passing IProduct should create a new form with FormGroup', () => {
        const formGroup = service.createProductFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            prodCode: expect.any(Object),
            prodName: expect.any(Object),
            bpiTreatmentFlag: expect.any(Object),
            amortizationMethod: expect.any(Object),
            amortizationType: expect.any(Object),
            compoundingFreq: expect.any(Object),
            emiRounding: expect.any(Object),
            lastRowEMIThreshold: expect.any(Object),
            graceDays: expect.any(Object),
            reschLockinPeriod: expect.any(Object),
            prepayAfterInstNo: expect.any(Object),
            prepayBeforeInstNo: expect.any(Object),
            minInstallmentGapBetPrepay: expect.any(Object),
            minPrepayAmount: expect.any(Object),
            forecloseAfterInstNo: expect.any(Object),
            forecloseBeforeInstaNo: expect.any(Object),
            minTenor: expect.any(Object),
            maxTenor: expect.any(Object),
            minInstallmentAmount: expect.any(Object),
            maxInstallmentAmount: expect.any(Object),
            dropLineAmount: expect.any(Object),
            dropLineODYN: expect.any(Object),
            dropLinePerc: expect.any(Object),
            dropMode: expect.any(Object),
            dropLIneFreq: expect.any(Object),
            dropLineCycleDay: expect.any(Object),
            roi: expect.any(Object),
            isDeleted: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            freeField4: expect.any(Object),
            freeField5: expect.any(Object),
            freeField6: expect.any(Object),
            loanCatagory: expect.any(Object),
            organisation: expect.any(Object),
            ledgerAccounts: expect.any(Object),
          })
        );
      });
    });

    describe('getProduct', () => {
      it('should return NewProduct for default Product initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createProductFormGroup(sampleWithNewData);

        const product = service.getProduct(formGroup) as any;

        expect(product).toMatchObject(sampleWithNewData);
      });

      it('should return NewProduct for empty Product initial value', () => {
        const formGroup = service.createProductFormGroup();

        const product = service.getProduct(formGroup) as any;

        expect(product).toMatchObject({});
      });

      it('should return IProduct', () => {
        const formGroup = service.createProductFormGroup(sampleWithRequiredData);

        const product = service.getProduct(formGroup) as any;

        expect(product).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProduct should not enable id FormControl', () => {
        const formGroup = service.createProductFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProduct should disable id FormControl', () => {
        const formGroup = service.createProductFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
