import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { INpaSetting, NewNpaSetting } from '../npa-setting.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts INpaSetting for edit and NewNpaSettingFormGroupInput for create.
 */
type NpaSettingFormGroupInput = INpaSetting | PartialWithRequiredKeyOf<NewNpaSetting>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends INpaSetting | NewNpaSetting> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

type NpaSettingFormRawValue = FormValueOf<INpaSetting>;

type NewNpaSettingFormRawValue = FormValueOf<NewNpaSetting>;

type NpaSettingFormDefaults = Pick<NewNpaSetting, 'id' | 'lastModified' | 'createdOn' | 'isDeleted'>;

type NpaSettingFormGroupContent = {
  id: FormControl<NpaSettingFormRawValue['id'] | NewNpaSetting['id']>;
  npaClassification: FormControl<NpaSettingFormRawValue['npaClassification']>;
  durationStart: FormControl<NpaSettingFormRawValue['durationStart']>;
  durationEnd: FormControl<NpaSettingFormRawValue['durationEnd']>;
  provision: FormControl<NpaSettingFormRawValue['provision']>;
  year: FormControl<NpaSettingFormRawValue['year']>;
  interestRate: FormControl<NpaSettingFormRawValue['interestRate']>;
  lastModified: FormControl<NpaSettingFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<NpaSettingFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<NpaSettingFormRawValue['createdBy']>;
  createdOn: FormControl<NpaSettingFormRawValue['createdOn']>;
  isDeleted: FormControl<NpaSettingFormRawValue['isDeleted']>;
  freeField1: FormControl<NpaSettingFormRawValue['freeField1']>;
  freeField2: FormControl<NpaSettingFormRawValue['freeField2']>;
  freeField3: FormControl<NpaSettingFormRawValue['freeField3']>;
  freeField4: FormControl<NpaSettingFormRawValue['freeField4']>;
  freeField5: FormControl<NpaSettingFormRawValue['freeField5']>;
  organisation: FormControl<NpaSettingFormRawValue['organisation']>;
};

export type NpaSettingFormGroup = FormGroup<NpaSettingFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class NpaSettingFormService {
  createNpaSettingFormGroup(npaSetting: NpaSettingFormGroupInput = { id: null }): NpaSettingFormGroup {
    const npaSettingRawValue = this.convertNpaSettingToNpaSettingRawValue({
      ...this.getFormDefaults(),
      ...npaSetting,
    });
    return new FormGroup<NpaSettingFormGroupContent>({
      id: new FormControl(
        { value: npaSettingRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      npaClassification: new FormControl(npaSettingRawValue.npaClassification),
      durationStart: new FormControl(npaSettingRawValue.durationStart),
      durationEnd: new FormControl(npaSettingRawValue.durationEnd),
      provision: new FormControl(npaSettingRawValue.provision),
      year: new FormControl(npaSettingRawValue.year),
      interestRate: new FormControl(npaSettingRawValue.interestRate),
      lastModified: new FormControl(npaSettingRawValue.lastModified),
      lastModifiedBy: new FormControl(npaSettingRawValue.lastModifiedBy),
      createdBy: new FormControl(npaSettingRawValue.createdBy),
      createdOn: new FormControl(npaSettingRawValue.createdOn),
      isDeleted: new FormControl(npaSettingRawValue.isDeleted),
      freeField1: new FormControl(npaSettingRawValue.freeField1),
      freeField2: new FormControl(npaSettingRawValue.freeField2),
      freeField3: new FormControl(npaSettingRawValue.freeField3),
      freeField4: new FormControl(npaSettingRawValue.freeField4),
      freeField5: new FormControl(npaSettingRawValue.freeField5),
      organisation: new FormControl(npaSettingRawValue.organisation),
    });
  }

  getNpaSetting(form: NpaSettingFormGroup): INpaSetting | NewNpaSetting {
    return this.convertNpaSettingRawValueToNpaSetting(form.getRawValue() as NpaSettingFormRawValue | NewNpaSettingFormRawValue);
  }

  resetForm(form: NpaSettingFormGroup, npaSetting: NpaSettingFormGroupInput): void {
    const npaSettingRawValue = this.convertNpaSettingToNpaSettingRawValue({ ...this.getFormDefaults(), ...npaSetting });
    form.reset(
      {
        ...npaSettingRawValue,
        id: { value: npaSettingRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): NpaSettingFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      lastModified: currentTime,
      createdOn: currentTime,
      isDeleted: false,
    };
  }

  private convertNpaSettingRawValueToNpaSetting(
    rawNpaSetting: NpaSettingFormRawValue | NewNpaSettingFormRawValue
  ): INpaSetting | NewNpaSetting {
    return {
      ...rawNpaSetting,
      lastModified: dayjs(rawNpaSetting.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawNpaSetting.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertNpaSettingToNpaSettingRawValue(
    npaSetting: INpaSetting | (Partial<NewNpaSetting> & NpaSettingFormDefaults)
  ): NpaSettingFormRawValue | PartialWithRequiredKeyOf<NewNpaSettingFormRawValue> {
    return {
      ...npaSetting,
      lastModified: npaSetting.lastModified ? npaSetting.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: npaSetting.createdOn ? npaSetting.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
