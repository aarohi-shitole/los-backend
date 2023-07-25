import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IMemberLimit, NewMemberLimit } from '../member-limit.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMemberLimit for edit and NewMemberLimitFormGroupInput for create.
 */
type MemberLimitFormGroupInput = IMemberLimit | PartialWithRequiredKeyOf<NewMemberLimit>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IMemberLimit | NewMemberLimit> = Omit<T, 'lastModified'> & {
  lastModified?: string | null;
};

type MemberLimitFormRawValue = FormValueOf<IMemberLimit>;

type NewMemberLimitFormRawValue = FormValueOf<NewMemberLimit>;

type MemberLimitFormDefaults = Pick<NewMemberLimit, 'id' | 'isDeleted' | 'isActive' | 'lastModified'>;

type MemberLimitFormGroupContent = {
  id: FormControl<MemberLimitFormRawValue['id'] | NewMemberLimit['id']>;
  limitSanctionAmount: FormControl<MemberLimitFormRawValue['limitSanctionAmount']>;
  dtLimitSanctioned: FormControl<MemberLimitFormRawValue['dtLimitSanctioned']>;
  dtLimitExpired: FormControl<MemberLimitFormRawValue['dtLimitExpired']>;
  purpose: FormControl<MemberLimitFormRawValue['purpose']>;
  isDeleted: FormControl<MemberLimitFormRawValue['isDeleted']>;
  isActive: FormControl<MemberLimitFormRawValue['isActive']>;
  lastModified: FormControl<MemberLimitFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<MemberLimitFormRawValue['lastModifiedBy']>;
  freeField1: FormControl<MemberLimitFormRawValue['freeField1']>;
  freeField2: FormControl<MemberLimitFormRawValue['freeField2']>;
  freeField3: FormControl<MemberLimitFormRawValue['freeField3']>;
  freeField4: FormControl<MemberLimitFormRawValue['freeField4']>;
};

export type MemberLimitFormGroup = FormGroup<MemberLimitFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MemberLimitFormService {
  createMemberLimitFormGroup(memberLimit: MemberLimitFormGroupInput = { id: null }): MemberLimitFormGroup {
    const memberLimitRawValue = this.convertMemberLimitToMemberLimitRawValue({
      ...this.getFormDefaults(),
      ...memberLimit,
    });
    return new FormGroup<MemberLimitFormGroupContent>({
      id: new FormControl(
        { value: memberLimitRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      limitSanctionAmount: new FormControl(memberLimitRawValue.limitSanctionAmount),
      dtLimitSanctioned: new FormControl(memberLimitRawValue.dtLimitSanctioned),
      dtLimitExpired: new FormControl(memberLimitRawValue.dtLimitExpired),
      purpose: new FormControl(memberLimitRawValue.purpose),
      isDeleted: new FormControl(memberLimitRawValue.isDeleted),
      isActive: new FormControl(memberLimitRawValue.isActive),
      lastModified: new FormControl(memberLimitRawValue.lastModified),
      lastModifiedBy: new FormControl(memberLimitRawValue.lastModifiedBy),
      freeField1: new FormControl(memberLimitRawValue.freeField1),
      freeField2: new FormControl(memberLimitRawValue.freeField2),
      freeField3: new FormControl(memberLimitRawValue.freeField3),
      freeField4: new FormControl(memberLimitRawValue.freeField4),
    });
  }

  getMemberLimit(form: MemberLimitFormGroup): IMemberLimit | NewMemberLimit {
    return this.convertMemberLimitRawValueToMemberLimit(form.getRawValue() as MemberLimitFormRawValue | NewMemberLimitFormRawValue);
  }

  resetForm(form: MemberLimitFormGroup, memberLimit: MemberLimitFormGroupInput): void {
    const memberLimitRawValue = this.convertMemberLimitToMemberLimitRawValue({ ...this.getFormDefaults(), ...memberLimit });
    form.reset(
      {
        ...memberLimitRawValue,
        id: { value: memberLimitRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): MemberLimitFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      isDeleted: false,
      isActive: false,
      lastModified: currentTime,
    };
  }

  private convertMemberLimitRawValueToMemberLimit(
    rawMemberLimit: MemberLimitFormRawValue | NewMemberLimitFormRawValue
  ): IMemberLimit | NewMemberLimit {
    return {
      ...rawMemberLimit,
      lastModified: dayjs(rawMemberLimit.lastModified, DATE_TIME_FORMAT),
    };
  }

  private convertMemberLimitToMemberLimitRawValue(
    memberLimit: IMemberLimit | (Partial<NewMemberLimit> & MemberLimitFormDefaults)
  ): MemberLimitFormRawValue | PartialWithRequiredKeyOf<NewMemberLimitFormRawValue> {
    return {
      ...memberLimit,
      lastModified: memberLimit.lastModified ? memberLimit.lastModified.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
