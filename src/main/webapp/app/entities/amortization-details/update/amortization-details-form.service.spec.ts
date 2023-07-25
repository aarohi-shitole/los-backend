import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../amortization-details.test-samples';

import { AmortizationDetailsFormService } from './amortization-details-form.service';

describe('AmortizationDetails Form Service', () => {
  let service: AmortizationDetailsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AmortizationDetailsFormService);
  });

  describe('Service methods', () => {
    describe('createAmortizationDetailsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAmortizationDetailsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            installmentDate: expect.any(Object),
            totalOutstandingPrincipleAmt: expect.any(Object),
            totalOutstandngInterestAmt: expect.any(Object),
            paidPrincipleAmt: expect.any(Object),
            paidInterestAmt: expect.any(Object),
            balPrincipleAmt: expect.any(Object),
            balInterestAmt: expect.any(Object),
            loanEmiAmt: expect.any(Object),
            principleEMI: expect.any(Object),
            roi: expect.any(Object),
            paymentStatus: expect.any(Object),
            year: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            freeField4: expect.any(Object),
            freeField5: expect.any(Object),
            freeField6: expect.any(Object),
            loanAccount: expect.any(Object),
          })
        );
      });

      it('passing IAmortizationDetails should create a new form with FormGroup', () => {
        const formGroup = service.createAmortizationDetailsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            installmentDate: expect.any(Object),
            totalOutstandingPrincipleAmt: expect.any(Object),
            totalOutstandngInterestAmt: expect.any(Object),
            paidPrincipleAmt: expect.any(Object),
            paidInterestAmt: expect.any(Object),
            balPrincipleAmt: expect.any(Object),
            balInterestAmt: expect.any(Object),
            loanEmiAmt: expect.any(Object),
            principleEMI: expect.any(Object),
            roi: expect.any(Object),
            paymentStatus: expect.any(Object),
            year: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            freeField4: expect.any(Object),
            freeField5: expect.any(Object),
            freeField6: expect.any(Object),
            loanAccount: expect.any(Object),
          })
        );
      });
    });

    describe('getAmortizationDetails', () => {
      it('should return NewAmortizationDetails for default AmortizationDetails initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createAmortizationDetailsFormGroup(sampleWithNewData);

        const amortizationDetails = service.getAmortizationDetails(formGroup) as any;

        expect(amortizationDetails).toMatchObject(sampleWithNewData);
      });

      it('should return NewAmortizationDetails for empty AmortizationDetails initial value', () => {
        const formGroup = service.createAmortizationDetailsFormGroup();

        const amortizationDetails = service.getAmortizationDetails(formGroup) as any;

        expect(amortizationDetails).toMatchObject({});
      });

      it('should return IAmortizationDetails', () => {
        const formGroup = service.createAmortizationDetailsFormGroup(sampleWithRequiredData);

        const amortizationDetails = service.getAmortizationDetails(formGroup) as any;

        expect(amortizationDetails).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAmortizationDetails should not enable id FormControl', () => {
        const formGroup = service.createAmortizationDetailsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAmortizationDetails should disable id FormControl', () => {
        const formGroup = service.createAmortizationDetailsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
