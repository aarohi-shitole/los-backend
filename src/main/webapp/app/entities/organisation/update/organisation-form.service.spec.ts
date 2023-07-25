import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../organisation.test-samples';

import { OrganisationFormService } from './organisation-form.service';

describe('Organisation Form Service', () => {
  let service: OrganisationFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OrganisationFormService);
  });

  describe('Service methods', () => {
    describe('createOrganisationFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createOrganisationFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            orgName: expect.any(Object),
            description: expect.any(Object),
            regNumber: expect.any(Object),
            gstin: expect.any(Object),
            pan: expect.any(Object),
            tan: expect.any(Object),
            phone: expect.any(Object),
            email: expect.any(Object),
            webSite: expect.any(Object),
            fax: expect.any(Object),
            isActivate: expect.any(Object),
            orgType: expect.any(Object),
            createdOn: expect.any(Object),
            createdBy: expect.any(Object),
            isDeleted: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            freeField4: expect.any(Object),
            freeField5: expect.any(Object),
            address: expect.any(Object),
          })
        );
      });

      it('passing IOrganisation should create a new form with FormGroup', () => {
        const formGroup = service.createOrganisationFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            orgName: expect.any(Object),
            description: expect.any(Object),
            regNumber: expect.any(Object),
            gstin: expect.any(Object),
            pan: expect.any(Object),
            tan: expect.any(Object),
            phone: expect.any(Object),
            email: expect.any(Object),
            webSite: expect.any(Object),
            fax: expect.any(Object),
            isActivate: expect.any(Object),
            orgType: expect.any(Object),
            createdOn: expect.any(Object),
            createdBy: expect.any(Object),
            isDeleted: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            freeField4: expect.any(Object),
            freeField5: expect.any(Object),
            address: expect.any(Object),
          })
        );
      });
    });

    describe('getOrganisation', () => {
      it('should return NewOrganisation for default Organisation initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createOrganisationFormGroup(sampleWithNewData);

        const organisation = service.getOrganisation(formGroup) as any;

        expect(organisation).toMatchObject(sampleWithNewData);
      });

      it('should return NewOrganisation for empty Organisation initial value', () => {
        const formGroup = service.createOrganisationFormGroup();

        const organisation = service.getOrganisation(formGroup) as any;

        expect(organisation).toMatchObject({});
      });

      it('should return IOrganisation', () => {
        const formGroup = service.createOrganisationFormGroup(sampleWithRequiredData);

        const organisation = service.getOrganisation(formGroup) as any;

        expect(organisation).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IOrganisation should not enable id FormControl', () => {
        const formGroup = service.createOrganisationFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewOrganisation should disable id FormControl', () => {
        const formGroup = service.createOrganisationFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
