import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../account-head-code.test-samples';

import { AccountHeadCodeFormService } from './account-head-code-form.service';

describe('AccountHeadCode Form Service', () => {
  let service: AccountHeadCodeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AccountHeadCodeFormService);
  });

  describe('Service methods', () => {
    describe('createAccountHeadCodeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAccountHeadCodeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            type: expect.any(Object),
            value: expect.any(Object),
            headCodeName: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            isDeleted: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            freeField4: expect.any(Object),
            freeField5: expect.any(Object),
            ledgerAccounts: expect.any(Object),
          })
        );
      });

      it('passing IAccountHeadCode should create a new form with FormGroup', () => {
        const formGroup = service.createAccountHeadCodeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            type: expect.any(Object),
            value: expect.any(Object),
            headCodeName: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            isDeleted: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            freeField4: expect.any(Object),
            freeField5: expect.any(Object),
            ledgerAccounts: expect.any(Object),
          })
        );
      });
    });

    describe('getAccountHeadCode', () => {
      it('should return NewAccountHeadCode for default AccountHeadCode initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createAccountHeadCodeFormGroup(sampleWithNewData);

        const accountHeadCode = service.getAccountHeadCode(formGroup) as any;

        expect(accountHeadCode).toMatchObject(sampleWithNewData);
      });

      it('should return NewAccountHeadCode for empty AccountHeadCode initial value', () => {
        const formGroup = service.createAccountHeadCodeFormGroup();

        const accountHeadCode = service.getAccountHeadCode(formGroup) as any;

        expect(accountHeadCode).toMatchObject({});
      });

      it('should return IAccountHeadCode', () => {
        const formGroup = service.createAccountHeadCodeFormGroup(sampleWithRequiredData);

        const accountHeadCode = service.getAccountHeadCode(formGroup) as any;

        expect(accountHeadCode).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAccountHeadCode should not enable id FormControl', () => {
        const formGroup = service.createAccountHeadCodeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAccountHeadCode should disable id FormControl', () => {
        const formGroup = service.createAccountHeadCodeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
