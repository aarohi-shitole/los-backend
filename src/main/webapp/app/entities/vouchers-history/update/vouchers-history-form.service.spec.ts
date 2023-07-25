import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../vouchers-history.test-samples';

import { VouchersHistoryFormService } from './vouchers-history-form.service';

describe('VouchersHistory Form Service', () => {
  let service: VouchersHistoryFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VouchersHistoryFormService);
  });

  describe('Service methods', () => {
    describe('createVouchersHistoryFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createVouchersHistoryFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            createdOn: expect.any(Object),
            createdBy: expect.any(Object),
            voucherDate: expect.any(Object),
            code: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            freeField4: expect.any(Object),
            freeField5: expect.any(Object),
            freeField6: expect.any(Object),
          })
        );
      });

      it('passing IVouchersHistory should create a new form with FormGroup', () => {
        const formGroup = service.createVouchersHistoryFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            createdOn: expect.any(Object),
            createdBy: expect.any(Object),
            voucherDate: expect.any(Object),
            code: expect.any(Object),
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

    describe('getVouchersHistory', () => {
      it('should return NewVouchersHistory for default VouchersHistory initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createVouchersHistoryFormGroup(sampleWithNewData);

        const vouchersHistory = service.getVouchersHistory(formGroup) as any;

        expect(vouchersHistory).toMatchObject(sampleWithNewData);
      });

      it('should return NewVouchersHistory for empty VouchersHistory initial value', () => {
        const formGroup = service.createVouchersHistoryFormGroup();

        const vouchersHistory = service.getVouchersHistory(formGroup) as any;

        expect(vouchersHistory).toMatchObject({});
      });

      it('should return IVouchersHistory', () => {
        const formGroup = service.createVouchersHistoryFormGroup(sampleWithRequiredData);

        const vouchersHistory = service.getVouchersHistory(formGroup) as any;

        expect(vouchersHistory).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IVouchersHistory should not enable id FormControl', () => {
        const formGroup = service.createVouchersHistoryFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewVouchersHistory should disable id FormControl', () => {
        const formGroup = service.createVouchersHistoryFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
