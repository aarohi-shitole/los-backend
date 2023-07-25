import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../member-limit.test-samples';

import { MemberLimitFormService } from './member-limit-form.service';

describe('MemberLimit Form Service', () => {
  let service: MemberLimitFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MemberLimitFormService);
  });

  describe('Service methods', () => {
    describe('createMemberLimitFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMemberLimitFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            limitSanctionAmount: expect.any(Object),
            dtLimitSanctioned: expect.any(Object),
            dtLimitExpired: expect.any(Object),
            purpose: expect.any(Object),
            isDeleted: expect.any(Object),
            isActive: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            freeField4: expect.any(Object),
          })
        );
      });

      it('passing IMemberLimit should create a new form with FormGroup', () => {
        const formGroup = service.createMemberLimitFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            limitSanctionAmount: expect.any(Object),
            dtLimitSanctioned: expect.any(Object),
            dtLimitExpired: expect.any(Object),
            purpose: expect.any(Object),
            isDeleted: expect.any(Object),
            isActive: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            freeField4: expect.any(Object),
          })
        );
      });
    });

    describe('getMemberLimit', () => {
      it('should return NewMemberLimit for default MemberLimit initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createMemberLimitFormGroup(sampleWithNewData);

        const memberLimit = service.getMemberLimit(formGroup) as any;

        expect(memberLimit).toMatchObject(sampleWithNewData);
      });

      it('should return NewMemberLimit for empty MemberLimit initial value', () => {
        const formGroup = service.createMemberLimitFormGroup();

        const memberLimit = service.getMemberLimit(formGroup) as any;

        expect(memberLimit).toMatchObject({});
      });

      it('should return IMemberLimit', () => {
        const formGroup = service.createMemberLimitFormGroup(sampleWithRequiredData);

        const memberLimit = service.getMemberLimit(formGroup) as any;

        expect(memberLimit).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMemberLimit should not enable id FormControl', () => {
        const formGroup = service.createMemberLimitFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMemberLimit should disable id FormControl', () => {
        const formGroup = service.createMemberLimitFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
