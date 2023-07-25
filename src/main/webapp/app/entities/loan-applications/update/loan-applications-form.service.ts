import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ILoanApplications, NewLoanApplications } from '../loan-applications.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILoanApplications for edit and NewLoanApplicationsFormGroupInput for create.
 */
type LoanApplicationsFormGroupInput = ILoanApplications | PartialWithRequiredKeyOf<NewLoanApplications>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ILoanApplications | NewLoanApplications> = Omit<T, 'mortgageDate' | 'lastModified'> & {
  mortgageDate?: string | null;
  lastModified?: string | null;
};

type LoanApplicationsFormRawValue = FormValueOf<ILoanApplications>;

type NewLoanApplicationsFormRawValue = FormValueOf<NewLoanApplications>;

type LoanApplicationsFormDefaults = Pick<NewLoanApplications, 'id' | 'isInsured' | 'mortgageDate' | 'lastModified'>;

type LoanApplicationsFormGroupContent = {
  id: FormControl<LoanApplicationsFormRawValue['id'] | NewLoanApplications['id']>;
  applicationNo: FormControl<LoanApplicationsFormRawValue['applicationNo']>;
  demandAmount: FormControl<LoanApplicationsFormRawValue['demandAmount']>;
  loanPurpose: FormControl<LoanApplicationsFormRawValue['loanPurpose']>;
  status: FormControl<LoanApplicationsFormRawValue['status']>;
  demandedLandAreaInHector: FormControl<LoanApplicationsFormRawValue['demandedLandAreaInHector']>;
  seasonOfCropLoan: FormControl<LoanApplicationsFormRawValue['seasonOfCropLoan']>;
  loanSteps: FormControl<LoanApplicationsFormRawValue['loanSteps']>;
  isInsured: FormControl<LoanApplicationsFormRawValue['isInsured']>;
  loanBenefitingArea: FormControl<LoanApplicationsFormRawValue['loanBenefitingArea']>;
  costOfInvestment: FormControl<LoanApplicationsFormRawValue['costOfInvestment']>;
  mortgageDeedNo: FormControl<LoanApplicationsFormRawValue['mortgageDeedNo']>;
  mortgageDate: FormControl<LoanApplicationsFormRawValue['mortgageDate']>;
  extentMorgageValue: FormControl<LoanApplicationsFormRawValue['extentMorgageValue']>;
  downPaymentAmt: FormControl<LoanApplicationsFormRawValue['downPaymentAmt']>;
  ltvRatio: FormControl<LoanApplicationsFormRawValue['ltvRatio']>;
  processingFee: FormControl<LoanApplicationsFormRawValue['processingFee']>;
  penalInterest: FormControl<LoanApplicationsFormRawValue['penalInterest']>;
  moratorium: FormControl<LoanApplicationsFormRawValue['moratorium']>;
  roi: FormControl<LoanApplicationsFormRawValue['roi']>;
  commityAmt: FormControl<LoanApplicationsFormRawValue['commityAmt']>;
  commityRoi: FormControl<LoanApplicationsFormRawValue['commityRoi']>;
  sectionAmt: FormControl<LoanApplicationsFormRawValue['sectionAmt']>;
  senctionRoi: FormControl<LoanApplicationsFormRawValue['senctionRoi']>;
  year: FormControl<LoanApplicationsFormRawValue['year']>;
  assignedTo: FormControl<LoanApplicationsFormRawValue['assignedTo']>;
  assignedFrom: FormControl<LoanApplicationsFormRawValue['assignedFrom']>;
  securityDocUrl: FormControl<LoanApplicationsFormRawValue['securityDocUrl']>;
  lastModified: FormControl<LoanApplicationsFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<LoanApplicationsFormRawValue['lastModifiedBy']>;
  freeField1: FormControl<LoanApplicationsFormRawValue['freeField1']>;
  freeField2: FormControl<LoanApplicationsFormRawValue['freeField2']>;
  freeField3: FormControl<LoanApplicationsFormRawValue['freeField3']>;
  freeField4: FormControl<LoanApplicationsFormRawValue['freeField4']>;
  freeField5: FormControl<LoanApplicationsFormRawValue['freeField5']>;
  freeField6: FormControl<LoanApplicationsFormRawValue['freeField6']>;
  freeField7: FormControl<LoanApplicationsFormRawValue['freeField7']>;
  member: FormControl<LoanApplicationsFormRawValue['member']>;
  securityUser: FormControl<LoanApplicationsFormRawValue['securityUser']>;
  product: FormControl<LoanApplicationsFormRawValue['product']>;
};

