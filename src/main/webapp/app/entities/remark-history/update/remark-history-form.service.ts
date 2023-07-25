import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IRemarkHistory, NewRemarkHistory } from '../remark-history.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRemarkHistory for edit and NewRemarkHistoryFormGroupInput for create.
 */
type RemarkHistoryFormGroupInput = IRemarkHistory | PartialWithRequiredKeyOf<NewRemarkHistory>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IRemarkHistory | NewRemarkHistory> = Omit<T, 'createdOn' | 'lastModified'> & {
  createdOn?: string | null;
  lastModified?: string | null;
};

type RemarkHistoryFormRawValue = FormValueOf<IRemarkHistory>;

type NewRemarkHistoryFormRawValue = FormValueOf<NewRemarkHistory>;

type RemarkHistoryFormDefaults = Pick<NewRemarkHistory, 'id' | 'createdOn' | 'lastModified'>;

type RemarkHistoryFormGroupContent = {
  id: FormControl<RemarkHistoryFormRawValue['id'] | NewRemarkHistory['id']>;
  remark: FormControl<RemarkHistoryFormRawValue['remark']>;
  loanAmt: FormControl<RemarkHistoryFormRawValue['loanAmt']>;
  modifiedAmt: FormControl<RemarkHistoryFormRawValue['modifiedAmt']>;
  loanInterest: FormControl<RemarkHistoryFormRawValue['loanInterest']>;
  modifiedInterest: FormControl<RemarkHistoryFormRawValue['modifiedInterest']>;
  createdOn: FormControl<RemarkHistoryFormRawValue['createdOn']>;
  createdBy: FormControl<RemarkHistoryFormRawValue['createdBy']>;
  branch: FormControl<RemarkHistoryFormRawValue['branch']>;
  applicationNo: FormControl<RemarkHistoryFormRawValue['applicationNo']>;
  lastModified: FormControl<RemarkHistoryFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<RemarkHistoryFormRawValue['lastModifiedBy']>;
  freeField1: FormControl<RemarkHistoryFormRawValue['freeField1']>;
  freeField2: FormControl<RemarkHistoryFormRawValue['freeField2']>;
  freeField3: FormControl<RemarkHistoryFormRawValue['freeField3']>;
  freeField4: FormControl<RemarkHistoryFormRawValue['freeField4']>;
  freeField5: FormControl<RemarkHistoryFormRawValue['freeField5']>;
  freeField6: FormControl<RemarkHistoryFormRawValue['freeField6']>;
  securityUser: FormControl<RemarkHistoryFormRawValue['securityUser']>;
  loanApplications: FormControl<RemarkHistoryFormRawValue['loanApplications']>;
};

export type RemarkHistoryFormGroup = FormGroup<RemarkHistoryFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RemarkHistoryFormService {
  createRemarkHistoryFormGroup(remarkHistory: RemarkHistoryFormGroupInput = { id: null }): RemarkHistoryFormGroup {
    const remarkHistoryRawValue = this.convertRemarkHistoryToRemarkHistoryRawValue({
      ...this.getFormDefaults(),
      ...remarkHistory,
    });
    return new FormGroup<RemarkHistoryFormGroupContent>({
      id: new FormControl(
        { value: remarkHistoryRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      remark: new FormControl(remarkHistoryRawValue.remark),
      loanAmt: new FormControl(remarkHistoryRawValue.loanAmt),
      modifiedAmt: new FormControl(remarkHistoryRawValue.modifiedAmt),
      loanInterest: new FormControl(remarkHistoryRawValue.loanInterest),
      modifiedInterest: new FormControl(remarkHistoryRawValue.modifiedInterest),
      createdOn: new FormControl(remarkHistoryRawValue.createdOn),
      createdBy: new FormControl(remarkHistoryRawValue.createdBy),
      branch: new FormControl(remarkHistoryRawValue.branch),
      applicationNo: new FormControl(remarkHistoryRawValue.applicationNo),
      lastModified: new FormControl(remarkHistoryRawValue.lastModified),
      lastModifiedBy: new FormControl(remarkHistoryRawValue.lastModifiedBy),
      freeField1: new FormControl(remarkHistoryRawValue.freeField1),
      freeField2: new FormControl(remarkHistoryRawValue.freeField2),
      freeField3: new FormControl(remarkHistoryRawValue.freeField3),
      freeField4: new FormControl(remarkHistoryRawValue.freeField4),
      freeField5: new FormControl(remarkHistoryRawValue.freeField5),
      freeField6: new FormControl(remarkHistoryRawValue.freeField6),
      securityUser: new FormControl(remarkHistoryRawValue.securityUser),
      loanApplications: new FormControl(remarkHistoryRawValue.loanApplications),
    });
  }

  getRemarkHistory(form: RemarkHistoryFormGroup): IRemarkHistory | NewRemarkHistory {
    return this.convertRemarkHistoryRawValueToRemarkHistory(form.getRawValue() as RemarkHistoryFormRawValue | NewRemarkHistoryFormRawValue);
  }

  resetForm(form: RemarkHistoryFormGroup, remarkHistory: RemarkHistoryFormGroupInput): void {
    const remarkHistoryRawValue = this.convertRemarkHistoryToRemarkHistoryRawValue({ ...this.getFormDefaults(), ...remarkHistory });
    form.reset(
      {
        ...remarkHistoryRawValue,
        id: { value: remarkHistoryRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): RemarkHistoryFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createdOn: currentTime,
      lastModified: currentTime,
    };
  }

  private convertRemarkHistoryRawValueToRemarkHistory(
    rawRemarkHistory: RemarkHistoryFormRawValue | NewRemarkHistoryFormRawValue
  ): IRemarkHistory | NewRemarkHistory {
    return {
      ...rawRemarkHistory,
      createdOn: dayjs(rawRemarkHistory.createdOn, DATE_TIME_FORMAT),
      lastModified: dayjs(rawRemarkHistory.lastModified, DATE_TIME_FORMAT),
    };
  }

  private convertRemarkHistoryToRemarkHistoryRawValue(
    remarkHistory: IRemarkHistory | (Partial<NewRemarkHistory> & RemarkHistoryFormDefaults)
  ): RemarkHistoryFormRawValue | PartialWithRequiredKeyOf<NewRemarkHistoryFormRawValue> {
    return {
      ...remarkHistory,
      createdOn: remarkHistory.createdOn ? remarkHistory.createdOn.format(DATE_TIME_FORMAT) : undefined,
      lastModified: remarkHistory.lastModified ? remarkHistory.lastModified.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
