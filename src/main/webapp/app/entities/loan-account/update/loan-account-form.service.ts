import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ILoanAccount, NewLoanAccount } from '../loan-account.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILoanAccount for edit and NewLoanAccountFormGroupInput for create.
 */
type LoanAccountFormGroupInput = ILoanAccount | PartialWithRequiredKeyOf<NewLoanAccount>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ILoanAccount | NewLoanAccount> = Omit<
  T,
  'loanStartDate' | 'loanEndDate' | 'loanPlannedClosureDate' | 'loanCloserDate' | 'emiStartDate' | 'lastModified'
> & {
  loanStartDate?: string | null;
  loanEndDate?: string | null;
  loanPlannedClosureDate?: string | null;
  loanCloserDate?: string | null;
  emiStartDate?: string | null;
  lastModified?: string | null;
};

type LoanAccountFormRawValue = FormValueOf<ILoanAccount>;

type NewLoanAccountFormRawValue = FormValueOf<NewLoanAccount>;

type LoanAccountFormDefaults = Pick<
  NewLoanAccount,
  'id' | 'loanStartDate' | 'loanEndDate' | 'loanPlannedClosureDate' | 'loanCloserDate' | 'emiStartDate' | 'lastModified'
>;

type LoanAccountFormGroupContent = {
  id: FormControl<LoanAccountFormRawValue['id'] | NewLoanAccount['id']>;
  loanAmount: FormControl<LoanAccountFormRawValue['loanAmount']>;
  applicationNo: FormControl<LoanAccountFormRawValue['applicationNo']>;
  status: FormControl<LoanAccountFormRawValue['status']>;
  loanStartDate: FormControl<LoanAccountFormRawValue['loanStartDate']>;
  loanEndDate: FormControl<LoanAccountFormRawValue['loanEndDate']>;
  loanPlannedClosureDate: FormControl<LoanAccountFormRawValue['loanPlannedClosureDate']>;
  loanCloserDate: FormControl<LoanAccountFormRawValue['loanCloserDate']>;
  emiStartDate: FormControl<LoanAccountFormRawValue['emiStartDate']>;
  loanNpaClass: FormControl<LoanAccountFormRawValue['loanNpaClass']>;
  parentAccHeadCode: FormControl<LoanAccountFormRawValue['parentAccHeadCode']>;
  loanAccountName: FormControl<LoanAccountFormRawValue['loanAccountName']>;
  disbursementAmt: FormControl<LoanAccountFormRawValue['disbursementAmt']>;
  disbursementStatus: FormControl<LoanAccountFormRawValue['disbursementStatus']>;
  repaymentPeriod: FormControl<LoanAccountFormRawValue['repaymentPeriod']>;
  year: FormControl<LoanAccountFormRawValue['year']>;
  processingFee: FormControl<LoanAccountFormRawValue['processingFee']>;
  moratorium: FormControl<LoanAccountFormRawValue['moratorium']>;
  roi: FormControl<LoanAccountFormRawValue['roi']>;
  lastModified: FormControl<LoanAccountFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<LoanAccountFormRawValue['lastModifiedBy']>;
  freeField1: FormControl<LoanAccountFormRawValue['freeField1']>;
  freeField2: FormControl<LoanAccountFormRawValue['freeField2']>;
  freeField3: FormControl<LoanAccountFormRawValue['freeField3']>;
  freeField4: FormControl<LoanAccountFormRawValue['freeField4']>;
  freeField5: FormControl<LoanAccountFormRawValue['freeField5']>;
  freeField6: FormControl<LoanAccountFormRawValue['freeField6']>;
  loanApplications: FormControl<LoanAccountFormRawValue['loanApplications']>;
  member: FormControl<LoanAccountFormRawValue['member']>;
  product: FormControl<LoanAccountFormRawValue['product']>;
};

