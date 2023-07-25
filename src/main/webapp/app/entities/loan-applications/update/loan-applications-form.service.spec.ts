import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../loan-applications.test-samples';

import { LoanApplicationsFormService } from './loan-applications-form.service';

describe('LoanApplications Form Service', () => {
  let service: LoanApplicationsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoanApplicationsFormService);
  });

  describe('Service methods', () => {
    describe('createLoanApplicationsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createLoanApplicationsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            applicationNo: expect.any(Object),
            demandAmount: expect.any(Object),
            loanPurpose: expect.any(Object),
            status: expect.any(Object),
            demandedLandAreaInHector: expect.any(Object),
            seasonOfCropLoan: expect.any(Object),
            loanSteps: expect.any(Object),
            isInsured: expect.any(Object),
            loanBenefitingArea: expect.any(Object),
            costOfInvestment: expect.any(Object),
            mortgageDeedNo: expect.any(Object),
            mortgageDate: expect.any(Object),
            extentMorgageValue: expect.any(Object),
            downPaymentAmt: expect.any(Object),
            ltvRatio: expect.any(Object),
            processingFee: expect.any(Object),
            penalInterest: expect.any(Object),
            moratorium: expect.any(Object),
            roi: expect.any(Object),
            commityAmt: expect.any(Object),
            commityRoi: expect.any(Object),
            sectionAmt: expect.any(Object),
            senctionRoi: expect.any(Object),
            year: expect.any(Object),
            assignedTo: expect.any(Object),
            assignedFrom: expect.any(Object),
            securityDocUrl: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            freeField4: expect.any(Object),
            freeField5: expect.any(Object),
            freeField6: expect.any(Object),
            freeField7: expect.any(Object),
            member: expect.any(Object),
            securityUser: expect.any(Object),
            product: expect.any(Object),
          })
        );
      });

      it('passing ILoanApplications should create a new form with FormGroup', () => {
        const formGroup = service.createLoanApplicationsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            applicationNo: expect.any(Object),
            demandAmount: expect.any(Object),
            loanPurpose: expect.any(Object),
            status: expect.any(Object),
            demandedLandAreaInHector: expect.any(Object),
            seasonOfCropLoan: expect.any(Object),
            loanSteps: expect.any(Object),
            isInsured: expect.any(Object),
            loanBenefitingArea: expect.any(Object),
            costOfInvestment: expect.any(Object),
            mortgageDeedNo: expect.any(Object),
            mortgageDate: expect.any(Object),
            extentMorgageValue: expect.any(Object),
            downPaymentAmt: expect.any(Object),
            ltvRatio: expect.any(Object),
            processingFee: expect.any(Object),
            penalInterest: expect.any(Object),
            moratorium: expect.any(Object),
            roi: expect.any(Object),
            commityAmt: expect.any(Object),
            commityRoi: expect.any(Object),
            sectionAmt: expect.any(Object),
            senctionRoi: expect.any(Object),
            year: expect.any(Object),
            assignedTo: expect.any(Object),
            assignedFrom: expect.any(Object),
            securityDocUrl: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            freeField4: expect.any(Object),
            freeField5: expect.any(Object),
            freeField6: expect.any(Object),
            freeField7: expect.any(Object),
            member: expect.any(Object),
            securityUser: expect.any(Object),
            product: expect.any(Object),
          })
        );
      });
    });

    describe('getLoanApplications', () => {
      it('should return NewLoanApplications for default LoanApplications initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createLoanApplicationsFormGroup(sampleWithNewData);

        const loanApplications = service.getLoanApplications(formGroup) as any;

        expect(loanApplications).toMatchObject(sampleWithNewData);
      });

      it('should return NewLoanApplications for empty LoanApplications initial value', () => {
        const formGroup = service.createLoanApplicationsFormGroup();

        const loanApplications = service.getLoanApplications(formGroup) as any;

        expect(loanApplications).toMatchObject({});
      });

      it('should return ILoanApplications', () => {
        const formGroup = service.createLoanApplicationsFormGroup(sampleWithRequiredData);

        const loanApplications = service.getLoanApplications(formGroup) as any;

        expect(loanApplications).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ILoanApplications should not enable id FormControl', () => {
        const formGroup = service.createLoanApplicationsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewLoanApplications should disable id FormControl', () => {
        const formGroup = service.createLoanApplicationsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
