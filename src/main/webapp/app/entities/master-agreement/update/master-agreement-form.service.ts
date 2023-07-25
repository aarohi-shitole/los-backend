import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IMasterAgreement, NewMasterAgreement } from '../master-agreement.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMasterAgreement for edit and NewMasterAgreementFormGroupInput for create.
 */
type MasterAgreementFormGroupInput = IMasterAgreement | PartialWithRequiredKeyOf<NewMasterAgreement>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IMasterAgreement | NewMasterAgreement> = Omit<T, 'lastModified'> & {
  lastModified?: string | null;
};

type MasterAgreementFormRawValue = FormValueOf<IMasterAgreement>;

type NewMasterAgreementFormRawValue = FormValueOf<NewMasterAgreement>;

type MasterAgreementFormDefaults = Pick<NewMasterAgreement, 'id' | 'isDeleted' | 'lastModified'>;

type MasterAgreementFormGroupContent = {
  id: FormControl<MasterAgreementFormRawValue['id'] | NewMasterAgreement['id']>;
  memberId: FormControl<MasterAgreementFormRawValue['memberId']>;
  portfolioCode: FormControl<MasterAgreementFormRawValue['portfolioCode']>;
  productCode: FormControl<MasterAgreementFormRawValue['productCode']>;
  homeBranch: FormControl<MasterAgreementFormRawValue['homeBranch']>;
  servBranch: FormControl<MasterAgreementFormRawValue['servBranch']>;
  homeState: FormControl<MasterAgreementFormRawValue['homeState']>;
  servState: FormControl<MasterAgreementFormRawValue['servState']>;
  gstExempted: FormControl<MasterAgreementFormRawValue['gstExempted']>;
  prefRepayMode: FormControl<MasterAgreementFormRawValue['prefRepayMode']>;
  tdsCode: FormControl<MasterAgreementFormRawValue['tdsCode']>;
  tdsRate: FormControl<MasterAgreementFormRawValue['tdsRate']>;
  sanctionedAmount: FormControl<MasterAgreementFormRawValue['sanctionedAmount']>;
  originationApplnNo: FormControl<MasterAgreementFormRawValue['originationApplnNo']>;
  cycleDay: FormControl<MasterAgreementFormRawValue['cycleDay']>;
  tenor: FormControl<MasterAgreementFormRawValue['tenor']>;
  interestRate: FormControl<MasterAgreementFormRawValue['interestRate']>;
  repayFreq: FormControl<MasterAgreementFormRawValue['repayFreq']>;
  isDeleted: FormControl<MasterAgreementFormRawValue['isDeleted']>;
  lastModified: FormControl<MasterAgreementFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<MasterAgreementFormRawValue['lastModifiedBy']>;
  freeField1: FormControl<MasterAgreementFormRawValue['freeField1']>;
  freeField2: FormControl<MasterAgreementFormRawValue['freeField2']>;
  freeField3: FormControl<MasterAgreementFormRawValue['freeField3']>;
  freeField4: FormControl<MasterAgreementFormRawValue['freeField4']>;
};

export type MasterAgreementFormGroup = FormGroup<MasterAgreementFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MasterAgreementFormService {
  createMasterAgreementFormGroup(masterAgreement: MasterAgreementFormGroupInput = { id: null }): MasterAgreementFormGroup {
    const masterAgreementRawValue = this.convertMasterAgreementToMasterAgreementRawValue({
      ...this.getFormDefaults(),
      ...masterAgreement,
    });
    return new FormGroup<MasterAgreementFormGroupContent>({
      id: new FormControl(
        { value: masterAgreementRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      memberId: new FormControl(masterAgreementRawValue.memberId),
      portfolioCode: new FormControl(masterAgreementRawValue.portfolioCode),
      productCode: new FormControl(masterAgreementRawValue.productCode),
      homeBranch: new FormControl(masterAgreementRawValue.homeBranch),
      servBranch: new FormControl(masterAgreementRawValue.servBranch),
      homeState: new FormControl(masterAgreementRawValue.homeState),
      servState: new FormControl(masterAgreementRawValue.servState),
      gstExempted: new FormControl(masterAgreementRawValue.gstExempted),
      prefRepayMode: new FormControl(masterAgreementRawValue.prefRepayMode),
      tdsCode: new FormControl(masterAgreementRawValue.tdsCode),
      tdsRate: new FormControl(masterAgreementRawValue.tdsRate),
      sanctionedAmount: new FormControl(masterAgreementRawValue.sanctionedAmount),
      originationApplnNo: new FormControl(masterAgreementRawValue.originationApplnNo),
      cycleDay: new FormControl(masterAgreementRawValue.cycleDay),
      tenor: new FormControl(masterAgreementRawValue.tenor),
      interestRate: new FormControl(masterAgreementRawValue.interestRate),
      repayFreq: new FormControl(masterAgreementRawValue.repayFreq),
      isDeleted: new FormControl(masterAgreementRawValue.isDeleted),
      lastModified: new FormControl(masterAgreementRawValue.lastModified),
      lastModifiedBy: new FormControl(masterAgreementRawValue.lastModifiedBy),
      freeField1: new FormControl(masterAgreementRawValue.freeField1),
      freeField2: new FormControl(masterAgreementRawValue.freeField2),
      freeField3: new FormControl(masterAgreementRawValue.freeField3),
      freeField4: new FormControl(masterAgreementRawValue.freeField4),
    });
  }

  getMasterAgreement(form: MasterAgreementFormGroup): IMasterAgreement | NewMasterAgreement {
    return this.convertMasterAgreementRawValueToMasterAgreement(
      form.getRawValue() as MasterAgreementFormRawValue | NewMasterAgreementFormRawValue
    );
  }

  resetForm(form: MasterAgreementFormGroup, masterAgreement: MasterAgreementFormGroupInput): void {
    const masterAgreementRawValue = this.convertMasterAgreementToMasterAgreementRawValue({ ...this.getFormDefaults(), ...masterAgreement });
    form.reset(
      {
        ...masterAgreementRawValue,
        id: { value: masterAgreementRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): MasterAgreementFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      isDeleted: false,
      lastModified: currentTime,
    };
  }

  private convertMasterAgreementRawValueToMasterAgreement(
    rawMasterAgreement: MasterAgreementFormRawValue | NewMasterAgreementFormRawValue
  ): IMasterAgreement | NewMasterAgreement {
    return {
      ...rawMasterAgreement,
      lastModified: dayjs(rawMasterAgreement.lastModified, DATE_TIME_FORMAT),
    };
  }

  private convertMasterAgreementToMasterAgreementRawValue(
    masterAgreement: IMasterAgreement | (Partial<NewMasterAgreement> & MasterAgreementFormDefaults)
  ): MasterAgreementFormRawValue | PartialWithRequiredKeyOf<NewMasterAgreementFormRawValue> {
    return {
      ...masterAgreement,
      lastModified: masterAgreement.lastModified ? masterAgreement.lastModified.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
