import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../interest-config.test-samples';

import { InterestConfigFormService } from './interest-config-form.service';

describe('InterestConfig Form Service', () => {
  let service: InterestConfigFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InterestConfigFormService);
  });

  describe('Service methods', () => {
    describe('createInterestConfigFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createInterestConfigFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            startDate: expect.any(Object),
            endDate: expect.any(Object),
            interestBasis: expect.any(Object),
            emiBasis: expect.any(Object),
            interestType: expect.any(Object),
            intrAccrualFreq: expect.any(Object),
            penalInterestRate: expect.any(Object),
            penalInterestBasis: expect.any(Object),
            penalAccountingBasis: expect.any(Object),
            minInterestRate: expect.any(Object),
            maxInterestRate: expect.any(Object),
            extendedInterestRate: expect.any(Object),
            surchargeRate: expect.any(Object),
            isDeleted: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            freeField4: expect.any(Object),
            freeField5: expect.any(Object),
            freeField6: expect.any(Object),
            product: expect.any(Object),
          })
        );
      });

      it('passing IInterestConfig should create a new form with FormGroup', () => {
        const formGroup = service.createInterestConfigFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            startDate: expect.any(Object),
            endDate: expect.any(Object),
            interestBasis: expect.any(Object),
            emiBasis: expect.any(Object),
            interestType: expect.any(Object),
            intrAccrualFreq: expect.any(Object),
            penalInterestRate: expect.any(Object),
            penalInterestBasis: expect.any(Object),
            penalAccountingBasis: expect.any(Object),
            minInterestRate: expect.any(Object),
            maxInterestRate: expect.any(Object),
            extendedInterestRate: expect.any(Object),
            surchargeRate: expect.any(Object),
            isDeleted: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            freeField4: expect.any(Object),
            freeField5: expect.any(Object),
            freeField6: expect.any(Object),
            product: expect.any(Object),
          })
        );
      });
    });

    describe('getInterestConfig', () => {
      it('should return NewInterestConfig for default InterestConfig initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createInterestConfigFormGroup(sampleWithNewData);

        const interestConfig = service.getInterestConfig(formGroup) as any;

        expect(interestConfig).toMatchObject(sampleWithNewData);
      });

      it('should return NewInterestConfig for empty InterestConfig initial value', () => {
        const formGroup = service.createInterestConfigFormGroup();

        const interestConfig = service.getInterestConfig(formGroup) as any;

        expect(interestConfig).toMatchObject({});
      });

      it('should return IInterestConfig', () => {
        const formGroup = service.createInterestConfigFormGroup(sampleWithRequiredData);

        const interestConfig = service.getInterestConfig(formGroup) as any;

        expect(interestConfig).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IInterestConfig should not enable id FormControl', () => {
        const formGroup = service.createInterestConfigFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewInterestConfig should disable id FormControl', () => {
        const formGroup = service.createInterestConfigFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
