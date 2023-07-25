import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IVouchers, NewVouchers } from '../vouchers.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IVouchers for edit and NewVouchersFormGroupInput for create.
 */
type VouchersFormGroupInput = IVouchers | PartialWithRequiredKeyOf<NewVouchers>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IVouchers | NewVouchers> = Omit<T, 'voucherDate' | 'lastModified'> & {
  voucherDate?: string | null;
  lastModified?: string | null;
};

type VouchersFormRawValue = FormValueOf<IVouchers>;

type NewVouchersFormRawValue = FormValueOf<NewVouchers>;

type VouchersFormDefaults = Pick<NewVouchers, 'id' | 'voucherDate' | 'isDeleted' | 'lastModified'>;

type VouchersFormGroupContent = {
  id: FormControl<VouchersFormRawValue['id'] | NewVouchers['id']>;
  voucherDate: FormControl<VouchersFormRawValue['voucherDate']>;
  voucherNo: FormControl<VouchersFormRawValue['voucherNo']>;
  preparedBy: FormControl<VouchersFormRawValue['preparedBy']>;
  code: FormControl<VouchersFormRawValue['code']>;
  narration: FormControl<VouchersFormRawValue['narration']>;
  authorisedBy: FormControl<VouchersFormRawValue['authorisedBy']>;
  mode: FormControl<VouchersFormRawValue['mode']>;
  isDeleted: FormControl<VouchersFormRawValue['isDeleted']>;
  lastModified: FormControl<VouchersFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<VouchersFormRawValue['lastModifiedBy']>;
  freeField1: FormControl<VouchersFormRawValue['freeField1']>;
  freeField2: FormControl<VouchersFormRawValue['freeField2']>;
  freeField3: FormControl<VouchersFormRawValue['freeField3']>;
  freeField4: FormControl<VouchersFormRawValue['freeField4']>;
  freeField5: FormControl<VouchersFormRawValue['freeField5']>;
  freeField6: FormControl<VouchersFormRawValue['freeField6']>;
};

export type VouchersFormGroup = FormGroup<VouchersFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class VouchersFormService {
  createVouchersFormGroup(vouchers: VouchersFormGroupInput = { id: null }): VouchersFormGroup {
    const vouchersRawValue = this.convertVouchersToVouchersRawValue({
      ...this.getFormDefaults(),
      ...vouchers,
    });
    return new FormGroup<VouchersFormGroupContent>({
      id: new FormControl(
        { value: vouchersRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      voucherDate: new FormControl(vouchersRawValue.voucherDate),
      voucherNo: new FormControl(vouchersRawValue.voucherNo),
      preparedBy: new FormControl(vouchersRawValue.preparedBy),
      code: new FormControl(vouchersRawValue.code),
      narration: new FormControl(vouchersRawValue.narration),
      authorisedBy: new FormControl(vouchersRawValue.authorisedBy),
      mode: new FormControl(vouchersRawValue.mode),
      isDeleted: new FormControl(vouchersRawValue.isDeleted),
      lastModified: new FormControl(vouchersRawValue.lastModified),
      lastModifiedBy: new FormControl(vouchersRawValue.lastModifiedBy),
      freeField1: new FormControl(vouchersRawValue.freeField1),
      freeField2: new FormControl(vouchersRawValue.freeField2),
      freeField3: new FormControl(vouchersRawValue.freeField3),
      freeField4: new FormControl(vouchersRawValue.freeField4),
      freeField5: new FormControl(vouchersRawValue.freeField5),
      freeField6: new FormControl(vouchersRawValue.freeField6),
    });
  }

  getVouchers(form: VouchersFormGroup): IVouchers | NewVouchers {
    return this.convertVouchersRawValueToVouchers(form.getRawValue() as VouchersFormRawValue | NewVouchersFormRawValue);
  }

  resetForm(form: VouchersFormGroup, vouchers: VouchersFormGroupInput): void {
    const vouchersRawValue = this.convertVouchersToVouchersRawValue({ ...this.getFormDefaults(), ...vouchers });
    form.reset(
      {
        ...vouchersRawValue,
        id: { value: vouchersRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): VouchersFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      voucherDate: currentTime,
      isDeleted: false,
      lastModified: currentTime,
    };
  }

  private convertVouchersRawValueToVouchers(rawVouchers: VouchersFormRawValue | NewVouchersFormRawValue): IVouchers | NewVouchers {
    return {
      ...rawVouchers,
      voucherDate: dayjs(rawVouchers.voucherDate, DATE_TIME_FORMAT),
      lastModified: dayjs(rawVouchers.lastModified, DATE_TIME_FORMAT),
    };
  }

  private convertVouchersToVouchersRawValue(
    vouchers: IVouchers | (Partial<NewVouchers> & VouchersFormDefaults)
  ): VouchersFormRawValue | PartialWithRequiredKeyOf<NewVouchersFormRawValue> {
    return {
      ...vouchers,
      voucherDate: vouchers.voucherDate ? vouchers.voucherDate.format(DATE_TIME_FORMAT) : undefined,
      lastModified: vouchers.lastModified ? vouchers.lastModified.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
