import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../member-assets.test-samples';

import { MemberAssetsFormService } from './member-assets-form.service';

describe('MemberAssets Form Service', () => {
  let service: MemberAssetsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MemberAssetsFormService);
  });

  describe('Service methods', () => {
    describe('createMemberAssetsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMemberAssetsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            assetCost: expect.any(Object),
            assetType: expect.any(Object),
            areaInSqFt: expect.any(Object),
            assetNature: expect.any(Object),
            address: expect.any(Object),
            landMark: expect.any(Object),
            assetOwner: expect.any(Object),
            completionYear: expect.any(Object),
            marketValue: expect.any(Object),
            isInsured: expect.any(Object),
            isUnderUsed: expect.any(Object),
            isOwned: expect.any(Object),
            landType: expect.any(Object),
            landGatNo: expect.any(Object),
            landAreaInHector: expect.any(Object),
            jindagiPatrakNo: expect.any(Object),
            jindagiAmount: expect.any(Object),
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
            freeField6: expect.any(Object),
            member: expect.any(Object),
            loanApplications: expect.any(Object),
          })
        );
      });

      it('passing IMemberAssets should create a new form with FormGroup', () => {
        const formGroup = service.createMemberAssetsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            assetCost: expect.any(Object),
            assetType: expect.any(Object),
            areaInSqFt: expect.any(Object),
            assetNature: expect.any(Object),
            address: expect.any(Object),
            landMark: expect.any(Object),
            assetOwner: expect.any(Object),
            completionYear: expect.any(Object),
            marketValue: expect.any(Object),
            isInsured: expect.any(Object),
            isUnderUsed: expect.any(Object),
            isOwned: expect.any(Object),
            landType: expect.any(Object),
            landGatNo: expect.any(Object),
            landAreaInHector: expect.any(Object),
            jindagiPatrakNo: expect.any(Object),
            jindagiAmount: expect.any(Object),
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
            freeField6: expect.any(Object),
            member: expect.any(Object),
            loanApplications: expect.any(Object),
          })
        );
      });
    });

    describe('getMemberAssets', () => {
      it('should return NewMemberAssets for default MemberAssets initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createMemberAssetsFormGroup(sampleWithNewData);

        const memberAssets = service.getMemberAssets(formGroup) as any;

        expect(memberAssets).toMatchObject(sampleWithNewData);
      });

      it('should return NewMemberAssets for empty MemberAssets initial value', () => {
        const formGroup = service.createMemberAssetsFormGroup();

        const memberAssets = service.getMemberAssets(formGroup) as any;

        expect(memberAssets).toMatchObject({});
      });

      it('should return IMemberAssets', () => {
        const formGroup = service.createMemberAssetsFormGroup(sampleWithRequiredData);

        const memberAssets = service.getMemberAssets(formGroup) as any;

        expect(memberAssets).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMemberAssets should not enable id FormControl', () => {
        const formGroup = service.createMemberAssetsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMemberAssets should disable id FormControl', () => {
        const formGroup = service.createMemberAssetsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
