import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IMember, NewMember } from '../member.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMember for edit and NewMemberFormGroupInput for create.
 */
type MemberFormGroupInput = IMember | PartialWithRequiredKeyOf<NewMember>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IMember | NewMember> = Omit<T, 'applicationDate' | 'lastModified' | 'createdOn'> & {
  applicationDate?: string | null;
  lastModified?: string | null;
  createdOn?: string | null;
};

type MemberFormRawValue = FormValueOf<IMember>;

type NewMemberFormRawValue = FormValueOf<NewMember>;

type MemberFormDefaults = Pick<
  NewMember,
  'id' | 'applicationDate' | 'hasAdharCardVerified' | 'hasPanCardVerified' | 'isActive' | 'isDeleted' | 'lastModified' | 'createdOn'
>;

type MemberFormGroupContent = {
  id: FormControl<MemberFormRawValue['id'] | NewMember['id']>;
  title: FormControl<MemberFormRawValue['title']>;
  firstName: FormControl<MemberFormRawValue['firstName']>;
  middleName: FormControl<MemberFormRawValue['middleName']>;
  lastName: FormControl<MemberFormRawValue['lastName']>;
  memberId: FormControl<MemberFormRawValue['memberId']>;
  membershipNo: FormControl<MemberFormRawValue['membershipNo']>;
  fatherName: FormControl<MemberFormRawValue['fatherName']>;
  motherName: FormControl<MemberFormRawValue['motherName']>;
  gender: FormControl<MemberFormRawValue['gender']>;
  dob: FormControl<MemberFormRawValue['dob']>;
  email: FormControl<MemberFormRawValue['email']>;
  mobileNo: FormControl<MemberFormRawValue['mobileNo']>;
  alternateMobile: FormControl<MemberFormRawValue['alternateMobile']>;
  fax: FormControl<MemberFormRawValue['fax']>;
  contactTimeFrom: FormControl<MemberFormRawValue['contactTimeFrom']>;
  contactTimeTo: FormControl<MemberFormRawValue['contactTimeTo']>;
  religion: FormControl<MemberFormRawValue['religion']>;
  custCategory: FormControl<MemberFormRawValue['custCategory']>;
  cast: FormControl<MemberFormRawValue['cast']>;
  aadharCardNo: FormControl<MemberFormRawValue['aadharCardNo']>;
  panCard: FormControl<MemberFormRawValue['panCard']>;
  passportNo: FormControl<MemberFormRawValue['passportNo']>;
  passportExpiry: FormControl<MemberFormRawValue['passportExpiry']>;
  rationCard: FormControl<MemberFormRawValue['rationCard']>;
  residenceStatus: FormControl<MemberFormRawValue['residenceStatus']>;
  maritalStatus: FormControl<MemberFormRawValue['maritalStatus']>;
  familyMemberCount: FormControl<MemberFormRawValue['familyMemberCount']>;
  occupation: FormControl<MemberFormRawValue['occupation']>;
  nationality: FormControl<MemberFormRawValue['nationality']>;
  noOfDependents: FormControl<MemberFormRawValue['noOfDependents']>;
  applicationDate: FormControl<MemberFormRawValue['applicationDate']>;
  status: FormControl<MemberFormRawValue['status']>;
  highestQualification: FormControl<MemberFormRawValue['highestQualification']>;
  hasAdharCardVerified: FormControl<MemberFormRawValue['hasAdharCardVerified']>;
  hasPanCardVerified: FormControl<MemberFormRawValue['hasPanCardVerified']>;
  loanStatus: FormControl<MemberFormRawValue['loanStatus']>;
  enqRefrenceNo: FormControl<MemberFormRawValue['enqRefrenceNo']>;
  numberOfAssets: FormControl<MemberFormRawValue['numberOfAssets']>;
  isActive: FormControl<MemberFormRawValue['isActive']>;
  isDeleted: FormControl<MemberFormRawValue['isDeleted']>;
  profileStepper: FormControl<MemberFormRawValue['profileStepper']>;
  lastModified: FormControl<MemberFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<MemberFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<MemberFormRawValue['createdBy']>;
  createdOn: FormControl<MemberFormRawValue['createdOn']>;
  freeField1: FormControl<MemberFormRawValue['freeField1']>;
  freeField2: FormControl<MemberFormRawValue['freeField2']>;
  freeField3: FormControl<MemberFormRawValue['freeField3']>;
  freeField4: FormControl<MemberFormRawValue['freeField4']>;
  freeField5: FormControl<MemberFormRawValue['freeField5']>;
  freeField6: FormControl<MemberFormRawValue['freeField6']>;
  enquiryDetails: FormControl<MemberFormRawValue['enquiryDetails']>;
  branch: FormControl<MemberFormRawValue['branch']>;
  member: FormControl<MemberFormRawValue['member']>;
  securityUser: FormControl<MemberFormRawValue['securityUser']>;
};

