import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IVouchersHistory, NewVouchersHistory } from '../vouchers-history.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IVouchersHistory for edit and NewVouchersHistoryFormGroupInput for create.
 */
type VouchersHistoryFormGroupInput = IVouchersHistory | PartialWithRequiredKeyOf<NewVouchersHistory>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IVouchersHistory | NewVouchersHistory> = Omit<T, 'createdOn' | 'voucherDate'> & {
  createdOn?: string | null;
  voucherDate?: string | null;
};

type VouchersHistoryFormRawValue = FormValueOf<IVouchersHistory>;

type NewVouchersHistoryFormRawValue = FormValueOf<NewVouchersHistory>;

type VouchersHistoryFormDefaults = Pick<NewVouchersHistory, 'id' | 'createdOn' | 'voucherDate'>;

type VouchersHistoryFormGroupContent = {
  id: FormControl<VouchersHistoryFormRawValue['id'] | NewVouchersHistory['id']>;
  createdOn: FormControl<VouchersHistoryFormRawValue['createdOn']>;
  createdBy: FormControl<VouchersHistoryFormRawValue['createdBy']>;
  voucherDate: FormControl<VouchersHistoryFormRawValue['voucherDate']>;
  code: FormControl<VouchersHistoryFormRawValue['code']>;
  freeField1: FormControl<VouchersHistoryFormRawValue['freeField1']>;
  freeField2: FormControl<VouchersHistoryFormRawValue['freeField2']>;
  freeField3: FormControl<VouchersHistoryFormRawValue['freeField3']>;
  freeField4: FormControl<VouchersHistoryFormRawValue['freeField4']>;
  freeField5: FormControl<VouchersHistoryFormRawValue['freeField5']>;
  freeField6: FormControl<VouchersHistoryFormRawValue['freeField6']>;
};

export type VouchersHistoryFormGroup = FormGroup<VouchersHistoryFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class VouchersHistoryFormService {
  createVouchersHistoryFormGroup(vouchersHistory: VouchersHistoryFormGroupInput = { id: null }): VouchersHistoryFormGroup {
    const vouchersHistoryRawValue = this.convertVouchersHistoryToVouchersHistoryRawValue({
      ...this.getFormDefaults(),
      ...vouchersHistory,
    });
    return new FormGroup<VouchersHistoryFormGroupContent>({
      id: new FormControl(
        { value: vouchersHistoryRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      createdOn: new FormControl(vouchersHistoryRawValue.createdOn),
      createdBy: new FormControl(vouchersHistoryRawValue.createdBy),
      voucherDate: new FormControl(vouchersHistoryRawValue.voucherDate),
      code: new FormControl(vouchersHistoryRawValue.code),
      freeField1: new FormControl(vouchersHistoryRawValue.freeField1),
      freeField2: new FormControl(vouchersHistoryRawValue.freeField2),
      freeField3: new FormControl(vouchersHistoryRawValue.freeField3),
      freeField4: new FormControl(vouchersHistoryRawValue.freeField4),
      freeField5: new FormControl(vouchersHistoryRawValue.freeField5),
      freeField6: new FormControl(vouchersHistoryRawValue.freeField6),
    });
  }

  getVouchersHistory(form: VouchersHistoryFormGroup): IVouchersHistory | NewVouchersHistory {
    return this.convertVouchersHistoryRawValueToVouchersHistory(
      form.getRawValue() as VouchersHistoryFormRawValue | NewVouchersHistoryFormRawValue
    );
  }

  resetForm(form: VouchersHistoryFormGroup, vouchersHistory: VouchersHistoryFormGroupInput): void {
    const vouchersHistoryRawValue = this.convertVouchersHistoryToVouchersHistoryRawValue({ ...this.getFormDefaults(), ...vouchersHistory });
    form.reset(
      {
        ...vouchersHistoryRawValue,
        id: { value: vouchersHistoryRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): VouchersHistoryFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createdOn: currentTime,
      voucherDate: currentTime,
    };
  }

  private convertVouchersHistoryRawValueToVouchersHistory(
    rawVouchersHistory: VouchersHistoryFormRawValue | NewVouchersHistoryFormRawValue
  ): IVouchersHistory | NewVouchersHistory {
    return {
      ...rawVouchersHistory,
      createdOn: dayjs(rawVouchersHistory.createdOn, DATE_TIME_FORMAT),
      voucherDate: dayjs(rawVouchersHistory.voucherDate, DATE_TIME_FORMAT),
    };
  }

  private convertVouchersHistoryToVouchersHistoryRawValue(
    vouchersHistory: IVouchersHistory | (Partial<NewVouchersHistory> & VouchersHistoryFormDefaults)
  ): VouchersHistoryFormRawValue | PartialWithRequiredKeyOf<NewVouchersHistoryFormRawValue> {
    return {
      ...vouchersHistory,
      createdOn: vouchersHistory.createdOn ? vouchersHistory.createdOn.format(DATE_TIME_FORMAT) : undefined,
      voucherDate: vouchersHistory.voucherDate ? vouchersHistory.voucherDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
