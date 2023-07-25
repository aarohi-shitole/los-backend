import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../loan-disbursement.test-samples';

import { LoanDisbursementFormService } from './loan-disbursement-form.service';

describe('LoanDisbursement Form Service', () => {
  let service: LoanDisbursementFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoanDisbursementFormService);
  });

  describe('Service methods', () => {
    describe('createLoanDisbursementFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createLoanDisbursementFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dtDisbDate: expect.any(Object),
            accountNo: expect.any(Object),
            ifscCode: expect.any(Object),
            disbAmount: expect.any(Object),
            disbuNumber: expect.any(Object),
            disbursementStatus: expect.any(Object),
            paymentMode: expect.any(Object),
            utrNo: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            freeField4: expect.any(Object),
            freeField5: expect.any(Object),
            freeField6: expect.any(Object),
            product: expect.any(Object),
            securityUser: expect.any(Object),
            loanAccount: expect.any(Object),
          })
        );
      });

      it('passing ILoanDisbursement should create a new form with FormGroup', () => {
        const formGroup = service.createLoanDisbursementFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dtDisbDate: expect.any(Object),
            accountNo: expect.any(Object),
            ifscCode: expect.any(Object),
            disbAmount: expect.any(Object),
            disbuNumber: expect.any(Object),
            disbursementStatus: expect.any(Object),
            paymentMode: expect.any(Object),
            utrNo: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            freeField4: expect.any(Object),
            freeField5: expect.any(Object),
            freeField6: expect.any(Object),
            product: expect.any(Object),
            securityUser: expect.any(Object),
            loanAccount: expect.any(Object),
          })
        );
      });
    });

    describe('getLoanDisbursement', () => {
      it('should return NewLoanDisbursement for default LoanDisbursement initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createLoanDisbursementFormGroup(sampleWithNewData);

        const loanDisbursement = service.getLoanDisbursement(formGroup) as any;

        expect(loanDisbursement).toMatchObject(sampleWithNewData);
      });

      it('should return NewLoanDisbursement for empty LoanDisbursement initial value', () => {
        const formGroup = service.createLoanDisbursementFormGroup();

        const loanDisbursement = service.getLoanDisbursement(formGroup) as any;

        expect(loanDisbursement).toMatchObject({});
      });

      it('should return ILoanDisbursement', () => {
        const formGroup = service.createLoanDisbursementFormGroup(sampleWithRequiredData);

        const loanDisbursement = service.getLoanDisbursement(formGroup) as any;

        expect(loanDisbursement).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ILoanDisbursement should not enable id FormControl', () => {
        const formGroup = service.createLoanDisbursementFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewLoanDisbursement should disable id FormControl', () => {
        const formGroup = service.createLoanDisbursementFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
