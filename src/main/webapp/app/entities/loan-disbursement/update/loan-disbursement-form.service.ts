import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ILoanDisbursement, NewLoanDisbursement } from '../loan-disbursement.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILoanDisbursement for edit and NewLoanDisbursementFormGroupInput for create.
 */
type LoanDisbursementFormGroupInput = ILoanDisbursement | PartialWithRequiredKeyOf<NewLoanDisbursement>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ILoanDisbursement | NewLoanDisbursement> = Omit<T, 'dtDisbDate' | 'lastModified'> & {
  dtDisbDate?: string | null;
  lastModified?: string | null;
};

type LoanDisbursementFormRawValue = FormValueOf<ILoanDisbursement>;

type NewLoanDisbursementFormRawValue = FormValueOf<NewLoanDisbursement>;

type LoanDisbursementFormDefaults = Pick<NewLoanDisbursement, 'id' | 'dtDisbDate' | 'lastModified'>;

type LoanDisbursementFormGroupContent = {
  id: FormControl<LoanDisbursementFormRawValue['id'] | NewLoanDisbursement['id']>;
  dtDisbDate: FormControl<LoanDisbursementFormRawValue['dtDisbDate']>;
  accountNo: FormControl<LoanDisbursementFormRawValue['accountNo']>;
  ifscCode: FormControl<LoanDisbursementFormRawValue['ifscCode']>;
  disbAmount: FormControl<LoanDisbursementFormRawValue['disbAmount']>;
  disbuNumber: FormControl<LoanDisbursementFormRawValue['disbuNumber']>;
  disbursementStatus: FormControl<LoanDisbursementFormRawValue['disbursementStatus']>;
  paymentMode: FormControl<LoanDisbursementFormRawValue['paymentMode']>;
  utrNo: FormControl<LoanDisbursementFormRawValue['utrNo']>;
  lastModified: FormControl<LoanDisbursementFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<LoanDisbursementFormRawValue['lastModifiedBy']>;
  freeField1: FormControl<LoanDisbursementFormRawValue['freeField1']>;
  freeField2: FormControl<LoanDisbursementFormRawValue['freeField2']>;
  freeField3: FormControl<LoanDisbursementFormRawValue['freeField3']>;
  freeField4: FormControl<LoanDisbursementFormRawValue['freeField4']>;
  freeField5: FormControl<LoanDisbursementFormRawValue['freeField5']>;
  freeField6: FormControl<LoanDisbursementFormRawValue['freeField6']>;
  product: FormControl<LoanDisbursementFormRawValue['product']>;
  securityUser: FormControl<LoanDisbursementFormRawValue['securityUser']>;
  loanAccount: FormControl<LoanDisbursementFormRawValue['loanAccount']>;
};

export type LoanDisbursementFormGroup = FormGroup<LoanDisbursementFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LoanDisbursementFormService {
  createLoanDisbursementFormGroup(loanDisbursement: LoanDisbursementFormGroupInput = { id: null }): LoanDisbursementFormGroup {
    const loanDisbursementRawValue = this.convertLoanDisbursementToLoanDisbursementRawValue({
      ...this.getFormDefaults(),
      ...loanDisbursement,
    });
    return new FormGroup<LoanDisbursementFormGroupContent>({
      id: new FormControl(
        { value: loanDisbursementRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      dtDisbDate: new FormControl(loanDisbursementRawValue.dtDisbDate),
      accountNo: new FormControl(loanDisbursementRawValue.accountNo),
      ifscCode: new FormControl(loanDisbursementRawValue.ifscCode),
      disbAmount: new FormControl(loanDisbursementRawValue.disbAmount),
      disbuNumber: new FormControl(loanDisbursementRawValue.disbuNumber),
      disbursementStatus: new FormControl(loanDisbursementRawValue.disbursementStatus),
      paymentMode: new FormControl(loanDisbursementRawValue.paymentMode),
      utrNo: new FormControl(loanDisbursementRawValue.utrNo),
      lastModified: new FormControl(loanDisbursementRawValue.lastModified),
      lastModifiedBy: new FormControl(loanDisbursementRawValue.lastModifiedBy),
      freeField1: new FormControl(loanDisbursementRawValue.freeField1),
      freeField2: new FormControl(loanDisbursementRawValue.freeField2),
      freeField3: new FormControl(loanDisbursementRawValue.freeField3),
      freeField4: new FormControl(loanDisbursementRawValue.freeField4),
      freeField5: new FormControl(loanDisbursementRawValue.freeField5),
      freeField6: new FormControl(loanDisbursementRawValue.freeField6),
      product: new FormControl(loanDisbursementRawValue.product),
      securityUser: new FormControl(loanDisbursementRawValue.securityUser),
      loanAccount: new FormControl(loanDisbursementRawValue.loanAccount),
    });
  }

  getLoanDisbursement(form: LoanDisbursementFormGroup): ILoanDisbursement | NewLoanDisbursement {
    return this.convertLoanDisbursementRawValueToLoanDisbursement(
      form.getRawValue() as LoanDisbursementFormRawValue | NewLoanDisbursementFormRawValue
    );
  }

  resetForm(form: LoanDisbursementFormGroup, loanDisbursement: LoanDisbursementFormGroupInput): void {
    const loanDisbursementRawValue = this.convertLoanDisbursementToLoanDisbursementRawValue({
      ...this.getFormDefaults(),
      ...loanDisbursement,
    });
    form.reset(
      {
        ...loanDisbursementRawValue,
        id: { value: loanDisbursementRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): LoanDisbursementFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dtDisbDate: currentTime,
      lastModified: currentTime,
    };
  }

  private convertLoanDisbursementRawValueToLoanDisbursement(
    rawLoanDisbursement: LoanDisbursementFormRawValue | NewLoanDisbursementFormRawValue
  ): ILoanDisbursement | NewLoanDisbursement {
    return {
      ...rawLoanDisbursement,
      dtDisbDate: dayjs(rawLoanDisbursement.dtDisbDate, DATE_TIME_FORMAT),
      lastModified: dayjs(rawLoanDisbursement.lastModified, DATE_TIME_FORMAT),
    };
  }

  private convertLoanDisbursementToLoanDisbursementRawValue(
    loanDisbursement: ILoanDisbursement | (Partial<NewLoanDisbursement> & LoanDisbursementFormDefaults)
  ): LoanDisbursementFormRawValue | PartialWithRequiredKeyOf<NewLoanDisbursementFormRawValue> {
    return {
      ...loanDisbursement,
      dtDisbDate: loanDisbursement.dtDisbDate ? loanDisbursement.dtDisbDate.format(DATE_TIME_FORMAT) : undefined,
      lastModified: loanDisbursement.lastModified ? loanDisbursement.lastModified.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
