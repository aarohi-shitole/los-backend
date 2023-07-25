import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../org-prerequisite-doc.test-samples';

import { OrgPrerequisiteDocFormService } from './org-prerequisite-doc-form.service';

describe('OrgPrerequisiteDoc Form Service', () => {
  let service: OrgPrerequisiteDocFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OrgPrerequisiteDocFormService);
  });

  describe('Service methods', () => {
    describe('createOrgPrerequisiteDocFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createOrgPrerequisiteDocFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            docDesc: expect.any(Object),
            docName: expect.any(Object),
            docCatagory: expect.any(Object),
            ismandatory: expect.any(Object),
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
            product: expect.any(Object),
            organisation: expect.any(Object),
          })
        );
      });

      it('passing IOrgPrerequisiteDoc should create a new form with FormGroup', () => {
        const formGroup = service.createOrgPrerequisiteDocFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            docDesc: expect.any(Object),
            docName: expect.any(Object),
            docCatagory: expect.any(Object),
            ismandatory: expect.any(Object),
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
            product: expect.any(Object),
            organisation: expect.any(Object),
          })
        );
      });
    });

    describe('getOrgPrerequisiteDoc', () => {
      it('should return NewOrgPrerequisiteDoc for default OrgPrerequisiteDoc initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createOrgPrerequisiteDocFormGroup(sampleWithNewData);

        const orgPrerequisiteDoc = service.getOrgPrerequisiteDoc(formGroup) as any;

        expect(orgPrerequisiteDoc).toMatchObject(sampleWithNewData);
      });

      it('should return NewOrgPrerequisiteDoc for empty OrgPrerequisiteDoc initial value', () => {
        const formGroup = service.createOrgPrerequisiteDocFormGroup();

        const orgPrerequisiteDoc = service.getOrgPrerequisiteDoc(formGroup) as any;

        expect(orgPrerequisiteDoc).toMatchObject({});
      });

      it('should return IOrgPrerequisiteDoc', () => {
        const formGroup = service.createOrgPrerequisiteDocFormGroup(sampleWithRequiredData);

        const orgPrerequisiteDoc = service.getOrgPrerequisiteDoc(formGroup) as any;

        expect(orgPrerequisiteDoc).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IOrgPrerequisiteDoc should not enable id FormControl', () => {
        const formGroup = service.createOrgPrerequisiteDocFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewOrgPrerequisiteDoc should disable id FormControl', () => {
        const formGroup = service.createOrgPrerequisiteDocFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