export type LoanApplicationsFormGroup = FormGroup<LoanApplicationsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LoanApplicationsFormService {
  createLoanApplicationsFormGroup(loanApplications: LoanApplicationsFormGroupInput = { id: null }): LoanApplicationsFormGroup {
    const loanApplicationsRawValue = this.convertLoanApplicationsToLoanApplicationsRawValue({
      ...this.getFormDefaults(),
      ...loanApplications,
    });
    return new FormGroup<LoanApplicationsFormGroupContent>({
      id: new FormControl(
        { value: loanApplicationsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      applicationNo: new FormControl(loanApplicationsRawValue.applicationNo),
      demandAmount: new FormControl(loanApplicationsRawValue.demandAmount),
      loanPurpose: new FormControl(loanApplicationsRawValue.loanPurpose),
      status: new FormControl(loanApplicationsRawValue.status),
      demandedLandAreaInHector: new FormControl(loanApplicationsRawValue.demandedLandAreaInHector),
      seasonOfCropLoan: new FormControl(loanApplicationsRawValue.seasonOfCropLoan),
      loanSteps: new FormControl(loanApplicationsRawValue.loanSteps),
      isInsured: new FormControl(loanApplicationsRawValue.isInsured),
      loanBenefitingArea: new FormControl(loanApplicationsRawValue.loanBenefitingArea),
      costOfInvestment: new FormControl(loanApplicationsRawValue.costOfInvestment),
      mortgageDeedNo: new FormControl(loanApplicationsRawValue.mortgageDeedNo),
      mortgageDate: new FormControl(loanApplicationsRawValue.mortgageDate),
      extentMorgageValue: new FormControl(loanApplicationsRawValue.extentMorgageValue),
      downPaymentAmt: new FormControl(loanApplicationsRawValue.downPaymentAmt),
      ltvRatio: new FormControl(loanApplicationsRawValue.ltvRatio),
      processingFee: new FormControl(loanApplicationsRawValue.processingFee),
      penalInterest: new FormControl(loanApplicationsRawValue.penalInterest),
      moratorium: new FormControl(loanApplicationsRawValue.moratorium),
      roi: new FormControl(loanApplicationsRawValue.roi),
      commityAmt: new FormControl(loanApplicationsRawValue.commityAmt),
      commityRoi: new FormControl(loanApplicationsRawValue.commityRoi),
      sectionAmt: new FormControl(loanApplicationsRawValue.sectionAmt),
      senctionRoi: new FormControl(loanApplicationsRawValue.senctionRoi),
      year: new FormControl(loanApplicationsRawValue.year),
      assignedTo: new FormControl(loanApplicationsRawValue.assignedTo),
      assignedFrom: new FormControl(loanApplicationsRawValue.assignedFrom),
      securityDocUrl: new FormControl(loanApplicationsRawValue.securityDocUrl),
      lastModified: new FormControl(loanApplicationsRawValue.lastModified),
      lastModifiedBy: new FormControl(loanApplicationsRawValue.lastModifiedBy),
      freeField1: new FormControl(loanApplicationsRawValue.freeField1),
      freeField2: new FormControl(loanApplicationsRawValue.freeField2),
      freeField3: new FormControl(loanApplicationsRawValue.freeField3),
      freeField4: new FormControl(loanApplicationsRawValue.freeField4),
      freeField5: new FormControl(loanApplicationsRawValue.freeField5),
      freeField6: new FormControl(loanApplicationsRawValue.freeField6),
      freeField7: new FormControl(loanApplicationsRawValue.freeField7),
      member: new FormControl(loanApplicationsRawValue.member),
      securityUser: new FormControl(loanApplicationsRawValue.securityUser),
      product: new FormControl(loanApplicationsRawValue.product),
    });
  }

  getLoanApplications(form: LoanApplicationsFormGroup): ILoanApplications | NewLoanApplications {
    return this.convertLoanApplicationsRawValueToLoanApplications(
      form.getRawValue() as LoanApplicationsFormRawValue | NewLoanApplicationsFormRawValue
    );
  }

  resetForm(form: LoanApplicationsFormGroup, loanApplications: LoanApplicationsFormGroupInput): void {
    const loanApplicationsRawValue = this.convertLoanApplicationsToLoanApplicationsRawValue({
      ...this.getFormDefaults(),
      ...loanApplications,
    });
    form.reset(
      {
        ...loanApplicationsRawValue,
        id: { value: loanApplicationsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): LoanApplicationsFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      isInsured: false,
      mortgageDate: currentTime,
      lastModified: currentTime,
    };
  }

  private convertLoanApplicationsRawValueToLoanApplications(
    rawLoanApplications: LoanApplicationsFormRawValue | NewLoanApplicationsFormRawValue
  ): ILoanApplications | NewLoanApplications {
    return {
      ...rawLoanApplications,
      mortgageDate: dayjs(rawLoanApplications.mortgageDate, DATE_TIME_FORMAT),
      lastModified: dayjs(rawLoanApplications.lastModified, DATE_TIME_FORMAT),
    };
  }

  private convertLoanApplicationsToLoanApplicationsRawValue(
    loanApplications: ILoanApplications | (Partial<NewLoanApplications> & LoanApplicationsFormDefaults)
  ): LoanApplicationsFormRawValue | PartialWithRequiredKeyOf<NewLoanApplicationsFormRawValue> {
    return {
      ...loanApplications,
      mortgageDate: loanApplications.mortgageDate ? loanApplications.mortgageDate.format(DATE_TIME_FORMAT) : undefined,
      lastModified: loanApplications.lastModified ? loanApplications.lastModified.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
