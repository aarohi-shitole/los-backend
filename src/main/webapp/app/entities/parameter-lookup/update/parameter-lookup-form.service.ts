import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IParameterLookup, NewParameterLookup } from '../parameter-lookup.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IParameterLookup for edit and NewParameterLookupFormGroupInput for create.
 */
type ParameterLookupFormGroupInput = IParameterLookup | PartialWithRequiredKeyOf<NewParameterLookup>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IParameterLookup | NewParameterLookup> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

type ParameterLookupFormRawValue = FormValueOf<IParameterLookup>;

type NewParameterLookupFormRawValue = FormValueOf<NewParameterLookup>;

type ParameterLookupFormDefaults = Pick<NewParameterLookup, 'id' | 'lastModified' | 'createdOn' | 'isDeleted'>;

type ParameterLookupFormGroupContent = {
  id: FormControl<ParameterLookupFormRawValue['id'] | NewParameterLookup['id']>;
  type: FormControl<ParameterLookupFormRawValue['type']>;
  name: FormControl<ParameterLookupFormRawValue['name']>;
  description: FormControl<ParameterLookupFormRawValue['description']>;
  value: FormControl<ParameterLookupFormRawValue['value']>;
  lastModified: FormControl<ParameterLookupFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<ParameterLookupFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<ParameterLookupFormRawValue['createdBy']>;
  createdOn: FormControl<ParameterLookupFormRawValue['createdOn']>;
  isDeleted: FormControl<ParameterLookupFormRawValue['isDeleted']>;
  freeField1: FormControl<ParameterLookupFormRawValue['freeField1']>;
  freeField2: FormControl<ParameterLookupFormRawValue['freeField2']>;
  freeField3: FormControl<ParameterLookupFormRawValue['freeField3']>;
  freeField4: FormControl<ParameterLookupFormRawValue['freeField4']>;
  organisation: FormControl<ParameterLookupFormRawValue['organisation']>;
};

export type ParameterLookupFormGroup = FormGroup<ParameterLookupFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ParameterLookupFormService {
  createParameterLookupFormGroup(parameterLookup: ParameterLookupFormGroupInput = { id: null }): ParameterLookupFormGroup {
    const parameterLookupRawValue = this.convertParameterLookupToParameterLookupRawValue({
      ...this.getFormDefaults(),
      ...parameterLookup,
    });
    return new FormGroup<ParameterLookupFormGroupContent>({
      id: new FormControl(
        { value: parameterLookupRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      type: new FormControl(parameterLookupRawValue.type),
      name: new FormControl(parameterLookupRawValue.name),
      description: new FormControl(parameterLookupRawValue.description),
      value: new FormControl(parameterLookupRawValue.value),
      lastModified: new FormControl(parameterLookupRawValue.lastModified),
      lastModifiedBy: new FormControl(parameterLookupRawValue.lastModifiedBy),
      createdBy: new FormControl(parameterLookupRawValue.createdBy),
      createdOn: new FormControl(parameterLookupRawValue.createdOn),
      isDeleted: new FormControl(parameterLookupRawValue.isDeleted),
      freeField1: new FormControl(parameterLookupRawValue.freeField1),
      freeField2: new FormControl(parameterLookupRawValue.freeField2),
      freeField3: new FormControl(parameterLookupRawValue.freeField3),
      freeField4: new FormControl(parameterLookupRawValue.freeField4),
      organisation: new FormControl(parameterLookupRawValue.organisation),
    });
  }

  getParameterLookup(form: ParameterLookupFormGroup): IParameterLookup | NewParameterLookup {
    return this.convertParameterLookupRawValueToParameterLookup(
      form.getRawValue() as ParameterLookupFormRawValue | NewParameterLookupFormRawValue
    );
  }

  resetForm(form: ParameterLookupFormGroup, parameterLookup: ParameterLookupFormGroupInput): void {
    const parameterLookupRawValue = this.convertParameterLookupToParameterLookupRawValue({ ...this.getFormDefaults(), ...parameterLookup });
    form.reset(
      {
        ...parameterLookupRawValue,
        id: { value: parameterLookupRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ParameterLookupFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      lastModified: currentTime,
      createdOn: currentTime,
      isDeleted: false,
    };
  }

  private convertParameterLookupRawValueToParameterLookup(
    rawParameterLookup: ParameterLookupFormRawValue | NewParameterLookupFormRawValue
  ): IParameterLookup | NewParameterLookup {
    return {
      ...rawParameterLookup,
      lastModified: dayjs(rawParameterLookup.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawParameterLookup.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertParameterLookupToParameterLookupRawValue(
    parameterLookup: IParameterLookup | (Partial<NewParameterLookup> & ParameterLookupFormDefaults)
  ): ParameterLookupFormRawValue | PartialWithRequiredKeyOf<NewParameterLookupFormRawValue> {
    return {
      ...parameterLookup,
      lastModified: parameterLookup.lastModified ? parameterLookup.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: parameterLookup.createdOn ? parameterLookup.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
