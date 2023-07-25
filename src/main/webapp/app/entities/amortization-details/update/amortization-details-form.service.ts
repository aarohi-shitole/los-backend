import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAmortizationDetails, NewAmortizationDetails } from '../amortization-details.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAmortizationDetails for edit and NewAmortizationDetailsFormGroupInput for create.
 */
type AmortizationDetailsFormGroupInput = IAmortizationDetails | PartialWithRequiredKeyOf<NewAmortizationDetails>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IAmortizationDetails | NewAmortizationDetails> = Omit<T, 'installmentDate' | 'lastModified'> & {
  installmentDate?: string | null;
  lastModified?: string | null;
};

type AmortizationDetailsFormRawValue = FormValueOf<IAmortizationDetails>;

type NewAmortizationDetailsFormRawValue = FormValueOf<NewAmortizationDetails>;

type AmortizationDetailsFormDefaults = Pick<NewAmortizationDetails, 'id' | 'installmentDate' | 'lastModified'>;

type AmortizationDetailsFormGroupContent = {
  id: FormControl<AmortizationDetailsFormRawValue['id'] | NewAmortizationDetails['id']>;
  installmentDate: FormControl<AmortizationDetailsFormRawValue['installmentDate']>;
  totalOutstandingPrincipleAmt: FormControl<AmortizationDetailsFormRawValue['totalOutstandingPrincipleAmt']>;
  totalOutstandngInterestAmt: FormControl<AmortizationDetailsFormRawValue['totalOutstandngInterestAmt']>;
  paidPrincipleAmt: FormControl<AmortizationDetailsFormRawValue['paidPrincipleAmt']>;
  paidInterestAmt: FormControl<AmortizationDetailsFormRawValue['paidInterestAmt']>;
  balPrincipleAmt: FormControl<AmortizationDetailsFormRawValue['balPrincipleAmt']>;
  balInterestAmt: FormControl<AmortizationDetailsFormRawValue['balInterestAmt']>;
  loanEmiAmt: FormControl<AmortizationDetailsFormRawValue['loanEmiAmt']>;
  principleEMI: FormControl<AmortizationDetailsFormRawValue['principleEMI']>;
  roi: FormControl<AmortizationDetailsFormRawValue['roi']>;
  paymentStatus: FormControl<AmortizationDetailsFormRawValue['paymentStatus']>;
  year: FormControl<AmortizationDetailsFormRawValue['year']>;
  lastModified: FormControl<AmortizationDetailsFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<AmortizationDetailsFormRawValue['lastModifiedBy']>;
  freeField1: FormControl<AmortizationDetailsFormRawValue['freeField1']>;
  freeField2: FormControl<AmortizationDetailsFormRawValue['freeField2']>;
  freeField3: FormControl<AmortizationDetailsFormRawValue['freeField3']>;
  freeField4: FormControl<AmortizationDetailsFormRawValue['freeField4']>;
  freeField5: FormControl<AmortizationDetailsFormRawValue['freeField5']>;
  freeField6: FormControl<AmortizationDetailsFormRawValue['freeField6']>;
  loanAccount: FormControl<AmortizationDetailsFormRawValue['loanAccount']>;
};

export type AmortizationDetailsFormGroup = FormGroup<AmortizationDetailsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AmortizationDetailsFormService {
  createAmortizationDetailsFormGroup(amortizationDetails: AmortizationDetailsFormGroupInput = { id: null }): AmortizationDetailsFormGroup {
    const amortizationDetailsRawValue = this.convertAmortizationDetailsToAmortizationDetailsRawValue({
      ...this.getFormDefaults(),
      ...amortizationDetails,
    });
    return new FormGroup<AmortizationDetailsFormGroupContent>({
      id: new FormControl(
        { value: amortizationDetailsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      installmentDate: new FormControl(amortizationDetailsRawValue.installmentDate),
      totalOutstandingPrincipleAmt: new FormControl(amortizationDetailsRawValue.totalOutstandingPrincipleAmt),
      totalOutstandngInterestAmt: new FormControl(amortizationDetailsRawValue.totalOutstandngInterestAmt),
      paidPrincipleAmt: new FormControl(amortizationDetailsRawValue.paidPrincipleAmt),
      paidInterestAmt: new FormControl(amortizationDetailsRawValue.paidInterestAmt),
      balPrincipleAmt: new FormControl(amortizationDetailsRawValue.balPrincipleAmt),
      balInterestAmt: new FormControl(amortizationDetailsRawValue.balInterestAmt),
      loanEmiAmt: new FormControl(amortizationDetailsRawValue.loanEmiAmt),
      principleEMI: new FormControl(amortizationDetailsRawValue.principleEMI),
      roi: new FormControl(amortizationDetailsRawValue.roi),
      paymentStatus: new FormControl(amortizationDetailsRawValue.paymentStatus),
      year: new FormControl(amortizationDetailsRawValue.year),
      lastModified: new FormControl(amortizationDetailsRawValue.lastModified),
      lastModifiedBy: new FormControl(amortizationDetailsRawValue.lastModifiedBy),
      freeField1: new FormControl(amortizationDetailsRawValue.freeField1),
      freeField2: new FormControl(amortizationDetailsRawValue.freeField2),
      freeField3: new FormControl(amortizationDetailsRawValue.freeField3),
      freeField4: new FormControl(amortizationDetailsRawValue.freeField4),
      freeField5: new FormControl(amortizationDetailsRawValue.freeField5),
      freeField6: new FormControl(amortizationDetailsRawValue.freeField6),
      loanAccount: new FormControl(amortizationDetailsRawValue.loanAccount),
    });
  }

  getAmortizationDetails(form: AmortizationDetailsFormGroup): IAmortizationDetails | NewAmortizationDetails {
    return this.convertAmortizationDetailsRawValueToAmortizationDetails(
      form.getRawValue() as AmortizationDetailsFormRawValue | NewAmortizationDetailsFormRawValue
    );
  }

  resetForm(form: AmortizationDetailsFormGroup, amortizationDetails: AmortizationDetailsFormGroupInput): void {
    const amortizationDetailsRawValue = this.convertAmortizationDetailsToAmortizationDetailsRawValue({
      ...this.getFormDefaults(),
      ...amortizationDetails,
    });
    form.reset(
      {
        ...amortizationDetailsRawValue,
        id: { value: amortizationDetailsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AmortizationDetailsFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      installmentDate: currentTime,
      lastModified: currentTime,
    };
  }

  private convertAmortizationDetailsRawValueToAmortizationDetails(
    rawAmortizationDetails: AmortizationDetailsFormRawValue | NewAmortizationDetailsFormRawValue
  ): IAmortizationDetails | NewAmortizationDetails {
    return {
      ...rawAmortizationDetails,
      installmentDate: dayjs(rawAmortizationDetails.installmentDate, DATE_TIME_FORMAT),
      lastModified: dayjs(rawAmortizationDetails.lastModified, DATE_TIME_FORMAT),
    };
  }

  private convertAmortizationDetailsToAmortizationDetailsRawValue(
    amortizationDetails: IAmortizationDetails | (Partial<NewAmortizationDetails> & AmortizationDetailsFormDefaults)
  ): AmortizationDetailsFormRawValue | PartialWithRequiredKeyOf<NewAmortizationDetailsFormRawValue> {
    return {
      ...amortizationDetails,
      installmentDate: amortizationDetails.installmentDate ? amortizationDetails.installmentDate.format(DATE_TIME_FORMAT) : undefined,
      lastModified: amortizationDetails.lastModified ? amortizationDetails.lastModified.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
