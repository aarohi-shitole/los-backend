import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../sequence-generator.test-samples';

import { SequenceGeneratorFormService } from './sequence-generator-form.service';

describe('SequenceGenerator Form Service', () => {
  let service: SequenceGeneratorFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SequenceGeneratorFormService);
  });

  describe('Service methods', () => {
    describe('createSequenceGeneratorFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSequenceGeneratorFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nextValMember: expect.any(Object),
            nextValLoanApp: expect.any(Object),
            nextValLoanAccount: expect.any(Object),
            nextValVoucher: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            freeField4: expect.any(Object),
            freeField5: expect.any(Object),
            branch: expect.any(Object),
          })
        );
      });

      it('passing ISequenceGenerator should create a new form with FormGroup', () => {
        const formGroup = service.createSequenceGeneratorFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nextValMember: expect.any(Object),
            nextValLoanApp: expect.any(Object),
            nextValLoanAccount: expect.any(Object),
            nextValVoucher: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            freeField4: expect.any(Object),
            freeField5: expect.any(Object),
            branch: expect.any(Object),
          })
        );
      });
    });

    describe('getSequenceGenerator', () => {
      it('should return NewSequenceGenerator for default SequenceGenerator initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createSequenceGeneratorFormGroup(sampleWithNewData);

        const sequenceGenerator = service.getSequenceGenerator(formGroup) as any;

        expect(sequenceGenerator).toMatchObject(sampleWithNewData);
      });

      it('should return NewSequenceGenerator for empty SequenceGenerator initial value', () => {
        const formGroup = service.createSequenceGeneratorFormGroup();

        const sequenceGenerator = service.getSequenceGenerator(formGroup) as any;

        expect(sequenceGenerator).toMatchObject({});
      });

      it('should return ISequenceGenerator', () => {
        const formGroup = service.createSequenceGeneratorFormGroup(sampleWithRequiredData);

        const sequenceGenerator = service.getSequenceGenerator(formGroup) as any;

        expect(sequenceGenerator).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISequenceGenerator should not enable id FormControl', () => {
        const formGroup = service.createSequenceGeneratorFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSequenceGenerator should disable id FormControl', () => {
        const formGroup = service.createSequenceGeneratorFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
