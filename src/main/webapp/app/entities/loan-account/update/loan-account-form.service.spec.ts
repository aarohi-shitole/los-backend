import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../loan-account.test-samples';

import { LoanAccountFormService } from './loan-account-form.service';

describe('LoanAccount Form Service', () => {
  let service: LoanAccountFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoanAccountFormService);
  });

  describe('Service methods', () => {
    describe('createLoanAccountFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createLoanAccountFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            loanAmount: expect.any(Object),
            applicationNo: expect.any(Object),
            status: expect.any(Object),
            loanStartDate: expect.any(Object),
            loanEndDate: expect.any(Object),
            loanPlannedClosureDate: expect.any(Object),
            loanCloserDate: expect.any(Object),
            emiStartDate: expect.any(Object),
            loanNpaClass: expect.any(Object),
            parentAccHeadCode: expect.any(Object),
            loanAccountName: expect.any(Object),
            disbursementAmt: expect.any(Object),
            disbursementStatus: expect.any(Object),
            repaymentPeriod: expect.any(Object),
            year: expect.any(Object),
            processingFee: expect.any(Object),
            moratorium: expect.any(Object),
            roi: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            freeField4: expect.any(Object),
            freeField5: expect.any(Object),
            freeField6: expect.any(Object),
            loanApplications: expect.any(Object),
            member: expect.any(Object),
            product: expect.any(Object),
          })
        );
      });

      it('passing ILoanAccount should create a new form with FormGroup', () => {
        const formGroup = service.createLoanAccountFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            loanAmount: expect.any(Object),
            applicationNo: expect.any(Object),
            status: expect.any(Object),
            loanStartDate: expect.any(Object),
            loanEndDate: expect.any(Object),
            loanPlannedClosureDate: expect.any(Object),
            loanCloserDate: expect.any(Object),
            emiStartDate: expect.any(Object),
            loanNpaClass: expect.any(Object),
            parentAccHeadCode: expect.any(Object),
            loanAccountName: expect.any(Object),
            disbursementAmt: expect.any(Object),
            disbursementStatus: expect.any(Object),
            repaymentPeriod: expect.any(Object),
            year: expect.any(Object),
            processingFee: expect.any(Object),
            moratorium: expect.any(Object),
            roi: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            freeField4: expect.any(Object),
            freeField5: expect.any(Object),
            freeField6: expect.any(Object),
            loanApplications: expect.any(Object),
            member: expect.any(Object),
            product: expect.any(Object),
          })
        );
      });
    });

    describe('getLoanAccount', () => {
      it('should return NewLoanAccount for default LoanAccount initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createLoanAccountFormGroup(sampleWithNewData);

        const loanAccount = service.getLoanAccount(formGroup) as any;

        expect(loanAccount).toMatchObject(sampleWithNewData);
      });

      it('should return NewLoanAccount for empty LoanAccount initial value', () => {
        const formGroup = service.createLoanAccountFormGroup();

        const loanAccount = service.getLoanAccount(formGroup) as any;

        expect(loanAccount).toMatchObject({});
      });

      it('should return ILoanAccount', () => {
        const formGroup = service.createLoanAccountFormGroup(sampleWithRequiredData);

        const loanAccount = service.getLoanAccount(formGroup) as any;

        expect(loanAccount).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ILoanAccount should not enable id FormControl', () => {
        const formGroup = service.createLoanAccountFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewLoanAccount should disable id FormControl', () => {
        const formGroup = service.createLoanAccountFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
