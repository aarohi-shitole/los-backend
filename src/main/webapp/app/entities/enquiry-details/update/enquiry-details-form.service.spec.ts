import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../enquiry-details.test-samples';

import { EnquiryDetailsFormService } from './enquiry-details-form.service';

describe('EnquiryDetails Form Service', () => {
  let service: EnquiryDetailsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EnquiryDetailsFormService);
  });

  describe('Service methods', () => {
    describe('createEnquiryDetailsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEnquiryDetailsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            customerName: expect.any(Object),
            subName: expect.any(Object),
            purpose: expect.any(Object),
            mobileNo: expect.any(Object),
            amount: expect.any(Object),
            refrenceNo: expect.any(Object),
            isDeleted: expect.any(Object),
            isActive: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            freeField4: expect.any(Object),
            state: expect.any(Object),
            district: expect.any(Object),
            taluka: expect.any(Object),
            city: expect.any(Object),
            product: expect.any(Object),
          })
        );
      });

      it('passing IEnquiryDetails should create a new form with FormGroup', () => {
        const formGroup = service.createEnquiryDetailsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            customerName: expect.any(Object),
            subName: expect.any(Object),
            purpose: expect.any(Object),
            mobileNo: expect.any(Object),
            amount: expect.any(Object),
            refrenceNo: expect.any(Object),
            isDeleted: expect.any(Object),
            isActive: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            freeField4: expect.any(Object),
            state: expect.any(Object),
            district: expect.any(Object),
            taluka: expect.any(Object),
            city: expect.any(Object),
            product: expect.any(Object),
          })
        );
      });
    });

    describe('getEnquiryDetails', () => {
      it('should return NewEnquiryDetails for default EnquiryDetails initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createEnquiryDetailsFormGroup(sampleWithNewData);

        const enquiryDetails = service.getEnquiryDetails(formGroup) as any;

        expect(enquiryDetails).toMatchObject(sampleWithNewData);
      });

      it('should return NewEnquiryDetails for empty EnquiryDetails initial value', () => {
        const formGroup = service.createEnquiryDetailsFormGroup();

        const enquiryDetails = service.getEnquiryDetails(formGroup) as any;

        expect(enquiryDetails).toMatchObject({});
      });

      it('should return IEnquiryDetails', () => {
        const formGroup = service.createEnquiryDetailsFormGroup(sampleWithRequiredData);

        const enquiryDetails = service.getEnquiryDetails(formGroup) as any;

        expect(enquiryDetails).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEnquiryDetails should not enable id FormControl', () => {
        const formGroup = service.createEnquiryDetailsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEnquiryDetails should disable id FormControl', () => {
        const formGroup = service.createEnquiryDetailsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
