import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../master-agreement.test-samples';

import { MasterAgreementFormService } from './master-agreement-form.service';

describe('MasterAgreement Form Service', () => {
  let service: MasterAgreementFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MasterAgreementFormService);
  });

  describe('Service methods', () => {
    describe('createMasterAgreementFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMasterAgreementFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            memberId: expect.any(Object),
            portfolioCode: expect.any(Object),
            productCode: expect.any(Object),
            homeBranch: expect.any(Object),
            servBranch: expect.any(Object),
            homeState: expect.any(Object),
            servState: expect.any(Object),
            gstExempted: expect.any(Object),
            prefRepayMode: expect.any(Object),
            tdsCode: expect.any(Object),
            tdsRate: expect.any(Object),
            sanctionedAmount: expect.any(Object),
            originationApplnNo: expect.any(Object),
            cycleDay: expect.any(Object),
            tenor: expect.any(Object),
            interestRate: expect.any(Object),
            repayFreq: expect.any(Object),
            isDeleted: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            freeField4: expect.any(Object),
          })
        );
      });

      it('passing IMasterAgreement should create a new form with FormGroup', () => {
        const formGroup = service.createMasterAgreementFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            memberId: expect.any(Object),
            portfolioCode: expect.any(Object),
            productCode: expect.any(Object),
            homeBranch: expect.any(Object),
            servBranch: expect.any(Object),
            homeState: expect.any(Object),
            servState: expect.any(Object),
            gstExempted: expect.any(Object),
            prefRepayMode: expect.any(Object),
            tdsCode: expect.any(Object),
            tdsRate: expect.any(Object),
            sanctionedAmount: expect.any(Object),
            originationApplnNo: expect.any(Object),
            cycleDay: expect.any(Object),
            tenor: expect.any(Object),
            interestRate: expect.any(Object),
            repayFreq: expect.any(Object),
            isDeleted: expect.any(Object),
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

    describe('getMasterAgreement', () => {
      it('should return NewMasterAgreement for default MasterAgreement initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createMasterAgreementFormGroup(sampleWithNewData);

        const masterAgreement = service.getMasterAgreement(formGroup) as any;

        expect(masterAgreement).toMatchObject(sampleWithNewData);
      });

      it('should return NewMasterAgreement for empty MasterAgreement initial value', () => {
        const formGroup = service.createMasterAgreementFormGroup();

        const masterAgreement = service.getMasterAgreement(formGroup) as any;

        expect(masterAgreement).toMatchObject({});
      });

      it('should return IMasterAgreement', () => {
        const formGroup = service.createMasterAgreementFormGroup(sampleWithRequiredData);

        const masterAgreement = service.getMasterAgreement(formGroup) as any;

        expect(masterAgreement).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMasterAgreement should not enable id FormControl', () => {
        const formGroup = service.createMasterAgreementFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMasterAgreement should disable id FormControl', () => {
        const formGroup = service.createMasterAgreementFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
