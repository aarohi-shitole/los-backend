import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../npa-setting.test-samples';

import { NpaSettingFormService } from './npa-setting-form.service';

describe('NpaSetting Form Service', () => {
  let service: NpaSettingFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NpaSettingFormService);
  });

  describe('Service methods', () => {
    describe('createNpaSettingFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createNpaSettingFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            npaClassification: expect.any(Object),
            durationStart: expect.any(Object),
            durationEnd: expect.any(Object),
            provision: expect.any(Object),
            year: expect.any(Object),
            interestRate: expect.any(Object),
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

      it('passing INpaSetting should create a new form with FormGroup', () => {
        const formGroup = service.createNpaSettingFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            npaClassification: expect.any(Object),
            durationStart: expect.any(Object),
            durationEnd: expect.any(Object),
            provision: expect.any(Object),
            year: expect.any(Object),
            interestRate: expect.any(Object),
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

    describe('getNpaSetting', () => {
      it('should return NewNpaSetting for default NpaSetting initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createNpaSettingFormGroup(sampleWithNewData);

        const npaSetting = service.getNpaSetting(formGroup) as any;

        expect(npaSetting).toMatchObject(sampleWithNewData);
      });

      it('should return NewNpaSetting for empty NpaSetting initial value', () => {
        const formGroup = service.createNpaSettingFormGroup();

        const npaSetting = service.getNpaSetting(formGroup) as any;

        expect(npaSetting).toMatchObject({});
      });

      it('should return INpaSetting', () => {
        const formGroup = service.createNpaSettingFormGroup(sampleWithRequiredData);

        const npaSetting = service.getNpaSetting(formGroup) as any;

        expect(npaSetting).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing INpaSetting should not enable id FormControl', () => {
        const formGroup = service.createNpaSettingFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewNpaSetting should disable id FormControl', () => {
        const formGroup = service.createNpaSettingFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