export type LoanAccountFormGroup = FormGroup<LoanAccountFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LoanAccountFormService {
  createLoanAccountFormGroup(loanAccount: LoanAccountFormGroupInput = { id: null }): LoanAccountFormGroup {
    const loanAccountRawValue = this.convertLoanAccountToLoanAccountRawValue({
      ...this.getFormDefaults(),
      ...loanAccount,
    });
    return new FormGroup<LoanAccountFormGroupContent>({
      id: new FormControl(
        { value: loanAccountRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      loanAmount: new FormControl(loanAccountRawValue.loanAmount),
      applicationNo: new FormControl(loanAccountRawValue.applicationNo),
      status: new FormControl(loanAccountRawValue.status),
      loanStartDate: new FormControl(loanAccountRawValue.loanStartDate),
      loanEndDate: new FormControl(loanAccountRawValue.loanEndDate),
      loanPlannedClosureDate: new FormControl(loanAccountRawValue.loanPlannedClosureDate),
      loanCloserDate: new FormControl(loanAccountRawValue.loanCloserDate),
      emiStartDate: new FormControl(loanAccountRawValue.emiStartDate),
      loanNpaClass: new FormControl(loanAccountRawValue.loanNpaClass),
      parentAccHeadCode: new FormControl(loanAccountRawValue.parentAccHeadCode),
      loanAccountName: new FormControl(loanAccountRawValue.loanAccountName),
      disbursementAmt: new FormControl(loanAccountRawValue.disbursementAmt),
      disbursementStatus: new FormControl(loanAccountRawValue.disbursementStatus),
      repaymentPeriod: new FormControl(loanAccountRawValue.repaymentPeriod),
      year: new FormControl(loanAccountRawValue.year),
      processingFee: new FormControl(loanAccountRawValue.processingFee),
      moratorium: new FormControl(loanAccountRawValue.moratorium),
      roi: new FormControl(loanAccountRawValue.roi),
      lastModified: new FormControl(loanAccountRawValue.lastModified),
      lastModifiedBy: new FormControl(loanAccountRawValue.lastModifiedBy),
      freeField1: new FormControl(loanAccountRawValue.freeField1),
      freeField2: new FormControl(loanAccountRawValue.freeField2),
      freeField3: new FormControl(loanAccountRawValue.freeField3),
      freeField4: new FormControl(loanAccountRawValue.freeField4),
      freeField5: new FormControl(loanAccountRawValue.freeField5),
      freeField6: new FormControl(loanAccountRawValue.freeField6),
      loanApplications: new FormControl(loanAccountRawValue.loanApplications),
      member: new FormControl(loanAccountRawValue.member),
      product: new FormControl(loanAccountRawValue.product),
    });
  }

  getLoanAccount(form: LoanAccountFormGroup): ILoanAccount | NewLoanAccount {
    return this.convertLoanAccountRawValueToLoanAccount(form.getRawValue() as LoanAccountFormRawValue | NewLoanAccountFormRawValue);
  }

  resetForm(form: LoanAccountFormGroup, loanAccount: LoanAccountFormGroupInput): void {
    const loanAccountRawValue = this.convertLoanAccountToLoanAccountRawValue({ ...this.getFormDefaults(), ...loanAccount });
    form.reset(
      {
        ...loanAccountRawValue,
        id: { value: loanAccountRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): LoanAccountFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      loanStartDate: currentTime,
      loanEndDate: currentTime,
      loanPlannedClosureDate: currentTime,
      loanCloserDate: currentTime,
      emiStartDate: currentTime,
      lastModified: currentTime,
    };
  }

  private convertLoanAccountRawValueToLoanAccount(
    rawLoanAccount: LoanAccountFormRawValue | NewLoanAccountFormRawValue
  ): ILoanAccount | NewLoanAccount {
    return {
      ...rawLoanAccount,
      loanStartDate: dayjs(rawLoanAccount.loanStartDate, DATE_TIME_FORMAT),
      loanEndDate: dayjs(rawLoanAccount.loanEndDate, DATE_TIME_FORMAT),
      loanPlannedClosureDate: dayjs(rawLoanAccount.loanPlannedClosureDate, DATE_TIME_FORMAT),
      loanCloserDate: dayjs(rawLoanAccount.loanCloserDate, DATE_TIME_FORMAT),
      emiStartDate: dayjs(rawLoanAccount.emiStartDate, DATE_TIME_FORMAT),
      lastModified: dayjs(rawLoanAccount.lastModified, DATE_TIME_FORMAT),
    };
  }

  private convertLoanAccountToLoanAccountRawValue(
    loanAccount: ILoanAccount | (Partial<NewLoanAccount> & LoanAccountFormDefaults)
  ): LoanAccountFormRawValue | PartialWithRequiredKeyOf<NewLoanAccountFormRawValue> {
    return {
      ...loanAccount,
      loanStartDate: loanAccount.loanStartDate ? loanAccount.loanStartDate.format(DATE_TIME_FORMAT) : undefined,
      loanEndDate: loanAccount.loanEndDate ? loanAccount.loanEndDate.format(DATE_TIME_FORMAT) : undefined,
      loanPlannedClosureDate: loanAccount.loanPlannedClosureDate ? loanAccount.loanPlannedClosureDate.format(DATE_TIME_FORMAT) : undefined,
      loanCloserDate: loanAccount.loanCloserDate ? loanAccount.loanCloserDate.format(DATE_TIME_FORMAT) : undefined,
      emiStartDate: loanAccount.emiStartDate ? loanAccount.emiStartDate.format(DATE_TIME_FORMAT) : undefined,
      lastModified: loanAccount.lastModified ? loanAccount.lastModified.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
