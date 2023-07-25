import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../member-existingfacility.test-samples';

import { MemberExistingfacilityFormService } from './member-existingfacility-form.service';

describe('MemberExistingfacility Form Service', () => {
  let service: MemberExistingfacilityFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MemberExistingfacilityFormService);
  });

  describe('Service methods', () => {
    describe('createMemberExistingfacilityFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMemberExistingfacilityFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            year: expect.any(Object),
            facilityName: expect.any(Object),
            facilitatedFrom: expect.any(Object),
            nature: expect.any(Object),
            amtInLac: expect.any(Object),
            purpose: expect.any(Object),
            sectionDate: expect.any(Object),
            outstandingInLac: expect.any(Object),
            status: expect.any(Object),
            rating: expect.any(Object),
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

      it('passing IMemberExistingfacility should create a new form with FormGroup', () => {
        const formGroup = service.createMemberExistingfacilityFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            year: expect.any(Object),
            facilityName: expect.any(Object),
            facilitatedFrom: expect.any(Object),
            nature: expect.any(Object),
            amtInLac: expect.any(Object),
            purpose: expect.any(Object),
            sectionDate: expect.any(Object),
            outstandingInLac: expect.any(Object),
            status: expect.any(Object),
            rating: expect.any(Object),
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

    describe('getMemberExistingfacility', () => {
      it('should return NewMemberExistingfacility for default MemberExistingfacility initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createMemberExistingfacilityFormGroup(sampleWithNewData);

        const memberExistingfacility = service.getMemberExistingfacility(formGroup) as any;

        expect(memberExistingfacility).toMatchObject(sampleWithNewData);
      });

      it('should return NewMemberExistingfacility for empty MemberExistingfacility initial value', () => {
        const formGroup = service.createMemberExistingfacilityFormGroup();

        const memberExistingfacility = service.getMemberExistingfacility(formGroup) as any;

        expect(memberExistingfacility).toMatchObject({});
      });

      it('should return IMemberExistingfacility', () => {
        const formGroup = service.createMemberExistingfacilityFormGroup(sampleWithRequiredData);

        const memberExistingfacility = service.getMemberExistingfacility(formGroup) as any;

        expect(memberExistingfacility).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMemberExistingfacility should not enable id FormControl', () => {
        const formGroup = service.createMemberExistingfacilityFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMemberExistingfacility should disable id FormControl', () => {
        const formGroup = service.createMemberExistingfacilityFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
