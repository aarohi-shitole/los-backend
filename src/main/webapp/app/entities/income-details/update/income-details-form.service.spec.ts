import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../income-details.test-samples';

import { IncomeDetailsFormService } from './income-details-form.service';

describe('IncomeDetails Form Service', () => {
  let service: IncomeDetailsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IncomeDetailsFormService);
  });

  describe('Service methods', () => {
    describe('createIncomeDetailsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createIncomeDetailsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            year: expect.any(Object),
            grossIncome: expect.any(Object),
            expenses: expect.any(Object),
            netIncome: expect.any(Object),
            paidTaxes: expect.any(Object),
            cashSurplus: expect.any(Object),
            incomeType: expect.any(Object),
            isDeleted: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            freeField4: expect.any(Object),
            freeField5: expect.any(Object),
            freeField6: expect.any(Object),
            member: expect.any(Object),
          })
        );
      });

      it('passing IIncomeDetails should create a new form with FormGroup', () => {
        const formGroup = service.createIncomeDetailsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            year: expect.any(Object),
            grossIncome: expect.any(Object),
            expenses: expect.any(Object),
            netIncome: expect.any(Object),
            paidTaxes: expect.any(Object),
            cashSurplus: expect.any(Object),
            incomeType: expect.any(Object),
            isDeleted: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            freeField4: expect.any(Object),
            freeField5: expect.any(Object),
            freeField6: expect.any(Object),
            member: expect.any(Object),
          })
        );
      });
    });

    describe('getIncomeDetails', () => {
      it('should return NewIncomeDetails for default IncomeDetails initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createIncomeDetailsFormGroup(sampleWithNewData);

        const incomeDetails = service.getIncomeDetails(formGroup) as any;

        expect(incomeDetails).toMatchObject(sampleWithNewData);
      });

      it('should return NewIncomeDetails for empty IncomeDetails initial value', () => {
        const formGroup = service.createIncomeDetailsFormGroup();

        const incomeDetails = service.getIncomeDetails(formGroup) as any;

        expect(incomeDetails).toMatchObject({});
      });

      it('should return IIncomeDetails', () => {
        const formGroup = service.createIncomeDetailsFormGroup(sampleWithRequiredData);

        const incomeDetails = service.getIncomeDetails(formGroup) as any;

        expect(incomeDetails).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IIncomeDetails should not enable id FormControl', () => {
        const formGroup = service.createIncomeDetailsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewIncomeDetails should disable id FormControl', () => {
        const formGroup = service.createIncomeDetailsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
