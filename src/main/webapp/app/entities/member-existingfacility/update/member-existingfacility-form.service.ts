import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IMemberExistingfacility, NewMemberExistingfacility } from '../member-existingfacility.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMemberExistingfacility for edit and NewMemberExistingfacilityFormGroupInput for create.
 */
type MemberExistingfacilityFormGroupInput = IMemberExistingfacility | PartialWithRequiredKeyOf<NewMemberExistingfacility>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IMemberExistingfacility | NewMemberExistingfacility> = Omit<T, 'sectionDate' | 'lastModified' | 'createdOn'> & {
  sectionDate?: string | null;
  lastModified?: string | null;
  createdOn?: string | null;
};

type MemberExistingfacilityFormRawValue = FormValueOf<IMemberExistingfacility>;

type NewMemberExistingfacilityFormRawValue = FormValueOf<NewMemberExistingfacility>;

type MemberExistingfacilityFormDefaults = Pick<
  NewMemberExistingfacility,
  'id' | 'sectionDate' | 'isDeleted' | 'lastModified' | 'createdOn'
>;

type MemberExistingfacilityFormGroupContent = {
  id: FormControl<MemberExistingfacilityFormRawValue['id'] | NewMemberExistingfacility['id']>;
  year: FormControl<MemberExistingfacilityFormRawValue['year']>;
  facilityName: FormControl<MemberExistingfacilityFormRawValue['facilityName']>;
  facilitatedFrom: FormControl<MemberExistingfacilityFormRawValue['facilitatedFrom']>;
  nature: FormControl<MemberExistingfacilityFormRawValue['nature']>;
  amtInLac: FormControl<MemberExistingfacilityFormRawValue['amtInLac']>;
  purpose: FormControl<MemberExistingfacilityFormRawValue['purpose']>;
  sectionDate: FormControl<MemberExistingfacilityFormRawValue['sectionDate']>;
  outstandingInLac: FormControl<MemberExistingfacilityFormRawValue['outstandingInLac']>;
  status: FormControl<MemberExistingfacilityFormRawValue['status']>;
  rating: FormControl<MemberExistingfacilityFormRawValue['rating']>;
  isDeleted: FormControl<MemberExistingfacilityFormRawValue['isDeleted']>;
  lastModified: FormControl<MemberExistingfacilityFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<MemberExistingfacilityFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<MemberExistingfacilityFormRawValue['createdBy']>;
  createdOn: FormControl<MemberExistingfacilityFormRawValue['createdOn']>;
  freeField1: FormControl<MemberExistingfacilityFormRawValue['freeField1']>;
  freeField2: FormControl<MemberExistingfacilityFormRawValue['freeField2']>;
  freeField3: FormControl<MemberExistingfacilityFormRawValue['freeField3']>;
  freeField4: FormControl<MemberExistingfacilityFormRawValue['freeField4']>;
  freeField5: FormControl<MemberExistingfacilityFormRawValue['freeField5']>;
  freeField6: FormControl<MemberExistingfacilityFormRawValue['freeField6']>;
  member: FormControl<MemberExistingfacilityFormRawValue['member']>;
};

export type MemberExistingfacilityFormGroup = FormGroup<MemberExistingfacilityFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MemberExistingfacilityFormService {
  createMemberExistingfacilityFormGroup(
    memberExistingfacility: MemberExistingfacilityFormGroupInput = { id: null }
  ): MemberExistingfacilityFormGroup {
    const memberExistingfacilityRawValue = this.convertMemberExistingfacilityToMemberExistingfacilityRawValue({
      ...this.getFormDefaults(),
      ...memberExistingfacility,
    });
    return new FormGroup<MemberExistingfacilityFormGroupContent>({
      id: new FormControl(
        { value: memberExistingfacilityRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      year: new FormControl(memberExistingfacilityRawValue.year),
      facilityName: new FormControl(memberExistingfacilityRawValue.facilityName),
      facilitatedFrom: new FormControl(memberExistingfacilityRawValue.facilitatedFrom),
      nature: new FormControl(memberExistingfacilityRawValue.nature),
      amtInLac: new FormControl(memberExistingfacilityRawValue.amtInLac),
      purpose: new FormControl(memberExistingfacilityRawValue.purpose),
      sectionDate: new FormControl(memberExistingfacilityRawValue.sectionDate),
      outstandingInLac: new FormControl(memberExistingfacilityRawValue.outstandingInLac),
      status: new FormControl(memberExistingfacilityRawValue.status),
      rating: new FormControl(memberExistingfacilityRawValue.rating),
      isDeleted: new FormControl(memberExistingfacilityRawValue.isDeleted),
      lastModified: new FormControl(memberExistingfacilityRawValue.lastModified),
      lastModifiedBy: new FormControl(memberExistingfacilityRawValue.lastModifiedBy),
      createdBy: new FormControl(memberExistingfacilityRawValue.createdBy),
      createdOn: new FormControl(memberExistingfacilityRawValue.createdOn),
      freeField1: new FormControl(memberExistingfacilityRawValue.freeField1),
      freeField2: new FormControl(memberExistingfacilityRawValue.freeField2),
      freeField3: new FormControl(memberExistingfacilityRawValue.freeField3),
      freeField4: new FormControl(memberExistingfacilityRawValue.freeField4),
      freeField5: new FormControl(memberExistingfacilityRawValue.freeField5),
      freeField6: new FormControl(memberExistingfacilityRawValue.freeField6),
      member: new FormControl(memberExistingfacilityRawValue.member),
    });
  }

  getMemberExistingfacility(form: MemberExistingfacilityFormGroup): IMemberExistingfacility | NewMemberExistingfacility {
    return this.convertMemberExistingfacilityRawValueToMemberExistingfacility(
      form.getRawValue() as MemberExistingfacilityFormRawValue | NewMemberExistingfacilityFormRawValue
    );
  }

  resetForm(form: MemberExistingfacilityFormGroup, memberExistingfacility: MemberExistingfacilityFormGroupInput): void {
    const memberExistingfacilityRawValue = this.convertMemberExistingfacilityToMemberExistingfacilityRawValue({
      ...this.getFormDefaults(),
      ...memberExistingfacility,
    });
    form.reset(
      {
        ...memberExistingfacilityRawValue,
        id: { value: memberExistingfacilityRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): MemberExistingfacilityFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      sectionDate: currentTime,
      isDeleted: false,
      lastModified: currentTime,
      createdOn: currentTime,
    };
  }

  private convertMemberExistingfacilityRawValueToMemberExistingfacility(
    rawMemberExistingfacility: MemberExistingfacilityFormRawValue | NewMemberExistingfacilityFormRawValue
  ): IMemberExistingfacility | NewMemberExistingfacility {
    return {
      ...rawMemberExistingfacility,
      sectionDate: dayjs(rawMemberExistingfacility.sectionDate, DATE_TIME_FORMAT),
      lastModified: dayjs(rawMemberExistingfacility.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawMemberExistingfacility.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertMemberExistingfacilityToMemberExistingfacilityRawValue(
    memberExistingfacility: IMemberExistingfacility | (Partial<NewMemberExistingfacility> & MemberExistingfacilityFormDefaults)
  ): MemberExistingfacilityFormRawValue | PartialWithRequiredKeyOf<NewMemberExistingfacilityFormRawValue> {
    return {
      ...memberExistingfacility,
      sectionDate: memberExistingfacility.sectionDate ? memberExistingfacility.sectionDate.format(DATE_TIME_FORMAT) : undefined,
      lastModified: memberExistingfacility.lastModified ? memberExistingfacility.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: memberExistingfacility.createdOn ? memberExistingfacility.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
