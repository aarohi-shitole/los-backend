import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IMemberAssets, NewMemberAssets } from '../member-assets.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMemberAssets for edit and NewMemberAssetsFormGroupInput for create.
 */
type MemberAssetsFormGroupInput = IMemberAssets | PartialWithRequiredKeyOf<NewMemberAssets>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IMemberAssets | NewMemberAssets> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

type MemberAssetsFormRawValue = FormValueOf<IMemberAssets>;

type NewMemberAssetsFormRawValue = FormValueOf<NewMemberAssets>;

type MemberAssetsFormDefaults = Pick<
  NewMemberAssets,
  'id' | 'isInsured' | 'isUnderUsed' | 'isOwned' | 'lastModified' | 'createdOn' | 'isDeleted'
>;

type MemberAssetsFormGroupContent = {
  id: FormControl<MemberAssetsFormRawValue['id'] | NewMemberAssets['id']>;
  assetCost: FormControl<MemberAssetsFormRawValue['assetCost']>;
  assetType: FormControl<MemberAssetsFormRawValue['assetType']>;
  areaInSqFt: FormControl<MemberAssetsFormRawValue['areaInSqFt']>;
  assetNature: FormControl<MemberAssetsFormRawValue['assetNature']>;
  address: FormControl<MemberAssetsFormRawValue['address']>;
  landMark: FormControl<MemberAssetsFormRawValue['landMark']>;
  assetOwner: FormControl<MemberAssetsFormRawValue['assetOwner']>;
  completionYear: FormControl<MemberAssetsFormRawValue['completionYear']>;
  marketValue: FormControl<MemberAssetsFormRawValue['marketValue']>;
  isInsured: FormControl<MemberAssetsFormRawValue['isInsured']>;
  isUnderUsed: FormControl<MemberAssetsFormRawValue['isUnderUsed']>;
  isOwned: FormControl<MemberAssetsFormRawValue['isOwned']>;
  landType: FormControl<MemberAssetsFormRawValue['landType']>;
  landGatNo: FormControl<MemberAssetsFormRawValue['landGatNo']>;
  landAreaInHector: FormControl<MemberAssetsFormRawValue['landAreaInHector']>;
  jindagiPatrakNo: FormControl<MemberAssetsFormRawValue['jindagiPatrakNo']>;
  jindagiAmount: FormControl<MemberAssetsFormRawValue['jindagiAmount']>;
  lastModified: FormControl<MemberAssetsFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<MemberAssetsFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<MemberAssetsFormRawValue['createdBy']>;
  createdOn: FormControl<MemberAssetsFormRawValue['createdOn']>;
  isDeleted: FormControl<MemberAssetsFormRawValue['isDeleted']>;
  freeField1: FormControl<MemberAssetsFormRawValue['freeField1']>;
  freeField2: FormControl<MemberAssetsFormRawValue['freeField2']>;
  freeField3: FormControl<MemberAssetsFormRawValue['freeField3']>;
  freeField4: FormControl<MemberAssetsFormRawValue['freeField4']>;
  freeField5: FormControl<MemberAssetsFormRawValue['freeField5']>;
  freeField6: FormControl<MemberAssetsFormRawValue['freeField6']>;
  member: FormControl<MemberAssetsFormRawValue['member']>;
  loanApplications: FormControl<MemberAssetsFormRawValue['loanApplications']>;
};

