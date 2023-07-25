import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAccountHeadCode, NewAccountHeadCode } from '../account-head-code.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAccountHeadCode for edit and NewAccountHeadCodeFormGroupInput for create.
 */
type AccountHeadCodeFormGroupInput = IAccountHeadCode | PartialWithRequiredKeyOf<NewAccountHeadCode>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IAccountHeadCode | NewAccountHeadCode> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

type AccountHeadCodeFormRawValue = FormValueOf<IAccountHeadCode>;

type NewAccountHeadCodeFormRawValue = FormValueOf<NewAccountHeadCode>;

type AccountHeadCodeFormDefaults = Pick<NewAccountHeadCode, 'id' | 'lastModified' | 'createdOn' | 'isDeleted'>;

type AccountHeadCodeFormGroupContent = {
  id: FormControl<AccountHeadCodeFormRawValue['id'] | NewAccountHeadCode['id']>;
  type: FormControl<AccountHeadCodeFormRawValue['type']>;
  value: FormControl<AccountHeadCodeFormRawValue['value']>;
  headCodeName: FormControl<AccountHeadCodeFormRawValue['headCodeName']>;
  lastModified: FormControl<AccountHeadCodeFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<AccountHeadCodeFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<AccountHeadCodeFormRawValue['createdBy']>;
  createdOn: FormControl<AccountHeadCodeFormRawValue['createdOn']>;
  isDeleted: FormControl<AccountHeadCodeFormRawValue['isDeleted']>;
  freeField1: FormControl<AccountHeadCodeFormRawValue['freeField1']>;
  freeField2: FormControl<AccountHeadCodeFormRawValue['freeField2']>;
  freeField3: FormControl<AccountHeadCodeFormRawValue['freeField3']>;
  freeField4: FormControl<AccountHeadCodeFormRawValue['freeField4']>;
  freeField5: FormControl<AccountHeadCodeFormRawValue['freeField5']>;
  ledgerAccounts: FormControl<AccountHeadCodeFormRawValue['ledgerAccounts']>;
};

export type AccountHeadCodeFormGroup = FormGroup<AccountHeadCodeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AccountHeadCodeFormService {
  createAccountHeadCodeFormGroup(accountHeadCode: AccountHeadCodeFormGroupInput = { id: null }): AccountHeadCodeFormGroup {
    const accountHeadCodeRawValue = this.convertAccountHeadCodeToAccountHeadCodeRawValue({
      ...this.getFormDefaults(),
      ...accountHeadCode,
    });
    return new FormGroup<AccountHeadCodeFormGroupContent>({
      id: new FormControl(
        { value: accountHeadCodeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      type: new FormControl(accountHeadCodeRawValue.type),
      value: new FormControl(accountHeadCodeRawValue.value),
      headCodeName: new FormControl(accountHeadCodeRawValue.headCodeName),
      lastModified: new FormControl(accountHeadCodeRawValue.lastModified),
      lastModifiedBy: new FormControl(accountHeadCodeRawValue.lastModifiedBy),
      createdBy: new FormControl(accountHeadCodeRawValue.createdBy),
      createdOn: new FormControl(accountHeadCodeRawValue.createdOn),
      isDeleted: new FormControl(accountHeadCodeRawValue.isDeleted),
      freeField1: new FormControl(accountHeadCodeRawValue.freeField1),
      freeField2: new FormControl(accountHeadCodeRawValue.freeField2),
      freeField3: new FormControl(accountHeadCodeRawValue.freeField3),
      freeField4: new FormControl(accountHeadCodeRawValue.freeField4),
      freeField5: new FormControl(accountHeadCodeRawValue.freeField5),
      ledgerAccounts: new FormControl(accountHeadCodeRawValue.ledgerAccounts),
    });
  }

  getAccountHeadCode(form: AccountHeadCodeFormGroup): IAccountHeadCode | NewAccountHeadCode {
    return this.convertAccountHeadCodeRawValueToAccountHeadCode(
      form.getRawValue() as AccountHeadCodeFormRawValue | NewAccountHeadCodeFormRawValue
    );
  }

  resetForm(form: AccountHeadCodeFormGroup, accountHeadCode: AccountHeadCodeFormGroupInput): void {
    const accountHeadCodeRawValue = this.convertAccountHeadCodeToAccountHeadCodeRawValue({ ...this.getFormDefaults(), ...accountHeadCode });
    form.reset(
      {
        ...accountHeadCodeRawValue,
        id: { value: accountHeadCodeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AccountHeadCodeFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      lastModified: currentTime,
      createdOn: currentTime,
      isDeleted: false,
    };
  }

  private convertAccountHeadCodeRawValueToAccountHeadCode(
    rawAccountHeadCode: AccountHeadCodeFormRawValue | NewAccountHeadCodeFormRawValue
  ): IAccountHeadCode | NewAccountHeadCode {
    return {
      ...rawAccountHeadCode,
      lastModified: dayjs(rawAccountHeadCode.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawAccountHeadCode.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertAccountHeadCodeToAccountHeadCodeRawValue(
    accountHeadCode: IAccountHeadCode | (Partial<NewAccountHeadCode> & AccountHeadCodeFormDefaults)
  ): AccountHeadCodeFormRawValue | PartialWithRequiredKeyOf<NewAccountHeadCodeFormRawValue> {
    return {
      ...accountHeadCode,
      lastModified: accountHeadCode.lastModified ? accountHeadCode.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: accountHeadCode.createdOn ? accountHeadCode.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