export type MemberFormGroup = FormGroup<MemberFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MemberFormService {
  createMemberFormGroup(member: MemberFormGroupInput = { id: null }): MemberFormGroup {
    const memberRawValue = this.convertMemberToMemberRawValue({
      ...this.getFormDefaults(),
      ...member,
    });
    return new FormGroup<MemberFormGroupContent>({
      id: new FormControl(
        { value: memberRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      title: new FormControl(memberRawValue.title),
      firstName: new FormControl(memberRawValue.firstName),
      middleName: new FormControl(memberRawValue.middleName),
      lastName: new FormControl(memberRawValue.lastName),
      memberId: new FormControl(memberRawValue.memberId),
      membershipNo: new FormControl(memberRawValue.membershipNo),
      fatherName: new FormControl(memberRawValue.fatherName),
      motherName: new FormControl(memberRawValue.motherName),
      gender: new FormControl(memberRawValue.gender),
      dob: new FormControl(memberRawValue.dob),
      email: new FormControl(memberRawValue.email),
      mobileNo: new FormControl(memberRawValue.mobileNo),
      alternateMobile: new FormControl(memberRawValue.alternateMobile),
      fax: new FormControl(memberRawValue.fax),
      contactTimeFrom: new FormControl(memberRawValue.contactTimeFrom),
      contactTimeTo: new FormControl(memberRawValue.contactTimeTo),
      religion: new FormControl(memberRawValue.religion),
      custCategory: new FormControl(memberRawValue.custCategory),
      cast: new FormControl(memberRawValue.cast),
      aadharCardNo: new FormControl(memberRawValue.aadharCardNo),
      panCard: new FormControl(memberRawValue.panCard),
      passportNo: new FormControl(memberRawValue.passportNo),
      passportExpiry: new FormControl(memberRawValue.passportExpiry),
      rationCard: new FormControl(memberRawValue.rationCard),
      residenceStatus: new FormControl(memberRawValue.residenceStatus),
      maritalStatus: new FormControl(memberRawValue.maritalStatus),
      familyMemberCount: new FormControl(memberRawValue.familyMemberCount),
      occupation: new FormControl(memberRawValue.occupation),
      nationality: new FormControl(memberRawValue.nationality),
      noOfDependents: new FormControl(memberRawValue.noOfDependents),
      applicationDate: new FormControl(memberRawValue.applicationDate),
      status: new FormControl(memberRawValue.status),
      highestQualification: new FormControl(memberRawValue.highestQualification),
      hasAdharCardVerified: new FormControl(memberRawValue.hasAdharCardVerified),
      hasPanCardVerified: new FormControl(memberRawValue.hasPanCardVerified),
      loanStatus: new FormControl(memberRawValue.loanStatus),
      enqRefrenceNo: new FormControl(memberRawValue.enqRefrenceNo),
      numberOfAssets: new FormControl(memberRawValue.numberOfAssets),
      isActive: new FormControl(memberRawValue.isActive),
      isDeleted: new FormControl(memberRawValue.isDeleted),
      profileStepper: new FormControl(memberRawValue.profileStepper),
      lastModified: new FormControl(memberRawValue.lastModified),
      lastModifiedBy: new FormControl(memberRawValue.lastModifiedBy),
      createdBy: new FormControl(memberRawValue.createdBy),
      createdOn: new FormControl(memberRawValue.createdOn),
      freeField1: new FormControl(memberRawValue.freeField1),
      freeField2: new FormControl(memberRawValue.freeField2),
      freeField3: new FormControl(memberRawValue.freeField3),
      freeField4: new FormControl(memberRawValue.freeField4),
      freeField5: new FormControl(memberRawValue.freeField5),
      freeField6: new FormControl(memberRawValue.freeField6),
      enquiryDetails: new FormControl(memberRawValue.enquiryDetails),
      branch: new FormControl(memberRawValue.branch),
      member: new FormControl(memberRawValue.member),
      securityUser: new FormControl(memberRawValue.securityUser),
    });
  }

  getMember(form: MemberFormGroup): IMember | NewMember {
    return this.convertMemberRawValueToMember(form.getRawValue() as MemberFormRawValue | NewMemberFormRawValue);
  }

  resetForm(form: MemberFormGroup, member: MemberFormGroupInput): void {
    const memberRawValue = this.convertMemberToMemberRawValue({ ...this.getFormDefaults(), ...member });
    form.reset(
      {
        ...memberRawValue,
        id: { value: memberRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): MemberFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      applicationDate: currentTime,
      hasAdharCardVerified: false,
      hasPanCardVerified: false,
      isActive: false,
      isDeleted: false,
      lastModified: currentTime,
      createdOn: currentTime,
    };
  }

  private convertMemberRawValueToMember(rawMember: MemberFormRawValue | NewMemberFormRawValue): IMember | NewMember {
    return {
      ...rawMember,
      applicationDate: dayjs(rawMember.applicationDate, DATE_TIME_FORMAT),
      lastModified: dayjs(rawMember.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawMember.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertMemberToMemberRawValue(
    member: IMember | (Partial<NewMember> & MemberFormDefaults)
  ): MemberFormRawValue | PartialWithRequiredKeyOf<NewMemberFormRawValue> {
    return {
      ...member,
      applicationDate: member.applicationDate ? member.applicationDate.format(DATE_TIME_FORMAT) : undefined,
      lastModified: member.lastModified ? member.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: member.createdOn ? member.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
