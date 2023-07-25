import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../loan-repayment-details.test-samples';

import { LoanRepaymentDetailsFormService } from './loan-repayment-details-form.service';

describe('LoanRepaymentDetails Form Service', () => {
  let service: LoanRepaymentDetailsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoanRepaymentDetailsFormService);
  });

  describe('Service methods', () => {
    describe('createLoanRepaymentDetailsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createLoanRepaymentDetailsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            repaymentDate: expect.any(Object),
            totalRepaymentAmt: expect.any(Object),
            installmentNumber: expect.any(Object),
            principlePaidAmt: expect.any(Object),
            interestPaidAmt: expect.any(Object),
            surChargeAmt: expect.any(Object),
            overDueAmt: expect.any(Object),
            balanceInterestAmt: expect.any(Object),
            balancePrincipleAmt: expect.any(Object),
            paymentMode: expect.any(Object),
            foreClosureChargeAmt: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            freeField4: expect.any(Object),
            freeField5: expect.any(Object),
            freeField6: expect.any(Object),
            loanAccount: expect.any(Object),
          })
        );
      });

      it('passing ILoanRepaymentDetails should create a new form with FormGroup', () => {
        const formGroup = service.createLoanRepaymentDetailsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            repaymentDate: expect.any(Object),
            totalRepaymentAmt: expect.any(Object),
            installmentNumber: expect.any(Object),
            principlePaidAmt: expect.any(Object),
            interestPaidAmt: expect.any(Object),
            surChargeAmt: expect.any(Object),
            overDueAmt: expect.any(Object),
            balanceInterestAmt: expect.any(Object),
            balancePrincipleAmt: expect.any(Object),
            paymentMode: expect.any(Object),
            foreClosureChargeAmt: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            freeField4: expect.any(Object),
            freeField5: expect.any(Object),
            freeField6: expect.any(Object),
            loanAccount: expect.any(Object),
          })
        );
      });
    });

    describe('getLoanRepaymentDetails', () => {
      it('should return NewLoanRepaymentDetails for default LoanRepaymentDetails initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createLoanRepaymentDetailsFormGroup(sampleWithNewData);

        const loanRepaymentDetails = service.getLoanRepaymentDetails(formGroup) as any;

        expect(loanRepaymentDetails).toMatchObject(sampleWithNewData);
      });

      it('should return NewLoanRepaymentDetails for empty LoanRepaymentDetails initial value', () => {
        const formGroup = service.createLoanRepaymentDetailsFormGroup();

        const loanRepaymentDetails = service.getLoanRepaymentDetails(formGroup) as any;

        expect(loanRepaymentDetails).toMatchObject({});
      });

      it('should return ILoanRepaymentDetails', () => {
        const formGroup = service.createLoanRepaymentDetailsFormGroup(sampleWithRequiredData);

        const loanRepaymentDetails = service.getLoanRepaymentDetails(formGroup) as any;

        expect(loanRepaymentDetails).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ILoanRepaymentDetails should not enable id FormControl', () => {
        const formGroup = service.createLoanRepaymentDetailsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewLoanRepaymentDetails should disable id FormControl', () => {
        const formGroup = service.createLoanRepaymentDetailsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
