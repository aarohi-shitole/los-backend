import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ILedgerAccounts, NewLedgerAccounts } from '../ledger-accounts.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILedgerAccounts for edit and NewLedgerAccountsFormGroupInput for create.
 */
type LedgerAccountsFormGroupInput = ILedgerAccounts | PartialWithRequiredKeyOf<NewLedgerAccounts>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ILedgerAccounts | NewLedgerAccounts> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

type LedgerAccountsFormRawValue = FormValueOf<ILedgerAccounts>;

type NewLedgerAccountsFormRawValue = FormValueOf<NewLedgerAccounts>;

type LedgerAccountsFormDefaults = Pick<NewLedgerAccounts, 'id' | 'lastModified' | 'createdOn' | 'isDeleted'>;

type LedgerAccountsFormGroupContent = {
  id: FormControl<LedgerAccountsFormRawValue['id'] | NewLedgerAccounts['id']>;
  accountNo: FormControl<LedgerAccountsFormRawValue['accountNo']>;
  accountName: FormControl<LedgerAccountsFormRawValue['accountName']>;
  accBalance: FormControl<LedgerAccountsFormRawValue['accBalance']>;
  accHeadCode: FormControl<LedgerAccountsFormRawValue['accHeadCode']>;
  ledgerCode: FormControl<LedgerAccountsFormRawValue['ledgerCode']>;
  appCode: FormControl<LedgerAccountsFormRawValue['appCode']>;
  ledgerClassification: FormControl<LedgerAccountsFormRawValue['ledgerClassification']>;
  level: FormControl<LedgerAccountsFormRawValue['level']>;
  year: FormControl<LedgerAccountsFormRawValue['year']>;
  accountType: FormControl<LedgerAccountsFormRawValue['accountType']>;
  lastModified: FormControl<LedgerAccountsFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<LedgerAccountsFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<LedgerAccountsFormRawValue['createdBy']>;
  createdOn: FormControl<LedgerAccountsFormRawValue['createdOn']>;
  isDeleted: FormControl<LedgerAccountsFormRawValue['isDeleted']>;
  freeField1: FormControl<LedgerAccountsFormRawValue['freeField1']>;
  freeField2: FormControl<LedgerAccountsFormRawValue['freeField2']>;
  freeField3: FormControl<LedgerAccountsFormRawValue['freeField3']>;
  freeField4: FormControl<LedgerAccountsFormRawValue['freeField4']>;
  freeField5: FormControl<LedgerAccountsFormRawValue['freeField5']>;
  branch: FormControl<LedgerAccountsFormRawValue['branch']>;
  ledgerAccounts: FormControl<LedgerAccountsFormRawValue['ledgerAccounts']>;
};

export type LedgerAccountsFormGroup = FormGroup<LedgerAccountsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LedgerAccountsFormService {
  createLedgerAccountsFormGroup(ledgerAccounts: LedgerAccountsFormGroupInput = { id: null }): LedgerAccountsFormGroup {
    const ledgerAccountsRawValue = this.convertLedgerAccountsToLedgerAccountsRawValue({
      ...this.getFormDefaults(),
      ...ledgerAccounts,
    });
    return new FormGroup<LedgerAccountsFormGroupContent>({
      id: new FormControl(
        { value: ledgerAccountsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      accountNo: new FormControl(ledgerAccountsRawValue.accountNo),
      accountName: new FormControl(ledgerAccountsRawValue.accountName),
      accBalance: new FormControl(ledgerAccountsRawValue.accBalance),
      accHeadCode: new FormControl(ledgerAccountsRawValue.accHeadCode),
      ledgerCode: new FormControl(ledgerAccountsRawValue.ledgerCode),
      appCode: new FormControl(ledgerAccountsRawValue.appCode),
      ledgerClassification: new FormControl(ledgerAccountsRawValue.ledgerClassification),
      level: new FormControl(ledgerAccountsRawValue.level),
      year: new FormControl(ledgerAccountsRawValue.year),
      accountType: new FormControl(ledgerAccountsRawValue.accountType),
      lastModified: new FormControl(ledgerAccountsRawValue.lastModified),
      lastModifiedBy: new FormControl(ledgerAccountsRawValue.lastModifiedBy),
      createdBy: new FormControl(ledgerAccountsRawValue.createdBy),
      createdOn: new FormControl(ledgerAccountsRawValue.createdOn),
      isDeleted: new FormControl(ledgerAccountsRawValue.isDeleted),
      freeField1: new FormControl(ledgerAccountsRawValue.freeField1),
      freeField2: new FormControl(ledgerAccountsRawValue.freeField2),
      freeField3: new FormControl(ledgerAccountsRawValue.freeField3),
      freeField4: new FormControl(ledgerAccountsRawValue.freeField4),
      freeField5: new FormControl(ledgerAccountsRawValue.freeField5),
      branch: new FormControl(ledgerAccountsRawValue.branch),
      ledgerAccounts: new FormControl(ledgerAccountsRawValue.ledgerAccounts),
    });
  }

  getLedgerAccounts(form: LedgerAccountsFormGroup): ILedgerAccounts | NewLedgerAccounts {
    return this.convertLedgerAccountsRawValueToLedgerAccounts(
      form.getRawValue() as LedgerAccountsFormRawValue | NewLedgerAccountsFormRawValue
    );
  }

  resetForm(form: LedgerAccountsFormGroup, ledgerAccounts: LedgerAccountsFormGroupInput): void {
    const ledgerAccountsRawValue = this.convertLedgerAccountsToLedgerAccountsRawValue({ ...this.getFormDefaults(), ...ledgerAccounts });
    form.reset(
      {
        ...ledgerAccountsRawValue,
        id: { value: ledgerAccountsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): LedgerAccountsFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      lastModified: currentTime,
      createdOn: currentTime,
      isDeleted: false,
    };
  }

  private convertLedgerAccountsRawValueToLedgerAccounts(
    rawLedgerAccounts: LedgerAccountsFormRawValue | NewLedgerAccountsFormRawValue
  ): ILedgerAccounts | NewLedgerAccounts {
    return {
      ...rawLedgerAccounts,
      lastModified: dayjs(rawLedgerAccounts.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawLedgerAccounts.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertLedgerAccountsToLedgerAccountsRawValue(
    ledgerAccounts: ILedgerAccounts | (Partial<NewLedgerAccounts> & LedgerAccountsFormDefaults)
  ): LedgerAccountsFormRawValue | PartialWithRequiredKeyOf<NewLedgerAccountsFormRawValue> {
    return {
      ...ledgerAccounts,
      lastModified: ledgerAccounts.lastModified ? ledgerAccounts.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: ledgerAccounts.createdOn ? ledgerAccounts.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
