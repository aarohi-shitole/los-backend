import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IEmployementDetails, NewEmployementDetails } from '../employement-details.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEmployementDetails for edit and NewEmployementDetailsFormGroupInput for create.
 */
type EmployementDetailsFormGroupInput = IEmployementDetails | PartialWithRequiredKeyOf<NewEmployementDetails>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IEmployementDetails | NewEmployementDetails> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

type EmployementDetailsFormRawValue = FormValueOf<IEmployementDetails>;

type NewEmployementDetailsFormRawValue = FormValueOf<NewEmployementDetails>;

type EmployementDetailsFormDefaults = Pick<NewEmployementDetails, 'id' | 'isDeleted' | 'lastModified' | 'createdOn'>;

type EmployementDetailsFormGroupContent = {
  id: FormControl<EmployementDetailsFormRawValue['id'] | NewEmployementDetails['id']>;
  type: FormControl<EmployementDetailsFormRawValue['type']>;
  employerName: FormControl<EmployementDetailsFormRawValue['employerName']>;
  status: FormControl<EmployementDetailsFormRawValue['status']>;
  designation: FormControl<EmployementDetailsFormRawValue['designation']>;
  duration: FormControl<EmployementDetailsFormRawValue['duration']>;
  employerAdd: FormControl<EmployementDetailsFormRawValue['employerAdd']>;
  prevCompanyName: FormControl<EmployementDetailsFormRawValue['prevCompanyName']>;
  prevCompanyduration: FormControl<EmployementDetailsFormRawValue['prevCompanyduration']>;
  orgType: FormControl<EmployementDetailsFormRawValue['orgType']>;
  constitutionType: FormControl<EmployementDetailsFormRawValue['constitutionType']>;
  industryType: FormControl<EmployementDetailsFormRawValue['industryType']>;
  businessRegNo: FormControl<EmployementDetailsFormRawValue['businessRegNo']>;
  compOwnerShip: FormControl<EmployementDetailsFormRawValue['compOwnerShip']>;
  partnerName1: FormControl<EmployementDetailsFormRawValue['partnerName1']>;
  partnerName2: FormControl<EmployementDetailsFormRawValue['partnerName2']>;
  partnerName3: FormControl<EmployementDetailsFormRawValue['partnerName3']>;
  isDeleted: FormControl<EmployementDetailsFormRawValue['isDeleted']>;
  lastModified: FormControl<EmployementDetailsFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<EmployementDetailsFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<EmployementDetailsFormRawValue['createdBy']>;
  createdOn: FormControl<EmployementDetailsFormRawValue['createdOn']>;
  freeField1: FormControl<EmployementDetailsFormRawValue['freeField1']>;
  freeField2: FormControl<EmployementDetailsFormRawValue['freeField2']>;
  freeField3: FormControl<EmployementDetailsFormRawValue['freeField3']>;
  freeField4: FormControl<EmployementDetailsFormRawValue['freeField4']>;
  freeField5: FormControl<EmployementDetailsFormRawValue['freeField5']>;
  freeField6: FormControl<EmployementDetailsFormRawValue['freeField6']>;
  member: FormControl<EmployementDetailsFormRawValue['member']>;
};

export type EmployementDetailsFormGroup = FormGroup<EmployementDetailsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EmployementDetailsFormService {
  createEmployementDetailsFormGroup(employementDetails: EmployementDetailsFormGroupInput = { id: null }): EmployementDetailsFormGroup {
    const employementDetailsRawValue = this.convertEmployementDetailsToEmployementDetailsRawValue({
      ...this.getFormDefaults(),
      ...employementDetails,
    });
    return new FormGroup<EmployementDetailsFormGroupContent>({
      id: new FormControl(
        { value: employementDetailsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      type: new FormControl(employementDetailsRawValue.type),
      employerName: new FormControl(employementDetailsRawValue.employerName),
      status: new FormControl(employementDetailsRawValue.status),
      designation: new FormControl(employementDetailsRawValue.designation),
      duration: new FormControl(employementDetailsRawValue.duration),
      employerAdd: new FormControl(employementDetailsRawValue.employerAdd),
      prevCompanyName: new FormControl(employementDetailsRawValue.prevCompanyName),
      prevCompanyduration: new FormControl(employementDetailsRawValue.prevCompanyduration),
      orgType: new FormControl(employementDetailsRawValue.orgType),
      constitutionType: new FormControl(employementDetailsRawValue.constitutionType),
      industryType: new FormControl(employementDetailsRawValue.industryType),
      businessRegNo: new FormControl(employementDetailsRawValue.businessRegNo),
      compOwnerShip: new FormControl(employementDetailsRawValue.compOwnerShip),
      partnerName1: new FormControl(employementDetailsRawValue.partnerName1),
      partnerName2: new FormControl(employementDetailsRawValue.partnerName2),
      partnerName3: new FormControl(employementDetailsRawValue.partnerName3),
      isDeleted: new FormControl(employementDetailsRawValue.isDeleted),
      lastModified: new FormControl(employementDetailsRawValue.lastModified),
      lastModifiedBy: new FormControl(employementDetailsRawValue.lastModifiedBy),
      createdBy: new FormControl(employementDetailsRawValue.createdBy),
      createdOn: new FormControl(employementDetailsRawValue.createdOn),
      freeField1: new FormControl(employementDetailsRawValue.freeField1),
      freeField2: new FormControl(employementDetailsRawValue.freeField2),
      freeField3: new FormControl(employementDetailsRawValue.freeField3),
      freeField4: new FormControl(employementDetailsRawValue.freeField4),
      freeField5: new FormControl(employementDetailsRawValue.freeField5),
      freeField6: new FormControl(employementDetailsRawValue.freeField6),
      member: new FormControl(employementDetailsRawValue.member),
    });
  }

  getEmployementDetails(form: EmployementDetailsFormGroup): IEmployementDetails | NewEmployementDetails {
    return this.convertEmployementDetailsRawValueToEmployementDetails(
      form.getRawValue() as EmployementDetailsFormRawValue | NewEmployementDetailsFormRawValue
    );
  }

  resetForm(form: EmployementDetailsFormGroup, employementDetails: EmployementDetailsFormGroupInput): void {
    const employementDetailsRawValue = this.convertEmployementDetailsToEmployementDetailsRawValue({
      ...this.getFormDefaults(),
      ...employementDetails,
    });
    form.reset(
      {
        ...employementDetailsRawValue,
        id: { value: employementDetailsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): EmployementDetailsFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      isDeleted: false,
      lastModified: currentTime,
      createdOn: currentTime,
    };
  }

  private convertEmployementDetailsRawValueToEmployementDetails(
    rawEmployementDetails: EmployementDetailsFormRawValue | NewEmployementDetailsFormRawValue
  ): IEmployementDetails | NewEmployementDetails {
    return {
      ...rawEmployementDetails,
      lastModified: dayjs(rawEmployementDetails.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawEmployementDetails.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertEmployementDetailsToEmployementDetailsRawValue(
    employementDetails: IEmployementDetails | (Partial<NewEmployementDetails> & EmployementDetailsFormDefaults)
  ): EmployementDetailsFormRawValue | PartialWithRequiredKeyOf<NewEmployementDetailsFormRawValue> {
    return {
      ...employementDetails,
      lastModified: employementDetails.lastModified ? employementDetails.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: employementDetails.createdOn ? employementDetails.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
