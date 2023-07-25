import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IEpay, NewEpay } from '../epay.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEpay for edit and NewEpayFormGroupInput for create.
 */
type EpayFormGroupInput = IEpay | PartialWithRequiredKeyOf<NewEpay>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IEpay | NewEpay> = Omit<T, 'lastModified'> & {
  lastModified?: string | null;
};

type EpayFormRawValue = FormValueOf<IEpay>;

type NewEpayFormRawValue = FormValueOf<NewEpay>;

type EpayFormDefaults = Pick<NewEpay, 'id' | 'isDeleted' | 'isActive' | 'lastModified'>;

type EpayFormGroupContent = {
  id: FormControl<EpayFormRawValue['id'] | NewEpay['id']>;
  instrumentType: FormControl<EpayFormRawValue['instrumentType']>;
  dtFromDate: FormControl<EpayFormRawValue['dtFromDate']>;
  dtToDate: FormControl<EpayFormRawValue['dtToDate']>;
  bankCode: FormControl<EpayFormRawValue['bankCode']>;
  bankBranchCode: FormControl<EpayFormRawValue['bankBranchCode']>;
  accountType: FormControl<EpayFormRawValue['accountType']>;
  accountNo: FormControl<EpayFormRawValue['accountNo']>;
  maxCeilAmount: FormControl<EpayFormRawValue['maxCeilAmount']>;
  installmentAmount: FormControl<EpayFormRawValue['installmentAmount']>;
  maxInstallmentAmount: FormControl<EpayFormRawValue['maxInstallmentAmount']>;
  mandateRefNo: FormControl<EpayFormRawValue['mandateRefNo']>;
  depositBank: FormControl<EpayFormRawValue['depositBank']>;
  mandateType: FormControl<EpayFormRawValue['mandateType']>;
  frequency: FormControl<EpayFormRawValue['frequency']>;
  ifscCode: FormControl<EpayFormRawValue['ifscCode']>;
  utrNo: FormControl<EpayFormRawValue['utrNo']>;
  isDeleted: FormControl<EpayFormRawValue['isDeleted']>;
  isActive: FormControl<EpayFormRawValue['isActive']>;
  lastModified: FormControl<EpayFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<EpayFormRawValue['lastModifiedBy']>;
  freeField1: FormControl<EpayFormRawValue['freeField1']>;
  freeField2: FormControl<EpayFormRawValue['freeField2']>;
  freeField3: FormControl<EpayFormRawValue['freeField3']>;
  freeField4: FormControl<EpayFormRawValue['freeField4']>;
};

export type EpayFormGroup = FormGroup<EpayFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EpayFormService {
  createEpayFormGroup(epay: EpayFormGroupInput = { id: null }): EpayFormGroup {
    const epayRawValue = this.convertEpayToEpayRawValue({
      ...this.getFormDefaults(),
      ...epay,
    });
    return new FormGroup<EpayFormGroupContent>({
      id: new FormControl(
        { value: epayRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      instrumentType: new FormControl(epayRawValue.instrumentType),
      dtFromDate: new FormControl(epayRawValue.dtFromDate),
      dtToDate: new FormControl(epayRawValue.dtToDate),
      bankCode: new FormControl(epayRawValue.bankCode),
      bankBranchCode: new FormControl(epayRawValue.bankBranchCode),
      accountType: new FormControl(epayRawValue.accountType),
      accountNo: new FormControl(epayRawValue.accountNo),
      maxCeilAmount: new FormControl(epayRawValue.maxCeilAmount),
      installmentAmount: new FormControl(epayRawValue.installmentAmount),
      maxInstallmentAmount: new FormControl(epayRawValue.maxInstallmentAmount),
      mandateRefNo: new FormControl(epayRawValue.mandateRefNo),
      depositBank: new FormControl(epayRawValue.depositBank),
      mandateType: new FormControl(epayRawValue.mandateType),
      frequency: new FormControl(epayRawValue.frequency),
      ifscCode: new FormControl(epayRawValue.ifscCode),
      utrNo: new FormControl(epayRawValue.utrNo),
      isDeleted: new FormControl(epayRawValue.isDeleted),
      isActive: new FormControl(epayRawValue.isActive),
      lastModified: new FormControl(epayRawValue.lastModified),
      lastModifiedBy: new FormControl(epayRawValue.lastModifiedBy),
      freeField1: new FormControl(epayRawValue.freeField1),
      freeField2: new FormControl(epayRawValue.freeField2),
      freeField3: new FormControl(epayRawValue.freeField3),
      freeField4: new FormControl(epayRawValue.freeField4),
    });
  }

  getEpay(form: EpayFormGroup): IEpay | NewEpay {
    return this.convertEpayRawValueToEpay(form.getRawValue() as EpayFormRawValue | NewEpayFormRawValue);
  }

  resetForm(form: EpayFormGroup, epay: EpayFormGroupInput): void {
    const epayRawValue = this.convertEpayToEpayRawValue({ ...this.getFormDefaults(), ...epay });
    form.reset(
      {
        ...epayRawValue,
        id: { value: epayRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): EpayFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      isDeleted: false,
      isActive: false,
      lastModified: currentTime,
    };
  }

  private convertEpayRawValueToEpay(rawEpay: EpayFormRawValue | NewEpayFormRawValue): IEpay | NewEpay {
    return {
      ...rawEpay,
      lastModified: dayjs(rawEpay.lastModified, DATE_TIME_FORMAT),
    };
  }

  private convertEpayToEpayRawValue(
    epay: IEpay | (Partial<NewEpay> & EpayFormDefaults)
  ): EpayFormRawValue | PartialWithRequiredKeyOf<NewEpayFormRawValue> {
    return {
      ...epay,
      lastModified: epay.lastModified ? epay.lastModified.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
