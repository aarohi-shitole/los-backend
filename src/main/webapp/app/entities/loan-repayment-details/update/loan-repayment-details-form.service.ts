import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ILoanRepaymentDetails, NewLoanRepaymentDetails } from '../loan-repayment-details.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILoanRepaymentDetails for edit and NewLoanRepaymentDetailsFormGroupInput for create.
 */
type LoanRepaymentDetailsFormGroupInput = ILoanRepaymentDetails | PartialWithRequiredKeyOf<NewLoanRepaymentDetails>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ILoanRepaymentDetails | NewLoanRepaymentDetails> = Omit<T, 'repaymentDate' | 'lastModified'> & {
  repaymentDate?: string | null;
  lastModified?: string | null;
};

type LoanRepaymentDetailsFormRawValue = FormValueOf<ILoanRepaymentDetails>;

type NewLoanRepaymentDetailsFormRawValue = FormValueOf<NewLoanRepaymentDetails>;

type LoanRepaymentDetailsFormDefaults = Pick<NewLoanRepaymentDetails, 'id' | 'repaymentDate' | 'lastModified'>;

type LoanRepaymentDetailsFormGroupContent = {
  id: FormControl<LoanRepaymentDetailsFormRawValue['id'] | NewLoanRepaymentDetails['id']>;
  repaymentDate: FormControl<LoanRepaymentDetailsFormRawValue['repaymentDate']>;
  totalRepaymentAmt: FormControl<LoanRepaymentDetailsFormRawValue['totalRepaymentAmt']>;
  installmentNumber: FormControl<LoanRepaymentDetailsFormRawValue['installmentNumber']>;
  principlePaidAmt: FormControl<LoanRepaymentDetailsFormRawValue['principlePaidAmt']>;
  interestPaidAmt: FormControl<LoanRepaymentDetailsFormRawValue['interestPaidAmt']>;
  surChargeAmt: FormControl<LoanRepaymentDetailsFormRawValue['surChargeAmt']>;
  overDueAmt: FormControl<LoanRepaymentDetailsFormRawValue['overDueAmt']>;
  balanceInterestAmt: FormControl<LoanRepaymentDetailsFormRawValue['balanceInterestAmt']>;
  balancePrincipleAmt: FormControl<LoanRepaymentDetailsFormRawValue['balancePrincipleAmt']>;
  paymentMode: FormControl<LoanRepaymentDetailsFormRawValue['paymentMode']>;
  foreClosureChargeAmt: FormControl<LoanRepaymentDetailsFormRawValue['foreClosureChargeAmt']>;
  lastModified: FormControl<LoanRepaymentDetailsFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<LoanRepaymentDetailsFormRawValue['lastModifiedBy']>;
  freeField1: FormControl<LoanRepaymentDetailsFormRawValue['freeField1']>;
  freeField2: FormControl<LoanRepaymentDetailsFormRawValue['freeField2']>;
  freeField3: FormControl<LoanRepaymentDetailsFormRawValue['freeField3']>;
  freeField4: FormControl<LoanRepaymentDetailsFormRawValue['freeField4']>;
  freeField5: FormControl<LoanRepaymentDetailsFormRawValue['freeField5']>;
  freeField6: FormControl<LoanRepaymentDetailsFormRawValue['freeField6']>;
  loanAccount: FormControl<LoanRepaymentDetailsFormRawValue['loanAccount']>;
};

