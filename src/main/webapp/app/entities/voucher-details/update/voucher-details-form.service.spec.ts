import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../voucher-details.test-samples';

import { VoucherDetailsFormService } from './voucher-details-form.service';

describe('VoucherDetails Form Service', () => {
  let service: VoucherDetailsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VoucherDetailsFormService);
  });

  describe('Service methods', () => {
    describe('createVoucherDetailsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createVoucherDetailsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            accHeadCode: expect.any(Object),
            creditAccount: expect.any(Object),
            debitAccount: expect.any(Object),
            transferAmt: expect.any(Object),
            isDeleted: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            freeField4: expect.any(Object),
            freeField5: expect.any(Object),
            freeField6: expect.any(Object),
          })
        );
      });

      it('passing IVoucherDetails should create a new form with FormGroup', () => {
        const formGroup = service.createVoucherDetailsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            accHeadCode: expect.any(Object),
            creditAccount: expect.any(Object),
            debitAccount: expect.any(Object),
            transferAmt: expect.any(Object),
            isDeleted: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            freeField4: expect.any(Object),
            freeField5: expect.any(Object),
            freeField6: expect.any(Object),
          })
        );
      });
    });

    describe('getVoucherDetails', () => {
      it('should return NewVoucherDetails for default VoucherDetails initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createVoucherDetailsFormGroup(sampleWithNewData);

        const voucherDetails = service.getVoucherDetails(formGroup) as any;

        expect(voucherDetails).toMatchObject(sampleWithNewData);
      });

      it('should return NewVoucherDetails for empty VoucherDetails initial value', () => {
        const formGroup = service.createVoucherDetailsFormGroup();

        const voucherDetails = service.getVoucherDetails(formGroup) as any;

        expect(voucherDetails).toMatchObject({});
      });

      it('should return IVoucherDetails', () => {
        const formGroup = service.createVoucherDetailsFormGroup(sampleWithRequiredData);

        const voucherDetails = service.getVoucherDetails(formGroup) as any;

        expect(voucherDetails).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IVoucherDetails should not enable id FormControl', () => {
        const formGroup = service.createVoucherDetailsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewVoucherDetails should disable id FormControl', () => {
        const formGroup = service.createVoucherDetailsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
