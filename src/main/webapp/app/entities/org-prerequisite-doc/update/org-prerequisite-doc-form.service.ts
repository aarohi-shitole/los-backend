import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IOrgPrerequisiteDoc, NewOrgPrerequisiteDoc } from '../org-prerequisite-doc.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IOrgPrerequisiteDoc for edit and NewOrgPrerequisiteDocFormGroupInput for create.
 */
type OrgPrerequisiteDocFormGroupInput = IOrgPrerequisiteDoc | PartialWithRequiredKeyOf<NewOrgPrerequisiteDoc>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IOrgPrerequisiteDoc | NewOrgPrerequisiteDoc> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

type OrgPrerequisiteDocFormRawValue = FormValueOf<IOrgPrerequisiteDoc>;

type NewOrgPrerequisiteDocFormRawValue = FormValueOf<NewOrgPrerequisiteDoc>;

type OrgPrerequisiteDocFormDefaults = Pick<NewOrgPrerequisiteDoc, 'id' | 'ismandatory' | 'lastModified' | 'createdOn' | 'isDeleted'>;

type OrgPrerequisiteDocFormGroupContent = {
  id: FormControl<OrgPrerequisiteDocFormRawValue['id'] | NewOrgPrerequisiteDoc['id']>;
  docDesc: FormControl<OrgPrerequisiteDocFormRawValue['docDesc']>;
  docName: FormControl<OrgPrerequisiteDocFormRawValue['docName']>;
  docCatagory: FormControl<OrgPrerequisiteDocFormRawValue['docCatagory']>;
  ismandatory: FormControl<OrgPrerequisiteDocFormRawValue['ismandatory']>;
  lastModified: FormControl<OrgPrerequisiteDocFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<OrgPrerequisiteDocFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<OrgPrerequisiteDocFormRawValue['createdBy']>;
  createdOn: FormControl<OrgPrerequisiteDocFormRawValue['createdOn']>;
  isDeleted: FormControl<OrgPrerequisiteDocFormRawValue['isDeleted']>;
  freeField1: FormControl<OrgPrerequisiteDocFormRawValue['freeField1']>;
  freeField2: FormControl<OrgPrerequisiteDocFormRawValue['freeField2']>;
  freeField3: FormControl<OrgPrerequisiteDocFormRawValue['freeField3']>;
  freeField4: FormControl<OrgPrerequisiteDocFormRawValue['freeField4']>;
  freeField5: FormControl<OrgPrerequisiteDocFormRawValue['freeField5']>;
  product: FormControl<OrgPrerequisiteDocFormRawValue['product']>;
  organisation: FormControl<OrgPrerequisiteDocFormRawValue['organisation']>;
};

export type OrgPrerequisiteDocFormGroup = FormGroup<OrgPrerequisiteDocFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class OrgPrerequisiteDocFormService {
  createOrgPrerequisiteDocFormGroup(orgPrerequisiteDoc: OrgPrerequisiteDocFormGroupInput = { id: null }): OrgPrerequisiteDocFormGroup {
    const orgPrerequisiteDocRawValue = this.convertOrgPrerequisiteDocToOrgPrerequisiteDocRawValue({
      ...this.getFormDefaults(),
      ...orgPrerequisiteDoc,
    });
    return new FormGroup<OrgPrerequisiteDocFormGroupContent>({
      id: new FormControl(
        { value: orgPrerequisiteDocRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      docDesc: new FormControl(orgPrerequisiteDocRawValue.docDesc),
      docName: new FormControl(orgPrerequisiteDocRawValue.docName),
      docCatagory: new FormControl(orgPrerequisiteDocRawValue.docCatagory),
      ismandatory: new FormControl(orgPrerequisiteDocRawValue.ismandatory),
      lastModified: new FormControl(orgPrerequisiteDocRawValue.lastModified),
      lastModifiedBy: new FormControl(orgPrerequisiteDocRawValue.lastModifiedBy),
      createdBy: new FormControl(orgPrerequisiteDocRawValue.createdBy),
      createdOn: new FormControl(orgPrerequisiteDocRawValue.createdOn),
      isDeleted: new FormControl(orgPrerequisiteDocRawValue.isDeleted),
      freeField1: new FormControl(orgPrerequisiteDocRawValue.freeField1),
      freeField2: new FormControl(orgPrerequisiteDocRawValue.freeField2),
      freeField3: new FormControl(orgPrerequisiteDocRawValue.freeField3),
      freeField4: new FormControl(orgPrerequisiteDocRawValue.freeField4),
      freeField5: new FormControl(orgPrerequisiteDocRawValue.freeField5),
      product: new FormControl(orgPrerequisiteDocRawValue.product),
      organisation: new FormControl(orgPrerequisiteDocRawValue.organisation),
    });
  }

  getOrgPrerequisiteDoc(form: OrgPrerequisiteDocFormGroup): IOrgPrerequisiteDoc | NewOrgPrerequisiteDoc {
    return this.convertOrgPrerequisiteDocRawValueToOrgPrerequisiteDoc(
      form.getRawValue() as OrgPrerequisiteDocFormRawValue | NewOrgPrerequisiteDocFormRawValue
    );
  }

  resetForm(form: OrgPrerequisiteDocFormGroup, orgPrerequisiteDoc: OrgPrerequisiteDocFormGroupInput): void {
    const orgPrerequisiteDocRawValue = this.convertOrgPrerequisiteDocToOrgPrerequisiteDocRawValue({
      ...this.getFormDefaults(),
      ...orgPrerequisiteDoc,
    });
    form.reset(
      {
        ...orgPrerequisiteDocRawValue,
        id: { value: orgPrerequisiteDocRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): OrgPrerequisiteDocFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      ismandatory: false,
      lastModified: currentTime,
      createdOn: currentTime,
      isDeleted: false,
    };
  }

  private convertOrgPrerequisiteDocRawValueToOrgPrerequisiteDoc(
    rawOrgPrerequisiteDoc: OrgPrerequisiteDocFormRawValue | NewOrgPrerequisiteDocFormRawValue
  ): IOrgPrerequisiteDoc | NewOrgPrerequisiteDoc {
    return {
      ...rawOrgPrerequisiteDoc,
      lastModified: dayjs(rawOrgPrerequisiteDoc.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawOrgPrerequisiteDoc.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertOrgPrerequisiteDocToOrgPrerequisiteDocRawValue(
    orgPrerequisiteDoc: IOrgPrerequisiteDoc | (Partial<NewOrgPrerequisiteDoc> & OrgPrerequisiteDocFormDefaults)
  ): OrgPrerequisiteDocFormRawValue | PartialWithRequiredKeyOf<NewOrgPrerequisiteDocFormRawValue> {
    return {
      ...orgPrerequisiteDoc,
      lastModified: orgPrerequisiteDoc.lastModified ? orgPrerequisiteDoc.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: orgPrerequisiteDoc.createdOn ? orgPrerequisiteDoc.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
