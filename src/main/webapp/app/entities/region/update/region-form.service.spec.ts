import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../region.test-samples';

import { RegionFormService } from './region-form.service';

describe('Region Form Service', () => {
  let service: RegionFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RegionFormService);
  });

  describe('Service methods', () => {
    describe('createRegionFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createRegionFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            regionName: expect.any(Object),
            isDeleted: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            freeField4: expect.any(Object),
            freeField5: expect.any(Object),
            organisation: expect.any(Object),
          })
        );
      });

      it('passing IRegion should create a new form with FormGroup', () => {
        const formGroup = service.createRegionFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            regionName: expect.any(Object),
            isDeleted: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
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

    describe('getRegion', () => {
      it('should return NewRegion for default Region initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createRegionFormGroup(sampleWithNewData);

        const region = service.getRegion(formGroup) as any;

        expect(region).toMatchObject(sampleWithNewData);
      });

      it('should return NewRegion for empty Region initial value', () => {
        const formGroup = service.createRegionFormGroup();

        const region = service.getRegion(formGroup) as any;

        expect(region).toMatchObject({});
      });

      it('should return IRegion', () => {
        const formGroup = service.createRegionFormGroup(sampleWithRequiredData);

        const region = service.getRegion(formGroup) as any;

        expect(region).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IRegion should not enable id FormControl', () => {
        const formGroup = service.createRegionFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewRegion should disable id FormControl', () => {
        const formGroup = service.createRegionFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
