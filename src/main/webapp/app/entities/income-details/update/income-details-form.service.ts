import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IIncomeDetails, NewIncomeDetails } from '../income-details.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IIncomeDetails for edit and NewIncomeDetailsFormGroupInput for create.
 */
type IncomeDetailsFormGroupInput = IIncomeDetails | PartialWithRequiredKeyOf<NewIncomeDetails>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IIncomeDetails | NewIncomeDetails> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

type IncomeDetailsFormRawValue = FormValueOf<IIncomeDetails>;

type NewIncomeDetailsFormRawValue = FormValueOf<NewIncomeDetails>;

type IncomeDetailsFormDefaults = Pick<NewIncomeDetails, 'id' | 'isDeleted' | 'lastModified' | 'createdOn'>;

type IncomeDetailsFormGroupContent = {
  id: FormControl<IncomeDetailsFormRawValue['id'] | NewIncomeDetails['id']>;
  year: FormControl<IncomeDetailsFormRawValue['year']>;
  grossIncome: FormControl<IncomeDetailsFormRawValue['grossIncome']>;
  expenses: FormControl<IncomeDetailsFormRawValue['expenses']>;
  netIncome: FormControl<IncomeDetailsFormRawValue['netIncome']>;
  paidTaxes: FormControl<IncomeDetailsFormRawValue['paidTaxes']>;
  cashSurplus: FormControl<IncomeDetailsFormRawValue['cashSurplus']>;
  incomeType: FormControl<IncomeDetailsFormRawValue['incomeType']>;
  isDeleted: FormControl<IncomeDetailsFormRawValue['isDeleted']>;
  lastModified: FormControl<IncomeDetailsFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<IncomeDetailsFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<IncomeDetailsFormRawValue['createdBy']>;
  createdOn: FormControl<IncomeDetailsFormRawValue['createdOn']>;
  freeField1: FormControl<IncomeDetailsFormRawValue['freeField1']>;
  freeField2: FormControl<IncomeDetailsFormRawValue['freeField2']>;
  freeField3: FormControl<IncomeDetailsFormRawValue['freeField3']>;
  freeField4: FormControl<IncomeDetailsFormRawValue['freeField4']>;
  freeField5: FormControl<IncomeDetailsFormRawValue['freeField5']>;
  freeField6: FormControl<IncomeDetailsFormRawValue['freeField6']>;
  member: FormControl<IncomeDetailsFormRawValue['member']>;
};

export type IncomeDetailsFormGroup = FormGroup<IncomeDetailsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class IncomeDetailsFormService {
  createIncomeDetailsFormGroup(incomeDetails: IncomeDetailsFormGroupInput = { id: null }): IncomeDetailsFormGroup {
    const incomeDetailsRawValue = this.convertIncomeDetailsToIncomeDetailsRawValue({
      ...this.getFormDefaults(),
      ...incomeDetails,
    });
    return new FormGroup<IncomeDetailsFormGroupContent>({
      id: new FormControl(
        { value: incomeDetailsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      year: new FormControl(incomeDetailsRawValue.year),
      grossIncome: new FormControl(incomeDetailsRawValue.grossIncome),
      expenses: new FormControl(incomeDetailsRawValue.expenses),
      netIncome: new FormControl(incomeDetailsRawValue.netIncome),
      paidTaxes: new FormControl(incomeDetailsRawValue.paidTaxes),
      cashSurplus: new FormControl(incomeDetailsRawValue.cashSurplus),
      incomeType: new FormControl(incomeDetailsRawValue.incomeType),
      isDeleted: new FormControl(incomeDetailsRawValue.isDeleted),
      lastModified: new FormControl(incomeDetailsRawValue.lastModified),
      lastModifiedBy: new FormControl(incomeDetailsRawValue.lastModifiedBy),
      createdBy: new FormControl(incomeDetailsRawValue.createdBy),
      createdOn: new FormControl(incomeDetailsRawValue.createdOn),
      freeField1: new FormControl(incomeDetailsRawValue.freeField1),
      freeField2: new FormControl(incomeDetailsRawValue.freeField2),
      freeField3: new FormControl(incomeDetailsRawValue.freeField3),
      freeField4: new FormControl(incomeDetailsRawValue.freeField4),
      freeField5: new FormControl(incomeDetailsRawValue.freeField5),
      freeField6: new FormControl(incomeDetailsRawValue.freeField6),
      member: new FormControl(incomeDetailsRawValue.member),
    });
  }

  getIncomeDetails(form: IncomeDetailsFormGroup): IIncomeDetails | NewIncomeDetails {
    return this.convertIncomeDetailsRawValueToIncomeDetails(form.getRawValue() as IncomeDetailsFormRawValue | NewIncomeDetailsFormRawValue);
  }

  resetForm(form: IncomeDetailsFormGroup, incomeDetails: IncomeDetailsFormGroupInput): void {
    const incomeDetailsRawValue = this.convertIncomeDetailsToIncomeDetailsRawValue({ ...this.getFormDefaults(), ...incomeDetails });
    form.reset(
      {
        ...incomeDetailsRawValue,
        id: { value: incomeDetailsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): IncomeDetailsFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      isDeleted: false,
      lastModified: currentTime,
      createdOn: currentTime,
    };
  }

  private convertIncomeDetailsRawValueToIncomeDetails(
    rawIncomeDetails: IncomeDetailsFormRawValue | NewIncomeDetailsFormRawValue
  ): IIncomeDetails | NewIncomeDetails {
    return {
      ...rawIncomeDetails,
      lastModified: dayjs(rawIncomeDetails.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawIncomeDetails.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertIncomeDetailsToIncomeDetailsRawValue(
    incomeDetails: IIncomeDetails | (Partial<NewIncomeDetails> & IncomeDetailsFormDefaults)
  ): IncomeDetailsFormRawValue | PartialWithRequiredKeyOf<NewIncomeDetailsFormRawValue> {
    return {
      ...incomeDetails,
      lastModified: incomeDetails.lastModified ? incomeDetails.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: incomeDetails.createdOn ? incomeDetails.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
