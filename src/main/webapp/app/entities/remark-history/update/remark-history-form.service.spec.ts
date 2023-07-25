import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../remark-history.test-samples';

import { RemarkHistoryFormService } from './remark-history-form.service';

describe('RemarkHistory Form Service', () => {
  let service: RemarkHistoryFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RemarkHistoryFormService);
  });

  describe('Service methods', () => {
    describe('createRemarkHistoryFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createRemarkHistoryFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            remark: expect.any(Object),
            loanAmt: expect.any(Object),
            modifiedAmt: expect.any(Object),
            loanInterest: expect.any(Object),
            modifiedInterest: expect.any(Object),
            createdOn: expect.any(Object),
            createdBy: expect.any(Object),
            branch: expect.any(Object),
            applicationNo: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            freeField4: expect.any(Object),
            freeField5: expect.any(Object),
            freeField6: expect.any(Object),
            securityUser: expect.any(Object),
            loanApplications: expect.any(Object),
          })
        );
      });

      it('passing IRemarkHistory should create a new form with FormGroup', () => {
        const formGroup = service.createRemarkHistoryFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            remark: expect.any(Object),
            loanAmt: expect.any(Object),
            modifiedAmt: expect.any(Object),
            loanInterest: expect.any(Object),
            modifiedInterest: expect.any(Object),
            createdOn: expect.any(Object),
            createdBy: expect.any(Object),
            branch: expect.any(Object),
            applicationNo: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            freeField4: expect.any(Object),
            freeField5: expect.any(Object),
            freeField6: expect.any(Object),
            securityUser: expect.any(Object),
            loanApplications: expect.any(Object),
          })
        );
      });
    });

    describe('getRemarkHistory', () => {
      it('should return NewRemarkHistory for default RemarkHistory initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createRemarkHistoryFormGroup(sampleWithNewData);

        const remarkHistory = service.getRemarkHistory(formGroup) as any;

        expect(remarkHistory).toMatchObject(sampleWithNewData);
      });

      it('should return NewRemarkHistory for empty RemarkHistory initial value', () => {
        const formGroup = service.createRemarkHistoryFormGroup();

        const remarkHistory = service.getRemarkHistory(formGroup) as any;

        expect(remarkHistory).toMatchObject({});
      });

      it('should return IRemarkHistory', () => {
        const formGroup = service.createRemarkHistoryFormGroup(sampleWithRequiredData);

        const remarkHistory = service.getRemarkHistory(formGroup) as any;

        expect(remarkHistory).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IRemarkHistory should not enable id FormControl', () => {
        const formGroup = service.createRemarkHistoryFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewRemarkHistory should disable id FormControl', () => {
        const formGroup = service.createRemarkHistoryFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
