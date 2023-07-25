import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../epay.test-samples';

import { EpayFormService } from './epay-form.service';

describe('Epay Form Service', () => {
  let service: EpayFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EpayFormService);
  });

  describe('Service methods', () => {
    describe('createEpayFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEpayFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            instrumentType: expect.any(Object),
            dtFromDate: expect.any(Object),
            dtToDate: expect.any(Object),
            bankCode: expect.any(Object),
            bankBranchCode: expect.any(Object),
            accountType: expect.any(Object),
            accountNo: expect.any(Object),
            maxCeilAmount: expect.any(Object),
            installmentAmount: expect.any(Object),
            maxInstallmentAmount: expect.any(Object),
            mandateRefNo: expect.any(Object),
            depositBank: expect.any(Object),
            mandateType: expect.any(Object),
            frequency: expect.any(Object),
            ifscCode: expect.any(Object),
            utrNo: expect.any(Object),
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

      it('passing IEpay should create a new form with FormGroup', () => {
        const formGroup = service.createEpayFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            instrumentType: expect.any(Object),
            dtFromDate: expect.any(Object),
            dtToDate: expect.any(Object),
            bankCode: expect.any(Object),
            bankBranchCode: expect.any(Object),
            accountType: expect.any(Object),
            accountNo: expect.any(Object),
            maxCeilAmount: expect.any(Object),
            installmentAmount: expect.any(Object),
            maxInstallmentAmount: expect.any(Object),
            mandateRefNo: expect.any(Object),
            depositBank: expect.any(Object),
            mandateType: expect.any(Object),
            frequency: expect.any(Object),
            ifscCode: expect.any(Object),
            utrNo: expect.any(Object),
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

    describe('getEpay', () => {
      it('should return NewEpay for default Epay initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createEpayFormGroup(sampleWithNewData);

        const epay = service.getEpay(formGroup) as any;

        expect(epay).toMatchObject(sampleWithNewData);
      });

      it('should return NewEpay for empty Epay initial value', () => {
        const formGroup = service.createEpayFormGroup();

        const epay = service.getEpay(formGroup) as any;

        expect(epay).toMatchObject({});
      });

      it('should return IEpay', () => {
        const formGroup = service.createEpayFormGroup(sampleWithRequiredData);

        const epay = service.getEpay(formGroup) as any;

        expect(epay).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEpay should not enable id FormControl', () => {
        const formGroup = service.createEpayFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEpay should disable id FormControl', () => {
        const formGroup = service.createEpayFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
