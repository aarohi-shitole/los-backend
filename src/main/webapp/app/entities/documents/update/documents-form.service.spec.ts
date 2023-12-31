import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../documents.test-samples';

import { DocumentsFormService } from './documents-form.service';

describe('Documents Form Service', () => {
  let service: DocumentsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DocumentsFormService);
  });

  describe('Service methods', () => {
    describe('createDocumentsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDocumentsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            docType: expect.any(Object),
            docSubType: expect.any(Object),
            tag: expect.any(Object),
            fileName: expect.any(Object),
            filePath: expect.any(Object),
            fileUrl: expect.any(Object),
            refrenceId: expect.any(Object),
            hasVerified: expect.any(Object),
            remark: expect.any(Object),
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
            member: expect.any(Object),
            guarantor: expect.any(Object),
          })
        );
      });

      it('passing IDocuments should create a new form with FormGroup', () => {
        const formGroup = service.createDocumentsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            docType: expect.any(Object),
            docSubType: expect.any(Object),
            tag: expect.any(Object),
            fileName: expect.any(Object),
            filePath: expect.any(Object),
            fileUrl: expect.any(Object),
            refrenceId: expect.any(Object),
            hasVerified: expect.any(Object),
            remark: expect.any(Object),
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
            member: expect.any(Object),
            guarantor: expect.any(Object),
          })
        );
      });
    });

    describe('getDocuments', () => {
      it('should return NewDocuments for default Documents initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createDocumentsFormGroup(sampleWithNewData);

        const documents = service.getDocuments(formGroup) as any;

        expect(documents).toMatchObject(sampleWithNewData);
      });

      it('should return NewDocuments for empty Documents initial value', () => {
        const formGroup = service.createDocumentsFormGroup();

        const documents = service.getDocuments(formGroup) as any;

        expect(documents).toMatchObject({});
      });

      it('should return IDocuments', () => {
        const formGroup = service.createDocumentsFormGroup(sampleWithRequiredData);

        const documents = service.getDocuments(formGroup) as any;

        expect(documents).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDocuments should not enable id FormControl', () => {
        const formGroup = service.createDocumentsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDocuments should disable id FormControl', () => {
        const formGroup = service.createDocumentsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
