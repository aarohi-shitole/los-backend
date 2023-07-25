import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../employement-details.test-samples';

import { EmployementDetailsFormService } from './employement-details-form.service';

describe('EmployementDetails Form Service', () => {
  let service: EmployementDetailsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EmployementDetailsFormService);
  });

  describe('Service methods', () => {
    describe('createEmployementDetailsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEmployementDetailsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            type: expect.any(Object),
            employerName: expect.any(Object),
            status: expect.any(Object),
            designation: expect.any(Object),
            duration: expect.any(Object),
            employerAdd: expect.any(Object),
            prevCompanyName: expect.any(Object),
            prevCompanyduration: expect.any(Object),
            orgType: expect.any(Object),
            constitutionType: expect.any(Object),
            industryType: expect.any(Object),
            businessRegNo: expect.any(Object),
            compOwnerShip: expect.any(Object),
            partnerName1: expect.any(Object),
            partnerName2: expect.any(Object),
            partnerName3: expect.any(Object),
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

      it('passing IEmployementDetails should create a new form with FormGroup', () => {
        const formGroup = service.createEmployementDetailsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            type: expect.any(Object),
            employerName: expect.any(Object),
            status: expect.any(Object),
            designation: expect.any(Object),
            duration: expect.any(Object),
            employerAdd: expect.any(Object),
            prevCompanyName: expect.any(Object),
            prevCompanyduration: expect.any(Object),
            orgType: expect.any(Object),
            constitutionType: expect.any(Object),
            industryType: expect.any(Object),
            businessRegNo: expect.any(Object),
            compOwnerShip: expect.any(Object),
            partnerName1: expect.any(Object),
            partnerName2: expect.any(Object),
            partnerName3: expect.any(Object),
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

    describe('getEmployementDetails', () => {
      it('should return NewEmployementDetails for default EmployementDetails initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createEmployementDetailsFormGroup(sampleWithNewData);

        const employementDetails = service.getEmployementDetails(formGroup) as any;

        expect(employementDetails).toMatchObject(sampleWithNewData);
      });

      it('should return NewEmployementDetails for empty EmployementDetails initial value', () => {
        const formGroup = service.createEmployementDetailsFormGroup();

        const employementDetails = service.getEmployementDetails(formGroup) as any;

        expect(employementDetails).toMatchObject({});
      });

      it('should return IEmployementDetails', () => {
        const formGroup = service.createEmployementDetailsFormGroup(sampleWithRequiredData);

        const employementDetails = service.getEmployementDetails(formGroup) as any;

        expect(employementDetails).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEmployementDetails should not enable id FormControl', () => {
        const formGroup = service.createEmployementDetailsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEmployementDetails should disable id FormControl', () => {
        const formGroup = service.createEmployementDetailsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
