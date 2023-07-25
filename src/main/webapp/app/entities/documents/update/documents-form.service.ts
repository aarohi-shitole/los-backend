import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDocuments, NewDocuments } from '../documents.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDocuments for edit and NewDocumentsFormGroupInput for create.
 */
type DocumentsFormGroupInput = IDocuments | PartialWithRequiredKeyOf<NewDocuments>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IDocuments | NewDocuments> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

type DocumentsFormRawValue = FormValueOf<IDocuments>;

type NewDocumentsFormRawValue = FormValueOf<NewDocuments>;

type DocumentsFormDefaults = Pick<NewDocuments, 'id' | 'hasVerified' | 'lastModified' | 'createdOn' | 'isDeleted'>;

type DocumentsFormGroupContent = {
  id: FormControl<DocumentsFormRawValue['id'] | NewDocuments['id']>;
  docType: FormControl<DocumentsFormRawValue['docType']>;
  docSubType: FormControl<DocumentsFormRawValue['docSubType']>;
  tag: FormControl<DocumentsFormRawValue['tag']>;
  fileName: FormControl<DocumentsFormRawValue['fileName']>;
  filePath: FormControl<DocumentsFormRawValue['filePath']>;
  fileUrl: FormControl<DocumentsFormRawValue['fileUrl']>;
  refrenceId: FormControl<DocumentsFormRawValue['refrenceId']>;
  hasVerified: FormControl<DocumentsFormRawValue['hasVerified']>;
  remark: FormControl<DocumentsFormRawValue['remark']>;
  lastModified: FormControl<DocumentsFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<DocumentsFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<DocumentsFormRawValue['createdBy']>;
  createdOn: FormControl<DocumentsFormRawValue['createdOn']>;
  isDeleted: FormControl<DocumentsFormRawValue['isDeleted']>;
  freeField1: FormControl<DocumentsFormRawValue['freeField1']>;
  freeField2: FormControl<DocumentsFormRawValue['freeField2']>;
  freeField3: FormControl<DocumentsFormRawValue['freeField3']>;
  freeField4: FormControl<DocumentsFormRawValue['freeField4']>;
  freeField5: FormControl<DocumentsFormRawValue['freeField5']>;
  member: FormControl<DocumentsFormRawValue['member']>;
  guarantor: FormControl<DocumentsFormRawValue['guarantor']>;
};

export type DocumentsFormGroup = FormGroup<DocumentsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DocumentsFormService {
  createDocumentsFormGroup(documents: DocumentsFormGroupInput = { id: null }): DocumentsFormGroup {
    const documentsRawValue = this.convertDocumentsToDocumentsRawValue({
      ...this.getFormDefaults(),
      ...documents,
    });
    return new FormGroup<DocumentsFormGroupContent>({
      id: new FormControl(
        { value: documentsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      docType: new FormControl(documentsRawValue.docType),
      docSubType: new FormControl(documentsRawValue.docSubType),
      tag: new FormControl(documentsRawValue.tag),
      fileName: new FormControl(documentsRawValue.fileName),
      filePath: new FormControl(documentsRawValue.filePath),
      fileUrl: new FormControl(documentsRawValue.fileUrl),
      refrenceId: new FormControl(documentsRawValue.refrenceId),
      hasVerified: new FormControl(documentsRawValue.hasVerified),
      remark: new FormControl(documentsRawValue.remark),
      lastModified: new FormControl(documentsRawValue.lastModified),
      lastModifiedBy: new FormControl(documentsRawValue.lastModifiedBy),
      createdBy: new FormControl(documentsRawValue.createdBy),
      createdOn: new FormControl(documentsRawValue.createdOn),
      isDeleted: new FormControl(documentsRawValue.isDeleted),
      freeField1: new FormControl(documentsRawValue.freeField1),
      freeField2: new FormControl(documentsRawValue.freeField2),
      freeField3: new FormControl(documentsRawValue.freeField3),
      freeField4: new FormControl(documentsRawValue.freeField4),
      freeField5: new FormControl(documentsRawValue.freeField5),
      member: new FormControl(documentsRawValue.member),
      guarantor: new FormControl(documentsRawValue.guarantor),
    });
  }

  getDocuments(form: DocumentsFormGroup): IDocuments | NewDocuments {
    return this.convertDocumentsRawValueToDocuments(form.getRawValue() as DocumentsFormRawValue | NewDocumentsFormRawValue);
  }

  resetForm(form: DocumentsFormGroup, documents: DocumentsFormGroupInput): void {
    const documentsRawValue = this.convertDocumentsToDocumentsRawValue({ ...this.getFormDefaults(), ...documents });
    form.reset(
      {
        ...documentsRawValue,
        id: { value: documentsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DocumentsFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      hasVerified: false,
      lastModified: currentTime,
      createdOn: currentTime,
      isDeleted: false,
    };
  }

  private convertDocumentsRawValueToDocuments(rawDocuments: DocumentsFormRawValue | NewDocumentsFormRawValue): IDocuments | NewDocuments {
    return {
      ...rawDocuments,
      lastModified: dayjs(rawDocuments.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawDocuments.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertDocumentsToDocumentsRawValue(
    documents: IDocuments | (Partial<NewDocuments> & DocumentsFormDefaults)
  ): DocumentsFormRawValue | PartialWithRequiredKeyOf<NewDocumentsFormRawValue> {
    return {
      ...documents,
      lastModified: documents.lastModified ? documents.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: documents.createdOn ? documents.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