export type LoanRepaymentDetailsFormGroup = FormGroup<LoanRepaymentDetailsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LoanRepaymentDetailsFormService {
  createLoanRepaymentDetailsFormGroup(
    loanRepaymentDetails: LoanRepaymentDetailsFormGroupInput = { id: null }
  ): LoanRepaymentDetailsFormGroup {
    const loanRepaymentDetailsRawValue = this.convertLoanRepaymentDetailsToLoanRepaymentDetailsRawValue({
      ...this.getFormDefaults(),
      ...loanRepaymentDetails,
    });
    return new FormGroup<LoanRepaymentDetailsFormGroupContent>({
      id: new FormControl(
        { value: loanRepaymentDetailsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      repaymentDate: new FormControl(loanRepaymentDetailsRawValue.repaymentDate),
      totalRepaymentAmt: new FormControl(loanRepaymentDetailsRawValue.totalRepaymentAmt),
      installmentNumber: new FormControl(loanRepaymentDetailsRawValue.installmentNumber),
      principlePaidAmt: new FormControl(loanRepaymentDetailsRawValue.principlePaidAmt),
      interestPaidAmt: new FormControl(loanRepaymentDetailsRawValue.interestPaidAmt),
      surChargeAmt: new FormControl(loanRepaymentDetailsRawValue.surChargeAmt),
      overDueAmt: new FormControl(loanRepaymentDetailsRawValue.overDueAmt),
      balanceInterestAmt: new FormControl(loanRepaymentDetailsRawValue.balanceInterestAmt),
      balancePrincipleAmt: new FormControl(loanRepaymentDetailsRawValue.balancePrincipleAmt),
      paymentMode: new FormControl(loanRepaymentDetailsRawValue.paymentMode),
      foreClosureChargeAmt: new FormControl(loanRepaymentDetailsRawValue.foreClosureChargeAmt),
      lastModified: new FormControl(loanRepaymentDetailsRawValue.lastModified),
      lastModifiedBy: new FormControl(loanRepaymentDetailsRawValue.lastModifiedBy),
      freeField1: new FormControl(loanRepaymentDetailsRawValue.freeField1),
      freeField2: new FormControl(loanRepaymentDetailsRawValue.freeField2),
      freeField3: new FormControl(loanRepaymentDetailsRawValue.freeField3),
      freeField4: new FormControl(loanRepaymentDetailsRawValue.freeField4),
      freeField5: new FormControl(loanRepaymentDetailsRawValue.freeField5),
      freeField6: new FormControl(loanRepaymentDetailsRawValue.freeField6),
      loanAccount: new FormControl(loanRepaymentDetailsRawValue.loanAccount),
    });
  }

  getLoanRepaymentDetails(form: LoanRepaymentDetailsFormGroup): ILoanRepaymentDetails | NewLoanRepaymentDetails {
    return this.convertLoanRepaymentDetailsRawValueToLoanRepaymentDetails(
      form.getRawValue() as LoanRepaymentDetailsFormRawValue | NewLoanRepaymentDetailsFormRawValue
    );
  }

  resetForm(form: LoanRepaymentDetailsFormGroup, loanRepaymentDetails: LoanRepaymentDetailsFormGroupInput): void {
    const loanRepaymentDetailsRawValue = this.convertLoanRepaymentDetailsToLoanRepaymentDetailsRawValue({
      ...this.getFormDefaults(),
      ...loanRepaymentDetails,
    });
    form.reset(
      {
        ...loanRepaymentDetailsRawValue,
        id: { value: loanRepaymentDetailsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): LoanRepaymentDetailsFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      repaymentDate: currentTime,
      lastModified: currentTime,
    };
  }

  private convertLoanRepaymentDetailsRawValueToLoanRepaymentDetails(
    rawLoanRepaymentDetails: LoanRepaymentDetailsFormRawValue | NewLoanRepaymentDetailsFormRawValue
  ): ILoanRepaymentDetails | NewLoanRepaymentDetails {
    return {
      ...rawLoanRepaymentDetails,
      repaymentDate: dayjs(rawLoanRepaymentDetails.repaymentDate, DATE_TIME_FORMAT),
      lastModified: dayjs(rawLoanRepaymentDetails.lastModified, DATE_TIME_FORMAT),
    };
  }

  private convertLoanRepaymentDetailsToLoanRepaymentDetailsRawValue(
    loanRepaymentDetails: ILoanRepaymentDetails | (Partial<NewLoanRepaymentDetails> & LoanRepaymentDetailsFormDefaults)
  ): LoanRepaymentDetailsFormRawValue | PartialWithRequiredKeyOf<NewLoanRepaymentDetailsFormRawValue> {
    return {
      ...loanRepaymentDetails,
      repaymentDate: loanRepaymentDetails.repaymentDate ? loanRepaymentDetails.repaymentDate.format(DATE_TIME_FORMAT) : undefined,
      lastModified: loanRepaymentDetails.lastModified ? loanRepaymentDetails.lastModified.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
