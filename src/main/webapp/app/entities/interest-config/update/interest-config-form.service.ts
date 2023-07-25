import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IInterestConfig, NewInterestConfig } from '../interest-config.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IInterestConfig for edit and NewInterestConfigFormGroupInput for create.
 */
type InterestConfigFormGroupInput = IInterestConfig | PartialWithRequiredKeyOf<NewInterestConfig>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IInterestConfig | NewInterestConfig> = Omit<T, 'startDate' | 'endDate' | 'lastModified'> & {
  startDate?: string | null;
  endDate?: string | null;
  lastModified?: string | null;
};

type InterestConfigFormRawValue = FormValueOf<IInterestConfig>;

type NewInterestConfigFormRawValue = FormValueOf<NewInterestConfig>;

type InterestConfigFormDefaults = Pick<NewInterestConfig, 'id' | 'startDate' | 'endDate' | 'isDeleted' | 'lastModified'>;

type InterestConfigFormGroupContent = {
  id: FormControl<InterestConfigFormRawValue['id'] | NewInterestConfig['id']>;
  startDate: FormControl<InterestConfigFormRawValue['startDate']>;
  endDate: FormControl<InterestConfigFormRawValue['endDate']>;
  interestBasis: FormControl<InterestConfigFormRawValue['interestBasis']>;
  emiBasis: FormControl<InterestConfigFormRawValue['emiBasis']>;
  interestType: FormControl<InterestConfigFormRawValue['interestType']>;
  intrAccrualFreq: FormControl<InterestConfigFormRawValue['intrAccrualFreq']>;
  penalInterestRate: FormControl<InterestConfigFormRawValue['penalInterestRate']>;
  penalInterestBasis: FormControl<InterestConfigFormRawValue['penalInterestBasis']>;
  penalAccountingBasis: FormControl<InterestConfigFormRawValue['penalAccountingBasis']>;
  minInterestRate: FormControl<InterestConfigFormRawValue['minInterestRate']>;
  maxInterestRate: FormControl<InterestConfigFormRawValue['maxInterestRate']>;
  extendedInterestRate: FormControl<InterestConfigFormRawValue['extendedInterestRate']>;
  surchargeRate: FormControl<InterestConfigFormRawValue['surchargeRate']>;
  isDeleted: FormControl<InterestConfigFormRawValue['isDeleted']>;
  lastModified: FormControl<InterestConfigFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<InterestConfigFormRawValue['lastModifiedBy']>;
  freeField1: FormControl<InterestConfigFormRawValue['freeField1']>;
  freeField2: FormControl<InterestConfigFormRawValue['freeField2']>;
  freeField3: FormControl<InterestConfigFormRawValue['freeField3']>;
  freeField4: FormControl<InterestConfigFormRawValue['freeField4']>;
  freeField5: FormControl<InterestConfigFormRawValue['freeField5']>;
  freeField6: FormControl<InterestConfigFormRawValue['freeField6']>;
  product: FormControl<InterestConfigFormRawValue['product']>;
};

export type InterestConfigFormGroup = FormGroup<InterestConfigFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class InterestConfigFormService {
  createInterestConfigFormGroup(interestConfig: InterestConfigFormGroupInput = { id: null }): InterestConfigFormGroup {
    const interestConfigRawValue = this.convertInterestConfigToInterestConfigRawValue({
      ...this.getFormDefaults(),
      ...interestConfig,
    });
    return new FormGroup<InterestConfigFormGroupContent>({
      id: new FormControl(
        { value: interestConfigRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      startDate: new FormControl(interestConfigRawValue.startDate),
      endDate: new FormControl(interestConfigRawValue.endDate),
      interestBasis: new FormControl(interestConfigRawValue.interestBasis),
      emiBasis: new FormControl(interestConfigRawValue.emiBasis),
      interestType: new FormControl(interestConfigRawValue.interestType),
      intrAccrualFreq: new FormControl(interestConfigRawValue.intrAccrualFreq),
      penalInterestRate: new FormControl(interestConfigRawValue.penalInterestRate),
      penalInterestBasis: new FormControl(interestConfigRawValue.penalInterestBasis),
      penalAccountingBasis: new FormControl(interestConfigRawValue.penalAccountingBasis),
      minInterestRate: new FormControl(interestConfigRawValue.minInterestRate),
      maxInterestRate: new FormControl(interestConfigRawValue.maxInterestRate),
      extendedInterestRate: new FormControl(interestConfigRawValue.extendedInterestRate),
      surchargeRate: new FormControl(interestConfigRawValue.surchargeRate),
      isDeleted: new FormControl(interestConfigRawValue.isDeleted),
      lastModified: new FormControl(interestConfigRawValue.lastModified),
      lastModifiedBy: new FormControl(interestConfigRawValue.lastModifiedBy),
      freeField1: new FormControl(interestConfigRawValue.freeField1),
      freeField2: new FormControl(interestConfigRawValue.freeField2),
      freeField3: new FormControl(interestConfigRawValue.freeField3),
      freeField4: new FormControl(interestConfigRawValue.freeField4),
      freeField5: new FormControl(interestConfigRawValue.freeField5),
      freeField6: new FormControl(interestConfigRawValue.freeField6),
      product: new FormControl(interestConfigRawValue.product),
    });
  }

  getInterestConfig(form: InterestConfigFormGroup): IInterestConfig | NewInterestConfig {
    return this.convertInterestConfigRawValueToInterestConfig(
      form.getRawValue() as InterestConfigFormRawValue | NewInterestConfigFormRawValue
    );
  }

  resetForm(form: InterestConfigFormGroup, interestConfig: InterestConfigFormGroupInput): void {
    const interestConfigRawValue = this.convertInterestConfigToInterestConfigRawValue({ ...this.getFormDefaults(), ...interestConfig });
    form.reset(
      {
        ...interestConfigRawValue,
        id: { value: interestConfigRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): InterestConfigFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      startDate: currentTime,
      endDate: currentTime,
      isDeleted: false,
      lastModified: currentTime,
    };
  }

  private convertInterestConfigRawValueToInterestConfig(
    rawInterestConfig: InterestConfigFormRawValue | NewInterestConfigFormRawValue
  ): IInterestConfig | NewInterestConfig {
    return {
      ...rawInterestConfig,
      startDate: dayjs(rawInterestConfig.startDate, DATE_TIME_FORMAT),
      endDate: dayjs(rawInterestConfig.endDate, DATE_TIME_FORMAT),
      lastModified: dayjs(rawInterestConfig.lastModified, DATE_TIME_FORMAT),
    };
  }

  private convertInterestConfigToInterestConfigRawValue(
    interestConfig: IInterestConfig | (Partial<NewInterestConfig> & InterestConfigFormDefaults)
  ): InterestConfigFormRawValue | PartialWithRequiredKeyOf<NewInterestConfigFormRawValue> {
    return {
      ...interestConfig,
      startDate: interestConfig.startDate ? interestConfig.startDate.format(DATE_TIME_FORMAT) : undefined,
      endDate: interestConfig.endDate ? interestConfig.endDate.format(DATE_TIME_FORMAT) : undefined,
      lastModified: interestConfig.lastModified ? interestConfig.lastModified.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
