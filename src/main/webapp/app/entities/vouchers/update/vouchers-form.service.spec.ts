import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../vouchers.test-samples';

import { VouchersFormService } from './vouchers-form.service';

describe('Vouchers Form Service', () => {
  let service: VouchersFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VouchersFormService);
  });

  describe('Service methods', () => {
    describe('createVouchersFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createVouchersFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            voucherDate: expect.any(Object),
            voucherNo: expect.any(Object),
            preparedBy: expect.any(Object),
            code: expect.any(Object),
            narration: expect.any(Object),
            authorisedBy: expect.any(Object),
            mode: expect.any(Object),
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

      it('passing IVouchers should create a new form with FormGroup', () => {
        const formGroup = service.createVouchersFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            voucherDate: expect.any(Object),
            voucherNo: expect.any(Object),
            preparedBy: expect.any(Object),
            code: expect.any(Object),
            narration: expect.any(Object),
            authorisedBy: expect.any(Object),
            mode: expect.any(Object),
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

    describe('getVouchers', () => {
      it('should return NewVouchers for default Vouchers initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createVouchersFormGroup(sampleWithNewData);

        const vouchers = service.getVouchers(formGroup) as any;

        expect(vouchers).toMatchObject(sampleWithNewData);
      });

      it('should return NewVouchers for empty Vouchers initial value', () => {
        const formGroup = service.createVouchersFormGroup();

        const vouchers = service.getVouchers(formGroup) as any;

        expect(vouchers).toMatchObject({});
      });

      it('should return IVouchers', () => {
        const formGroup = service.createVouchersFormGroup(sampleWithRequiredData);

        const vouchers = service.getVouchers(formGroup) as any;

        expect(vouchers).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IVouchers should not enable id FormControl', () => {
        const formGroup = service.createVouchersFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewVouchers should disable id FormControl', () => {
        const formGroup = service.createVouchersFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
