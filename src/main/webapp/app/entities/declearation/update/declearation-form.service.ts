import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDeclearation, NewDeclearation } from '../declearation.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDeclearation for edit and NewDeclearationFormGroupInput for create.
 */
type DeclearationFormGroupInput = IDeclearation | PartialWithRequiredKeyOf<NewDeclearation>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IDeclearation | NewDeclearation> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

type DeclearationFormRawValue = FormValueOf<IDeclearation>;

type NewDeclearationFormRawValue = FormValueOf<NewDeclearation>;

type DeclearationFormDefaults = Pick<NewDeclearation, 'id' | 'lastModified' | 'createdOn' | 'isDeleted'>;

type DeclearationFormGroupContent = {
  id: FormControl<DeclearationFormRawValue['id'] | NewDeclearation['id']>;
  tiltle: FormControl<DeclearationFormRawValue['tiltle']>;
  type: FormControl<DeclearationFormRawValue['type']>;
  description: FormControl<DeclearationFormRawValue['description']>;
  tag: FormControl<DeclearationFormRawValue['tag']>;
  subType: FormControl<DeclearationFormRawValue['subType']>;
  lastModified: FormControl<DeclearationFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<DeclearationFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<DeclearationFormRawValue['createdBy']>;
  createdOn: FormControl<DeclearationFormRawValue['createdOn']>;
  isDeleted: FormControl<DeclearationFormRawValue['isDeleted']>;
  freeField1: FormControl<DeclearationFormRawValue['freeField1']>;
  freeField2: FormControl<DeclearationFormRawValue['freeField2']>;
  freeField3: FormControl<DeclearationFormRawValue['freeField3']>;
  freeField4: FormControl<DeclearationFormRawValue['freeField4']>;
  organisation: FormControl<DeclearationFormRawValue['organisation']>;
};

export type DeclearationFormGroup = FormGroup<DeclearationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DeclearationFormService {
  createDeclearationFormGroup(declearation: DeclearationFormGroupInput = { id: null }): DeclearationFormGroup {
    const declearationRawValue = this.convertDeclearationToDeclearationRawValue({
      ...this.getFormDefaults(),
      ...declearation,
    });
    return new FormGroup<DeclearationFormGroupContent>({
      id: new FormControl(
        { value: declearationRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      tiltle: new FormControl(declearationRawValue.tiltle),
      type: new FormControl(declearationRawValue.type),
      description: new FormControl(declearationRawValue.description),
      tag: new FormControl(declearationRawValue.tag),
      subType: new FormControl(declearationRawValue.subType),
      lastModified: new FormControl(declearationRawValue.lastModified),
      lastModifiedBy: new FormControl(declearationRawValue.lastModifiedBy),
      createdBy: new FormControl(declearationRawValue.createdBy),
      createdOn: new FormControl(declearationRawValue.createdOn),
      isDeleted: new FormControl(declearationRawValue.isDeleted),
      freeField1: new FormControl(declearationRawValue.freeField1),
      freeField2: new FormControl(declearationRawValue.freeField2),
      freeField3: new FormControl(declearationRawValue.freeField3),
      freeField4: new FormControl(declearationRawValue.freeField4),
      organisation: new FormControl(declearationRawValue.organisation),
    });
  }

  getDeclearation(form: DeclearationFormGroup): IDeclearation | NewDeclearation {
    return this.convertDeclearationRawValueToDeclearation(form.getRawValue() as DeclearationFormRawValue | NewDeclearationFormRawValue);
  }

  resetForm(form: DeclearationFormGroup, declearation: DeclearationFormGroupInput): void {
    const declearationRawValue = this.convertDeclearationToDeclearationRawValue({ ...this.getFormDefaults(), ...declearation });
    form.reset(
      {
        ...declearationRawValue,
        id: { value: declearationRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DeclearationFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      lastModified: currentTime,
      createdOn: currentTime,
      isDeleted: false,
    };
  }

  private convertDeclearationRawValueToDeclearation(
    rawDeclearation: DeclearationFormRawValue | NewDeclearationFormRawValue
  ): IDeclearation | NewDeclearation {
    return {
      ...rawDeclearation,
      lastModified: dayjs(rawDeclearation.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawDeclearation.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertDeclearationToDeclearationRawValue(
    declearation: IDeclearation | (Partial<NewDeclearation> & DeclearationFormDefaults)
  ): DeclearationFormRawValue | PartialWithRequiredKeyOf<NewDeclearationFormRawValue> {
    return {
      ...declearation,
      lastModified: declearation.lastModified ? declearation.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: declearation.createdOn ? declearation.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
