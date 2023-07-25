import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../ledger-accounts.test-samples';

import { LedgerAccountsFormService } from './ledger-accounts-form.service';

describe('LedgerAccounts Form Service', () => {
  let service: LedgerAccountsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LedgerAccountsFormService);
  });

  describe('Service methods', () => {
    describe('createLedgerAccountsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createLedgerAccountsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            accountNo: expect.any(Object),
            accountName: expect.any(Object),
            accBalance: expect.any(Object),
            accHeadCode: expect.any(Object),
            ledgerCode: expect.any(Object),
            appCode: expect.any(Object),
            ledgerClassification: expect.any(Object),
            level: expect.any(Object),
            year: expect.any(Object),
            accountType: expect.any(Object),
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
            branch: expect.any(Object),
            ledgerAccounts: expect.any(Object),
          })
        );
      });

      it('passing ILedgerAccounts should create a new form with FormGroup', () => {
        const formGroup = service.createLedgerAccountsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            accountNo: expect.any(Object),
            accountName: expect.any(Object),
            accBalance: expect.any(Object),
            accHeadCode: expect.any(Object),
            ledgerCode: expect.any(Object),
            appCode: expect.any(Object),
            ledgerClassification: expect.any(Object),
            level: expect.any(Object),
            year: expect.any(Object),
            accountType: expect.any(Object),
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
            branch: expect.any(Object),
            ledgerAccounts: expect.any(Object),
          })
        );
      });
    });

    describe('getLedgerAccounts', () => {
      it('should return NewLedgerAccounts for default LedgerAccounts initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createLedgerAccountsFormGroup(sampleWithNewData);

        const ledgerAccounts = service.getLedgerAccounts(formGroup) as any;

        expect(ledgerAccounts).toMatchObject(sampleWithNewData);
      });

      it('should return NewLedgerAccounts for empty LedgerAccounts initial value', () => {
        const formGroup = service.createLedgerAccountsFormGroup();

        const ledgerAccounts = service.getLedgerAccounts(formGroup) as any;

        expect(ledgerAccounts).toMatchObject({});
      });

      it('should return ILedgerAccounts', () => {
        const formGroup = service.createLedgerAccountsFormGroup(sampleWithRequiredData);

        const ledgerAccounts = service.getLedgerAccounts(formGroup) as any;

        expect(ledgerAccounts).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ILedgerAccounts should not enable id FormControl', () => {
        const formGroup = service.createLedgerAccountsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewLedgerAccounts should disable id FormControl', () => {
        const formGroup = service.createLedgerAccountsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
