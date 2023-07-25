import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../schemes-details.test-samples';

import { SchemesDetailsFormService } from './schemes-details-form.service';

describe('SchemesDetails Form Service', () => {
  let service: SchemesDetailsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SchemesDetailsFormService);
  });

  describe('Service methods', () => {
    describe('createSchemesDetailsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSchemesDetailsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fdDurationDays: expect.any(Object),
            interest: expect.any(Object),
            lockInPeriod: expect.any(Object),
            schemeName: expect.any(Object),
            rdDurationMonths: expect.any(Object),
            reinvestInterest: expect.any(Object),
            minAmount: expect.any(Object),
            lastDayOfScheam: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            isDeleted: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            freeField4: expect.any(Object),
            freeField5: expect.any(Object),
            organisation: expect.any(Object),
          })
        );
      });

      it('passing ISchemesDetails should create a new form with FormGroup', () => {
        const formGroup = service.createSchemesDetailsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fdDurationDays: expect.any(Object),
            interest: expect.any(Object),
            lockInPeriod: expect.any(Object),
            schemeName: expect.any(Object),
            rdDurationMonths: expect.any(Object),
            reinvestInterest: expect.any(Object),
            minAmount: expect.any(Object),
            lastDayOfScheam: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            isDeleted: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            freeField4: expect.any(Object),
            freeField5: expect.any(Object),
            organisation: expect.any(Object),
          })
        );
      });
    });

    describe('getSchemesDetails', () => {
      it('should return NewSchemesDetails for default SchemesDetails initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createSchemesDetailsFormGroup(sampleWithNewData);

        const schemesDetails = service.getSchemesDetails(formGroup) as any;

        expect(schemesDetails).toMatchObject(sampleWithNewData);
      });

      it('should return NewSchemesDetails for empty SchemesDetails initial value', () => {
        const formGroup = service.createSchemesDetailsFormGroup();

        const schemesDetails = service.getSchemesDetails(formGroup) as any;

        expect(schemesDetails).toMatchObject({});
      });

      it('should return ISchemesDetails', () => {
        const formGroup = service.createSchemesDetailsFormGroup(sampleWithRequiredData);

        const schemesDetails = service.getSchemesDetails(formGroup) as any;

        expect(schemesDetails).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISchemesDetails should not enable id FormControl', () => {
        const formGroup = service.createSchemesDetailsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSchemesDetails should disable id FormControl', () => {
        const formGroup = service.createSchemesDetailsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
