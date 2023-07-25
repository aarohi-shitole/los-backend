import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISchemesDetails, NewSchemesDetails } from '../schemes-details.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISchemesDetails for edit and NewSchemesDetailsFormGroupInput for create.
 */
type SchemesDetailsFormGroupInput = ISchemesDetails | PartialWithRequiredKeyOf<NewSchemesDetails>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ISchemesDetails | NewSchemesDetails> = Omit<T, 'lastDayOfScheam' | 'lastModified' | 'createdOn'> & {
  lastDayOfScheam?: string | null;
  lastModified?: string | null;
  createdOn?: string | null;
};

type SchemesDetailsFormRawValue = FormValueOf<ISchemesDetails>;

type NewSchemesDetailsFormRawValue = FormValueOf<NewSchemesDetails>;

type SchemesDetailsFormDefaults = Pick<
  NewSchemesDetails,
  'id' | 'reinvestInterest' | 'lastDayOfScheam' | 'lastModified' | 'createdOn' | 'isDeleted'
>;

type SchemesDetailsFormGroupContent = {
  id: FormControl<SchemesDetailsFormRawValue['id'] | NewSchemesDetails['id']>;
  fdDurationDays: FormControl<SchemesDetailsFormRawValue['fdDurationDays']>;
  interest: FormControl<SchemesDetailsFormRawValue['interest']>;
  lockInPeriod: FormControl<SchemesDetailsFormRawValue['lockInPeriod']>;
  schemeName: FormControl<SchemesDetailsFormRawValue['schemeName']>;
  rdDurationMonths: FormControl<SchemesDetailsFormRawValue['rdDurationMonths']>;
  reinvestInterest: FormControl<SchemesDetailsFormRawValue['reinvestInterest']>;
  minAmount: FormControl<SchemesDetailsFormRawValue['minAmount']>;
  lastDayOfScheam: FormControl<SchemesDetailsFormRawValue['lastDayOfScheam']>;
  lastModified: FormControl<SchemesDetailsFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<SchemesDetailsFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<SchemesDetailsFormRawValue['createdBy']>;
  createdOn: FormControl<SchemesDetailsFormRawValue['createdOn']>;
  isDeleted: FormControl<SchemesDetailsFormRawValue['isDeleted']>;
  freeField1: FormControl<SchemesDetailsFormRawValue['freeField1']>;
  freeField2: FormControl<SchemesDetailsFormRawValue['freeField2']>;
  freeField3: FormControl<SchemesDetailsFormRawValue['freeField3']>;
  freeField4: FormControl<SchemesDetailsFormRawValue['freeField4']>;
  freeField5: FormControl<SchemesDetailsFormRawValue['freeField5']>;
  organisation: FormControl<SchemesDetailsFormRawValue['organisation']>;
};

export type SchemesDetailsFormGroup = FormGroup<SchemesDetailsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SchemesDetailsFormService {
  createSchemesDetailsFormGroup(schemesDetails: SchemesDetailsFormGroupInput = { id: null }): SchemesDetailsFormGroup {
    const schemesDetailsRawValue = this.convertSchemesDetailsToSchemesDetailsRawValue({
      ...this.getFormDefaults(),
      ...schemesDetails,
    });
    return new FormGroup<SchemesDetailsFormGroupContent>({
      id: new FormControl(
        { value: schemesDetailsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      fdDurationDays: new FormControl(schemesDetailsRawValue.fdDurationDays),
      interest: new FormControl(schemesDetailsRawValue.interest),
      lockInPeriod: new FormControl(schemesDetailsRawValue.lockInPeriod),
      schemeName: new FormControl(schemesDetailsRawValue.schemeName),
      rdDurationMonths: new FormControl(schemesDetailsRawValue.rdDurationMonths),
      reinvestInterest: new FormControl(schemesDetailsRawValue.reinvestInterest),
      minAmount: new FormControl(schemesDetailsRawValue.minAmount),
      lastDayOfScheam: new FormControl(schemesDetailsRawValue.lastDayOfScheam),
      lastModified: new FormControl(schemesDetailsRawValue.lastModified),
      lastModifiedBy: new FormControl(schemesDetailsRawValue.lastModifiedBy),
      createdBy: new FormControl(schemesDetailsRawValue.createdBy),
      createdOn: new FormControl(schemesDetailsRawValue.createdOn),
      isDeleted: new FormControl(schemesDetailsRawValue.isDeleted),
      freeField1: new FormControl(schemesDetailsRawValue.freeField1),
      freeField2: new FormControl(schemesDetailsRawValue.freeField2),
      freeField3: new FormControl(schemesDetailsRawValue.freeField3),
      freeField4: new FormControl(schemesDetailsRawValue.freeField4),
      freeField5: new FormControl(schemesDetailsRawValue.freeField5),
      organisation: new FormControl(schemesDetailsRawValue.organisation),
    });
  }

  getSchemesDetails(form: SchemesDetailsFormGroup): ISchemesDetails | NewSchemesDetails {
    return this.convertSchemesDetailsRawValueToSchemesDetails(
      form.getRawValue() as SchemesDetailsFormRawValue | NewSchemesDetailsFormRawValue
    );
  }

  resetForm(form: SchemesDetailsFormGroup, schemesDetails: SchemesDetailsFormGroupInput): void {
    const schemesDetailsRawValue = this.convertSchemesDetailsToSchemesDetailsRawValue({ ...this.getFormDefaults(), ...schemesDetails });
    form.reset(
      {
        ...schemesDetailsRawValue,
        id: { value: schemesDetailsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): SchemesDetailsFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      reinvestInterest: false,
      lastDayOfScheam: currentTime,
      lastModified: currentTime,
      createdOn: currentTime,
      isDeleted: false,
    };
  }

  private convertSchemesDetailsRawValueToSchemesDetails(
    rawSchemesDetails: SchemesDetailsFormRawValue | NewSchemesDetailsFormRawValue
  ): ISchemesDetails | NewSchemesDetails {
    return {
      ...rawSchemesDetails,
      lastDayOfScheam: dayjs(rawSchemesDetails.lastDayOfScheam, DATE_TIME_FORMAT),
      lastModified: dayjs(rawSchemesDetails.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawSchemesDetails.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertSchemesDetailsToSchemesDetailsRawValue(
    schemesDetails: ISchemesDetails | (Partial<NewSchemesDetails> & SchemesDetailsFormDefaults)
  ): SchemesDetailsFormRawValue | PartialWithRequiredKeyOf<NewSchemesDetailsFormRawValue> {
    return {
      ...schemesDetails,
      lastDayOfScheam: schemesDetails.lastDayOfScheam ? schemesDetails.lastDayOfScheam.format(DATE_TIME_FORMAT) : undefined,
      lastModified: schemesDetails.lastModified ? schemesDetails.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: schemesDetails.createdOn ? schemesDetails.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
