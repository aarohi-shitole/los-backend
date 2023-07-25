import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../declearation.test-samples';

import { DeclearationFormService } from './declearation-form.service';

describe('Declearation Form Service', () => {
  let service: DeclearationFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DeclearationFormService);
  });

  describe('Service methods', () => {
    describe('createDeclearationFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDeclearationFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            tiltle: expect.any(Object),
            type: expect.any(Object),
            description: expect.any(Object),
            tag: expect.any(Object),
            subType: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            isDeleted: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            freeField4: expect.any(Object),
            organisation: expect.any(Object),
          })
        );
      });

      it('passing IDeclearation should create a new form with FormGroup', () => {
        const formGroup = service.createDeclearationFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            tiltle: expect.any(Object),
            type: expect.any(Object),
            description: expect.any(Object),
            tag: expect.any(Object),
            subType: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            isDeleted: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            freeField4: expect.any(Object),
            organisation: expect.any(Object),
          })
        );
      });
    });

    describe('getDeclearation', () => {
      it('should return NewDeclearation for default Declearation initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createDeclearationFormGroup(sampleWithNewData);

        const declearation = service.getDeclearation(formGroup) as any;

        expect(declearation).toMatchObject(sampleWithNewData);
      });

      it('should return NewDeclearation for empty Declearation initial value', () => {
        const formGroup = service.createDeclearationFormGroup();

        const declearation = service.getDeclearation(formGroup) as any;

        expect(declearation).toMatchObject({});
      });

      it('should return IDeclearation', () => {
        const formGroup = service.createDeclearationFormGroup(sampleWithRequiredData);

        const declearation = service.getDeclearation(formGroup) as any;

        expect(declearation).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDeclearation should not enable id FormControl', () => {
        const formGroup = service.createDeclearationFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDeclearation should disable id FormControl', () => {
        const formGroup = service.createDeclearationFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
