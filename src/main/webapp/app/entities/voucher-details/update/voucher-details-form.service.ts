import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IVoucherDetails, NewVoucherDetails } from '../voucher-details.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IVoucherDetails for edit and NewVoucherDetailsFormGroupInput for create.
 */
type VoucherDetailsFormGroupInput = IVoucherDetails | PartialWithRequiredKeyOf<NewVoucherDetails>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IVoucherDetails | NewVoucherDetails> = Omit<T, 'lastModified'> & {
  lastModified?: string | null;
};

type VoucherDetailsFormRawValue = FormValueOf<IVoucherDetails>;

type NewVoucherDetailsFormRawValue = FormValueOf<NewVoucherDetails>;

type VoucherDetailsFormDefaults = Pick<NewVoucherDetails, 'id' | 'isDeleted' | 'lastModified'>;

type VoucherDetailsFormGroupContent = {
  id: FormControl<VoucherDetailsFormRawValue['id'] | NewVoucherDetails['id']>;
  accHeadCode: FormControl<VoucherDetailsFormRawValue['accHeadCode']>;
  creditAccount: FormControl<VoucherDetailsFormRawValue['creditAccount']>;
  debitAccount: FormControl<VoucherDetailsFormRawValue['debitAccount']>;
  transferAmt: FormControl<VoucherDetailsFormRawValue['transferAmt']>;
  isDeleted: FormControl<VoucherDetailsFormRawValue['isDeleted']>;
  lastModified: FormControl<VoucherDetailsFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<VoucherDetailsFormRawValue['lastModifiedBy']>;
  freeField1: FormControl<VoucherDetailsFormRawValue['freeField1']>;
  freeField2: FormControl<VoucherDetailsFormRawValue['freeField2']>;
  freeField3: FormControl<VoucherDetailsFormRawValue['freeField3']>;
  freeField4: FormControl<VoucherDetailsFormRawValue['freeField4']>;
  freeField5: FormControl<VoucherDetailsFormRawValue['freeField5']>;
  freeField6: FormControl<VoucherDetailsFormRawValue['freeField6']>;
};

export type VoucherDetailsFormGroup = FormGroup<VoucherDetailsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class VoucherDetailsFormService {
  createVoucherDetailsFormGroup(voucherDetails: VoucherDetailsFormGroupInput = { id: null }): VoucherDetailsFormGroup {
    const voucherDetailsRawValue = this.convertVoucherDetailsToVoucherDetailsRawValue({
      ...this.getFormDefaults(),
      ...voucherDetails,
    });
    return new FormGroup<VoucherDetailsFormGroupContent>({
      id: new FormControl(
        { value: voucherDetailsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      accHeadCode: new FormControl(voucherDetailsRawValue.accHeadCode),
      creditAccount: new FormControl(voucherDetailsRawValue.creditAccount),
      debitAccount: new FormControl(voucherDetailsRawValue.debitAccount),
      transferAmt: new FormControl(voucherDetailsRawValue.transferAmt),
      isDeleted: new FormControl(voucherDetailsRawValue.isDeleted),
      lastModified: new FormControl(voucherDetailsRawValue.lastModified),
      lastModifiedBy: new FormControl(voucherDetailsRawValue.lastModifiedBy),
      freeField1: new FormControl(voucherDetailsRawValue.freeField1),
      freeField2: new FormControl(voucherDetailsRawValue.freeField2),
      freeField3: new FormControl(voucherDetailsRawValue.freeField3),
      freeField4: new FormControl(voucherDetailsRawValue.freeField4),
      freeField5: new FormControl(voucherDetailsRawValue.freeField5),
      freeField6: new FormControl(voucherDetailsRawValue.freeField6),
    });
  }

  getVoucherDetails(form: VoucherDetailsFormGroup): IVoucherDetails | NewVoucherDetails {
    return this.convertVoucherDetailsRawValueToVoucherDetails(
      form.getRawValue() as VoucherDetailsFormRawValue | NewVoucherDetailsFormRawValue
    );
  }

  resetForm(form: VoucherDetailsFormGroup, voucherDetails: VoucherDetailsFormGroupInput): void {
    const voucherDetailsRawValue = this.convertVoucherDetailsToVoucherDetailsRawValue({ ...this.getFormDefaults(), ...voucherDetails });
    form.reset(
      {
        ...voucherDetailsRawValue,
        id: { value: voucherDetailsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): VoucherDetailsFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      isDeleted: false,
      lastModified: currentTime,
    };
  }

  private convertVoucherDetailsRawValueToVoucherDetails(
    rawVoucherDetails: VoucherDetailsFormRawValue | NewVoucherDetailsFormRawValue
  ): IVoucherDetails | NewVoucherDetails {
    return {
      ...rawVoucherDetails,
      lastModified: dayjs(rawVoucherDetails.lastModified, DATE_TIME_FORMAT),
    };
  }

  private convertVoucherDetailsToVoucherDetailsRawValue(
    voucherDetails: IVoucherDetails | (Partial<NewVoucherDetails> & VoucherDetailsFormDefaults)
  ): VoucherDetailsFormRawValue | PartialWithRequiredKeyOf<NewVoucherDetailsFormRawValue> {
    return {
      ...voucherDetails,
      lastModified: voucherDetails.lastModified ? voucherDetails.lastModified.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
