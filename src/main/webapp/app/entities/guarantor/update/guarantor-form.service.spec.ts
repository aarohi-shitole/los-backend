import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../guarantor.test-samples';

import { GuarantorFormService } from './guarantor-form.service';

describe('Guarantor Form Service', () => {
  let service: GuarantorFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GuarantorFormService);
  });

  describe('Service methods', () => {
    describe('createGuarantorFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createGuarantorFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            title: expect.any(Object),
            firstName: expect.any(Object),
            middleName: expect.any(Object),
            lastName: expect.any(Object),
            membershipNo: expect.any(Object),
            gender: expect.any(Object),
            dob: expect.any(Object),
            email: expect.any(Object),
            mobileNo: expect.any(Object),
            houseOwner: expect.any(Object),
            occupation: expect.any(Object),
            employerNameAdd: expect.any(Object),
            soclibilAmt: expect.any(Object),
            soclibilType: expect.any(Object),
            otherlibilAmt: expect.any(Object),
            otherlibilType: expect.any(Object),
            aadharCardNo: expect.any(Object),
            panCard: expect.any(Object),
            maritalStatus: expect.any(Object),
            hasAdharVerified: expect.any(Object),
            hasPanVerified: expect.any(Object),
            numberOfAssets: expect.any(Object),
            grossAnnualInc: expect.any(Object),
            netIncome: expect.any(Object),
            isIncomeTaxPayer: expect.any(Object),
            isActive: expect.any(Object),
            isDeleted: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            freeField4: expect.any(Object),
            freeField5: expect.any(Object),
            freeField6: expect.any(Object),
            memberAssets: expect.any(Object),
            employementDetails: expect.any(Object),
            member: expect.any(Object),
          })
        );
      });

      it('passing IGuarantor should create a new form with FormGroup', () => {
        const formGroup = service.createGuarantorFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            title: expect.any(Object),
            firstName: expect.any(Object),
            middleName: expect.any(Object),
            lastName: expect.any(Object),
            membershipNo: expect.any(Object),
            gender: expect.any(Object),
            dob: expect.any(Object),
            email: expect.any(Object),
            mobileNo: expect.any(Object),
            houseOwner: expect.any(Object),
            occupation: expect.any(Object),
            employerNameAdd: expect.any(Object),
            soclibilAmt: expect.any(Object),
            soclibilType: expect.any(Object),
            otherlibilAmt: expect.any(Object),
            otherlibilType: expect.any(Object),
            aadharCardNo: expect.any(Object),
            panCard: expect.any(Object),
            maritalStatus: expect.any(Object),
            hasAdharVerified: expect.any(Object),
            hasPanVerified: expect.any(Object),
            numberOfAssets: expect.any(Object),
            grossAnnualInc: expect.any(Object),
            netIncome: expect.any(Object),
            isIncomeTaxPayer: expect.any(Object),
            isActive: expect.any(Object),
            isDeleted: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            freeField4: expect.any(Object),
            freeField5: expect.any(Object),
            freeField6: expect.any(Object),
            memberAssets: expect.any(Object),
            employementDetails: expect.any(Object),
            member: expect.any(Object),
          })
        );
      });
    });

    describe('getGuarantor', () => {
      it('should return NewGuarantor for default Guarantor initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createGuarantorFormGroup(sampleWithNewData);

        const guarantor = service.getGuarantor(formGroup) as any;

        expect(guarantor).toMatchObject(sampleWithNewData);
      });

      it('should return NewGuarantor for empty Guarantor initial value', () => {
        const formGroup = service.createGuarantorFormGroup();

        const guarantor = service.getGuarantor(formGroup) as any;

        expect(guarantor).toMatchObject({});
      });

      it('should return IGuarantor', () => {
        const formGroup = service.createGuarantorFormGroup(sampleWithRequiredData);

        const guarantor = service.getGuarantor(formGroup) as any;

        expect(guarantor).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IGuarantor should not enable id FormControl', () => {
        const formGroup = service.createGuarantorFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewGuarantor should disable id FormControl', () => {
        const formGroup = service.createGuarantorFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
