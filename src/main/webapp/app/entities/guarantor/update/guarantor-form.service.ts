import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IGuarantor, NewGuarantor } from '../guarantor.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IGuarantor for edit and NewGuarantorFormGroupInput for create.
 */
type GuarantorFormGroupInput = IGuarantor | PartialWithRequiredKeyOf<NewGuarantor>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IGuarantor | NewGuarantor> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

type GuarantorFormRawValue = FormValueOf<IGuarantor>;

type NewGuarantorFormRawValue = FormValueOf<NewGuarantor>;

type GuarantorFormDefaults = Pick<
  NewGuarantor,
  'id' | 'hasAdharVerified' | 'hasPanVerified' | 'isIncomeTaxPayer' | 'isActive' | 'isDeleted' | 'lastModified' | 'createdOn'
>;

type GuarantorFormGroupContent = {
  id: FormControl<GuarantorFormRawValue['id'] | NewGuarantor['id']>;
  title: FormControl<GuarantorFormRawValue['title']>;
  firstName: FormControl<GuarantorFormRawValue['firstName']>;
  middleName: FormControl<GuarantorFormRawValue['middleName']>;
  lastName: FormControl<GuarantorFormRawValue['lastName']>;
  membershipNo: FormControl<GuarantorFormRawValue['membershipNo']>;
  gender: FormControl<GuarantorFormRawValue['gender']>;
  dob: FormControl<GuarantorFormRawValue['dob']>;
  email: FormControl<GuarantorFormRawValue['email']>;
  mobileNo: FormControl<GuarantorFormRawValue['mobileNo']>;
  houseOwner: FormControl<GuarantorFormRawValue['houseOwner']>;
  occupation: FormControl<GuarantorFormRawValue['occupation']>;
  employerNameAdd: FormControl<GuarantorFormRawValue['employerNameAdd']>;
  soclibilAmt: FormControl<GuarantorFormRawValue['soclibilAmt']>;
  soclibilType: FormControl<GuarantorFormRawValue['soclibilType']>;
  otherlibilAmt: FormControl<GuarantorFormRawValue['otherlibilAmt']>;
  otherlibilType: FormControl<GuarantorFormRawValue['otherlibilType']>;
  aadharCardNo: FormControl<GuarantorFormRawValue['aadharCardNo']>;
  panCard: FormControl<GuarantorFormRawValue['panCard']>;
  maritalStatus: FormControl<GuarantorFormRawValue['maritalStatus']>;
  hasAdharVerified: FormControl<GuarantorFormRawValue['hasAdharVerified']>;
  hasPanVerified: FormControl<GuarantorFormRawValue['hasPanVerified']>;
  numberOfAssets: FormControl<GuarantorFormRawValue['numberOfAssets']>;
  grossAnnualInc: FormControl<GuarantorFormRawValue['grossAnnualInc']>;
  netIncome: FormControl<GuarantorFormRawValue['netIncome']>;
  isIncomeTaxPayer: FormControl<GuarantorFormRawValue['isIncomeTaxPayer']>;
  isActive: FormControl<GuarantorFormRawValue['isActive']>;
  isDeleted: FormControl<GuarantorFormRawValue['isDeleted']>;
  lastModified: FormControl<GuarantorFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<GuarantorFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<GuarantorFormRawValue['createdBy']>;
  createdOn: FormControl<GuarantorFormRawValue['createdOn']>;
  freeField1: FormControl<GuarantorFormRawValue['freeField1']>;
  freeField2: FormControl<GuarantorFormRawValue['freeField2']>;
  freeField3: FormControl<GuarantorFormRawValue['freeField3']>;
  freeField4: FormControl<GuarantorFormRawValue['freeField4']>;
  freeField5: FormControl<GuarantorFormRawValue['freeField5']>;
  freeField6: FormControl<GuarantorFormRawValue['freeField6']>;
  memberAssets: FormControl<GuarantorFormRawValue['memberAssets']>;
  employementDetails: FormControl<GuarantorFormRawValue['employementDetails']>;
  member: FormControl<GuarantorFormRawValue['member']>;
};

