import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IEnquiryDetails, NewEnquiryDetails } from '../enquiry-details.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEnquiryDetails for edit and NewEnquiryDetailsFormGroupInput for create.
 */
type EnquiryDetailsFormGroupInput = IEnquiryDetails | PartialWithRequiredKeyOf<NewEnquiryDetails>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IEnquiryDetails | NewEnquiryDetails> = Omit<T, 'lastModified'> & {
  lastModified?: string | null;
};

type EnquiryDetailsFormRawValue = FormValueOf<IEnquiryDetails>;

type NewEnquiryDetailsFormRawValue = FormValueOf<NewEnquiryDetails>;

type EnquiryDetailsFormDefaults = Pick<NewEnquiryDetails, 'id' | 'isDeleted' | 'isActive' | 'lastModified'>;

type EnquiryDetailsFormGroupContent = {
  id: FormControl<EnquiryDetailsFormRawValue['id'] | NewEnquiryDetails['id']>;
  customerName: FormControl<EnquiryDetailsFormRawValue['customerName']>;
  subName: FormControl<EnquiryDetailsFormRawValue['subName']>;
  purpose: FormControl<EnquiryDetailsFormRawValue['purpose']>;
  mobileNo: FormControl<EnquiryDetailsFormRawValue['mobileNo']>;
  amount: FormControl<EnquiryDetailsFormRawValue['amount']>;
  refrenceNo: FormControl<EnquiryDetailsFormRawValue['refrenceNo']>;
  isDeleted: FormControl<EnquiryDetailsFormRawValue['isDeleted']>;
  isActive: FormControl<EnquiryDetailsFormRawValue['isActive']>;
  lastModified: FormControl<EnquiryDetailsFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<EnquiryDetailsFormRawValue['lastModifiedBy']>;
  freeField1: FormControl<EnquiryDetailsFormRawValue['freeField1']>;
  freeField2: FormControl<EnquiryDetailsFormRawValue['freeField2']>;
  freeField3: FormControl<EnquiryDetailsFormRawValue['freeField3']>;
  freeField4: FormControl<EnquiryDetailsFormRawValue['freeField4']>;
  state: FormControl<EnquiryDetailsFormRawValue['state']>;
  district: FormControl<EnquiryDetailsFormRawValue['district']>;
  taluka: FormControl<EnquiryDetailsFormRawValue['taluka']>;
  city: FormControl<EnquiryDetailsFormRawValue['city']>;
  product: FormControl<EnquiryDetailsFormRawValue['product']>;
};

export type EnquiryDetailsFormGroup = FormGroup<EnquiryDetailsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EnquiryDetailsFormService {
  createEnquiryDetailsFormGroup(enquiryDetails: EnquiryDetailsFormGroupInput = { id: null }): EnquiryDetailsFormGroup {
    const enquiryDetailsRawValue = this.convertEnquiryDetailsToEnquiryDetailsRawValue({
      ...this.getFormDefaults(),
      ...enquiryDetails,
    });
    return new FormGroup<EnquiryDetailsFormGroupContent>({
      id: new FormControl(
        { value: enquiryDetailsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      customerName: new FormControl(enquiryDetailsRawValue.customerName),
      subName: new FormControl(enquiryDetailsRawValue.subName),
      purpose: new FormControl(enquiryDetailsRawValue.purpose),
      mobileNo: new FormControl(enquiryDetailsRawValue.mobileNo),
      amount: new FormControl(enquiryDetailsRawValue.amount),
      refrenceNo: new FormControl(enquiryDetailsRawValue.refrenceNo),
      isDeleted: new FormControl(enquiryDetailsRawValue.isDeleted),
      isActive: new FormControl(enquiryDetailsRawValue.isActive),
      lastModified: new FormControl(enquiryDetailsRawValue.lastModified),
      lastModifiedBy: new FormControl(enquiryDetailsRawValue.lastModifiedBy),
      freeField1: new FormControl(enquiryDetailsRawValue.freeField1),
      freeField2: new FormControl(enquiryDetailsRawValue.freeField2),
      freeField3: new FormControl(enquiryDetailsRawValue.freeField3),
      freeField4: new FormControl(enquiryDetailsRawValue.freeField4),
      state: new FormControl(enquiryDetailsRawValue.state),
      district: new FormControl(enquiryDetailsRawValue.district),
      taluka: new FormControl(enquiryDetailsRawValue.taluka),
      city: new FormControl(enquiryDetailsRawValue.city),
      product: new FormControl(enquiryDetailsRawValue.product),
    });
  }

  getEnquiryDetails(form: EnquiryDetailsFormGroup): IEnquiryDetails | NewEnquiryDetails {
    return this.convertEnquiryDetailsRawValueToEnquiryDetails(
      form.getRawValue() as EnquiryDetailsFormRawValue | NewEnquiryDetailsFormRawValue
    );
  }

  resetForm(form: EnquiryDetailsFormGroup, enquiryDetails: EnquiryDetailsFormGroupInput): void {
    const enquiryDetailsRawValue = this.convertEnquiryDetailsToEnquiryDetailsRawValue({ ...this.getFormDefaults(), ...enquiryDetails });
    form.reset(
      {
        ...enquiryDetailsRawValue,
        id: { value: enquiryDetailsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): EnquiryDetailsFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      isDeleted: false,
      isActive: false,
      lastModified: currentTime,
    };
  }

  private convertEnquiryDetailsRawValueToEnquiryDetails(
    rawEnquiryDetails: EnquiryDetailsFormRawValue | NewEnquiryDetailsFormRawValue
  ): IEnquiryDetails | NewEnquiryDetails {
    return {
      ...rawEnquiryDetails,
      lastModified: dayjs(rawEnquiryDetails.lastModified, DATE_TIME_FORMAT),
    };
  }

  private convertEnquiryDetailsToEnquiryDetailsRawValue(
    enquiryDetails: IEnquiryDetails | (Partial<NewEnquiryDetails> & EnquiryDetailsFormDefaults)
  ): EnquiryDetailsFormRawValue | PartialWithRequiredKeyOf<NewEnquiryDetailsFormRawValue> {
    return {
      ...enquiryDetails,
      lastModified: enquiryDetails.lastModified ? enquiryDetails.lastModified.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
