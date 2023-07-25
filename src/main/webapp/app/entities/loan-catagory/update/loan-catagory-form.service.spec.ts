import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../loan-catagory.test-samples';

import { LoanCatagoryFormService } from './loan-catagory-form.service';

describe('LoanCatagory Form Service', () => {
  let service: LoanCatagoryFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoanCatagoryFormService);
  });

  describe('Service methods', () => {
    describe('createLoanCatagoryFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createLoanCatagoryFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            productName: expect.any(Object),
            decription: expect.any(Object),
            value: expect.any(Object),
            code: expect.any(Object),
            offer: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            isDeleted: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            freeField4: expect.any(Object),
          })
        );
      });

      it('passing ILoanCatagory should create a new form with FormGroup', () => {
        const formGroup = service.createLoanCatagoryFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            productName: expect.any(Object),
            decription: expect.any(Object),
            value: expect.any(Object),
            code: expect.any(Object),
            offer: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            isDeleted: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            freeField4: expect.any(Object),
          })
        );
      });
    });

    describe('getLoanCatagory', () => {
      it('should return NewLoanCatagory for default LoanCatagory initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createLoanCatagoryFormGroup(sampleWithNewData);

        const loanCatagory = service.getLoanCatagory(formGroup) as any;

        expect(loanCatagory).toMatchObject(sampleWithNewData);
      });

      it('should return NewLoanCatagory for empty LoanCatagory initial value', () => {
        const formGroup = service.createLoanCatagoryFormGroup();

        const loanCatagory = service.getLoanCatagory(formGroup) as any;

        expect(loanCatagory).toMatchObject({});
      });

      it('should return ILoanCatagory', () => {
        const formGroup = service.createLoanCatagoryFormGroup(sampleWithRequiredData);

        const loanCatagory = service.getLoanCatagory(formGroup) as any;

        expect(loanCatagory).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ILoanCatagory should not enable id FormControl', () => {
        const formGroup = service.createLoanCatagoryFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewLoanCatagory should disable id FormControl', () => {
        const formGroup = service.createLoanCatagoryFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