export type GuarantorFormGroup = FormGroup<GuarantorFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class GuarantorFormService {
  createGuarantorFormGroup(guarantor: GuarantorFormGroupInput = { id: null }): GuarantorFormGroup {
    const guarantorRawValue = this.convertGuarantorToGuarantorRawValue({
      ...this.getFormDefaults(),
      ...guarantor,
    });
    return new FormGroup<GuarantorFormGroupContent>({
      id: new FormControl(
        { value: guarantorRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      title: new FormControl(guarantorRawValue.title),
      firstName: new FormControl(guarantorRawValue.firstName),
      middleName: new FormControl(guarantorRawValue.middleName),
      lastName: new FormControl(guarantorRawValue.lastName),
      membershipNo: new FormControl(guarantorRawValue.membershipNo),
      gender: new FormControl(guarantorRawValue.gender),
      dob: new FormControl(guarantorRawValue.dob),
      email: new FormControl(guarantorRawValue.email),
      mobileNo: new FormControl(guarantorRawValue.mobileNo),
      houseOwner: new FormControl(guarantorRawValue.houseOwner),
      occupation: new FormControl(guarantorRawValue.occupation),
      employerNameAdd: new FormControl(guarantorRawValue.employerNameAdd),
      soclibilAmt: new FormControl(guarantorRawValue.soclibilAmt),
      soclibilType: new FormControl(guarantorRawValue.soclibilType),
      otherlibilAmt: new FormControl(guarantorRawValue.otherlibilAmt),
      otherlibilType: new FormControl(guarantorRawValue.otherlibilType),
      aadharCardNo: new FormControl(guarantorRawValue.aadharCardNo),
      panCard: new FormControl(guarantorRawValue.panCard),
      maritalStatus: new FormControl(guarantorRawValue.maritalStatus),
      hasAdharVerified: new FormControl(guarantorRawValue.hasAdharVerified),
      hasPanVerified: new FormControl(guarantorRawValue.hasPanVerified),
      numberOfAssets: new FormControl(guarantorRawValue.numberOfAssets),
      grossAnnualInc: new FormControl(guarantorRawValue.grossAnnualInc),
      netIncome: new FormControl(guarantorRawValue.netIncome),
      isIncomeTaxPayer: new FormControl(guarantorRawValue.isIncomeTaxPayer),
      isActive: new FormControl(guarantorRawValue.isActive),
      isDeleted: new FormControl(guarantorRawValue.isDeleted),
      lastModified: new FormControl(guarantorRawValue.lastModified),
      lastModifiedBy: new FormControl(guarantorRawValue.lastModifiedBy),
      createdBy: new FormControl(guarantorRawValue.createdBy),
      createdOn: new FormControl(guarantorRawValue.createdOn),
      freeField1: new FormControl(guarantorRawValue.freeField1),
      freeField2: new FormControl(guarantorRawValue.freeField2),
      freeField3: new FormControl(guarantorRawValue.freeField3),
      freeField4: new FormControl(guarantorRawValue.freeField4),
      freeField5: new FormControl(guarantorRawValue.freeField5),
      freeField6: new FormControl(guarantorRawValue.freeField6),
      memberAssets: new FormControl(guarantorRawValue.memberAssets),
      employementDetails: new FormControl(guarantorRawValue.employementDetails),
      member: new FormControl(guarantorRawValue.member),
    });
  }

  getGuarantor(form: GuarantorFormGroup): IGuarantor | NewGuarantor {
    return this.convertGuarantorRawValueToGuarantor(form.getRawValue() as GuarantorFormRawValue | NewGuarantorFormRawValue);
  }

  resetForm(form: GuarantorFormGroup, guarantor: GuarantorFormGroupInput): void {
    const guarantorRawValue = this.convertGuarantorToGuarantorRawValue({ ...this.getFormDefaults(), ...guarantor });
    form.reset(
      {
        ...guarantorRawValue,
        id: { value: guarantorRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): GuarantorFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      hasAdharVerified: false,
      hasPanVerified: false,
      isIncomeTaxPayer: false,
      isActive: false,
      isDeleted: false,
      lastModified: currentTime,
      createdOn: currentTime,
    };
  }

  private convertGuarantorRawValueToGuarantor(rawGuarantor: GuarantorFormRawValue | NewGuarantorFormRawValue): IGuarantor | NewGuarantor {
    return {
      ...rawGuarantor,
      lastModified: dayjs(rawGuarantor.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawGuarantor.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertGuarantorToGuarantorRawValue(
    guarantor: IGuarantor | (Partial<NewGuarantor> & GuarantorFormDefaults)
  ): GuarantorFormRawValue | PartialWithRequiredKeyOf<NewGuarantorFormRawValue> {
    return {
      ...guarantor,
      lastModified: guarantor.lastModified ? guarantor.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: guarantor.createdOn ? guarantor.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