export type MemberAssetsFormGroup = FormGroup<MemberAssetsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MemberAssetsFormService {
  createMemberAssetsFormGroup(memberAssets: MemberAssetsFormGroupInput = { id: null }): MemberAssetsFormGroup {
    const memberAssetsRawValue = this.convertMemberAssetsToMemberAssetsRawValue({
      ...this.getFormDefaults(),
      ...memberAssets,
    });
    return new FormGroup<MemberAssetsFormGroupContent>({
      id: new FormControl(
        { value: memberAssetsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      assetCost: new FormControl(memberAssetsRawValue.assetCost),
      assetType: new FormControl(memberAssetsRawValue.assetType),
      areaInSqFt: new FormControl(memberAssetsRawValue.areaInSqFt),
      assetNature: new FormControl(memberAssetsRawValue.assetNature),
      address: new FormControl(memberAssetsRawValue.address),
      landMark: new FormControl(memberAssetsRawValue.landMark),
      assetOwner: new FormControl(memberAssetsRawValue.assetOwner),
      completionYear: new FormControl(memberAssetsRawValue.completionYear),
      marketValue: new FormControl(memberAssetsRawValue.marketValue),
      isInsured: new FormControl(memberAssetsRawValue.isInsured),
      isUnderUsed: new FormControl(memberAssetsRawValue.isUnderUsed),
      isOwned: new FormControl(memberAssetsRawValue.isOwned),
      landType: new FormControl(memberAssetsRawValue.landType),
      landGatNo: new FormControl(memberAssetsRawValue.landGatNo),
      landAreaInHector: new FormControl(memberAssetsRawValue.landAreaInHector),
      jindagiPatrakNo: new FormControl(memberAssetsRawValue.jindagiPatrakNo),
      jindagiAmount: new FormControl(memberAssetsRawValue.jindagiAmount),
      lastModified: new FormControl(memberAssetsRawValue.lastModified),
      lastModifiedBy: new FormControl(memberAssetsRawValue.lastModifiedBy),
      createdBy: new FormControl(memberAssetsRawValue.createdBy),
      createdOn: new FormControl(memberAssetsRawValue.createdOn),
      isDeleted: new FormControl(memberAssetsRawValue.isDeleted),
      freeField1: new FormControl(memberAssetsRawValue.freeField1),
      freeField2: new FormControl(memberAssetsRawValue.freeField2),
      freeField3: new FormControl(memberAssetsRawValue.freeField3),
      freeField4: new FormControl(memberAssetsRawValue.freeField4),
      freeField5: new FormControl(memberAssetsRawValue.freeField5),
      freeField6: new FormControl(memberAssetsRawValue.freeField6),
      member: new FormControl(memberAssetsRawValue.member),
      loanApplications: new FormControl(memberAssetsRawValue.loanApplications),
    });
  }

  getMemberAssets(form: MemberAssetsFormGroup): IMemberAssets | NewMemberAssets {
    return this.convertMemberAssetsRawValueToMemberAssets(form.getRawValue() as MemberAssetsFormRawValue | NewMemberAssetsFormRawValue);
  }

  resetForm(form: MemberAssetsFormGroup, memberAssets: MemberAssetsFormGroupInput): void {
    const memberAssetsRawValue = this.convertMemberAssetsToMemberAssetsRawValue({ ...this.getFormDefaults(), ...memberAssets });
    form.reset(
      {
        ...memberAssetsRawValue,
        id: { value: memberAssetsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): MemberAssetsFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      isInsured: false,
      isUnderUsed: false,
      isOwned: false,
      lastModified: currentTime,
      createdOn: currentTime,
      isDeleted: false,
    };
  }

  private convertMemberAssetsRawValueToMemberAssets(
    rawMemberAssets: MemberAssetsFormRawValue | NewMemberAssetsFormRawValue
  ): IMemberAssets | NewMemberAssets {
    return {
      ...rawMemberAssets,
      lastModified: dayjs(rawMemberAssets.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawMemberAssets.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertMemberAssetsToMemberAssetsRawValue(
    memberAssets: IMemberAssets | (Partial<NewMemberAssets> & MemberAssetsFormDefaults)
  ): MemberAssetsFormRawValue | PartialWithRequiredKeyOf<NewMemberAssetsFormRawValue> {
    return {
      ...memberAssets,
      lastModified: memberAssets.lastModified ? memberAssets.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: memberAssets.createdOn ? memberAssets.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
