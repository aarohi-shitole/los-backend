import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ILoanCatagory, NewLoanCatagory } from '../loan-catagory.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILoanCatagory for edit and NewLoanCatagoryFormGroupInput for create.
 */
type LoanCatagoryFormGroupInput = ILoanCatagory | PartialWithRequiredKeyOf<NewLoanCatagory>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ILoanCatagory | NewLoanCatagory> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

type LoanCatagoryFormRawValue = FormValueOf<ILoanCatagory>;

type NewLoanCatagoryFormRawValue = FormValueOf<NewLoanCatagory>;

type LoanCatagoryFormDefaults = Pick<NewLoanCatagory, 'id' | 'lastModified' | 'createdOn' | 'isDeleted'>;

type LoanCatagoryFormGroupContent = {
  id: FormControl<LoanCatagoryFormRawValue['id'] | NewLoanCatagory['id']>;
  productName: FormControl<LoanCatagoryFormRawValue['productName']>;
  decription: FormControl<LoanCatagoryFormRawValue['decription']>;
  value: FormControl<LoanCatagoryFormRawValue['value']>;
  code: FormControl<LoanCatagoryFormRawValue['code']>;
  offer: FormControl<LoanCatagoryFormRawValue['offer']>;
  lastModified: FormControl<LoanCatagoryFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<LoanCatagoryFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<LoanCatagoryFormRawValue['createdBy']>;
  createdOn: FormControl<LoanCatagoryFormRawValue['createdOn']>;
  isDeleted: FormControl<LoanCatagoryFormRawValue['isDeleted']>;
  freeField1: FormControl<LoanCatagoryFormRawValue['freeField1']>;
  freeField2: FormControl<LoanCatagoryFormRawValue['freeField2']>;
  freeField3: FormControl<LoanCatagoryFormRawValue['freeField3']>;
  freeField4: FormControl<LoanCatagoryFormRawValue['freeField4']>;
};

export type LoanCatagoryFormGroup = FormGroup<LoanCatagoryFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LoanCatagoryFormService {
  createLoanCatagoryFormGroup(loanCatagory: LoanCatagoryFormGroupInput = { id: null }): LoanCatagoryFormGroup {
    const loanCatagoryRawValue = this.convertLoanCatagoryToLoanCatagoryRawValue({
      ...this.getFormDefaults(),
      ...loanCatagory,
    });
    return new FormGroup<LoanCatagoryFormGroupContent>({
      id: new FormControl(
        { value: loanCatagoryRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      productName: new FormControl(loanCatagoryRawValue.productName),
      decription: new FormControl(loanCatagoryRawValue.decription),
      value: new FormControl(loanCatagoryRawValue.value),
      code: new FormControl(loanCatagoryRawValue.code),
      offer: new FormControl(loanCatagoryRawValue.offer),
      lastModified: new FormControl(loanCatagoryRawValue.lastModified),
      lastModifiedBy: new FormControl(loanCatagoryRawValue.lastModifiedBy),
      createdBy: new FormControl(loanCatagoryRawValue.createdBy),
      createdOn: new FormControl(loanCatagoryRawValue.createdOn),
      isDeleted: new FormControl(loanCatagoryRawValue.isDeleted),
      freeField1: new FormControl(loanCatagoryRawValue.freeField1),
      freeField2: new FormControl(loanCatagoryRawValue.freeField2),
      freeField3: new FormControl(loanCatagoryRawValue.freeField3),
      freeField4: new FormControl(loanCatagoryRawValue.freeField4),
    });
  }

  getLoanCatagory(form: LoanCatagoryFormGroup): ILoanCatagory | NewLoanCatagory {
    return this.convertLoanCatagoryRawValueToLoanCatagory(form.getRawValue() as LoanCatagoryFormRawValue | NewLoanCatagoryFormRawValue);
  }

  resetForm(form: LoanCatagoryFormGroup, loanCatagory: LoanCatagoryFormGroupInput): void {
    const loanCatagoryRawValue = this.convertLoanCatagoryToLoanCatagoryRawValue({ ...this.getFormDefaults(), ...loanCatagory });
    form.reset(
      {
        ...loanCatagoryRawValue,
        id: { value: loanCatagoryRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): LoanCatagoryFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      lastModified: currentTime,
      createdOn: currentTime,
      isDeleted: false,
    };
  }

  private convertLoanCatagoryRawValueToLoanCatagory(
    rawLoanCatagory: LoanCatagoryFormRawValue | NewLoanCatagoryFormRawValue
  ): ILoanCatagory | NewLoanCatagory {
    return {
      ...rawLoanCatagory,
      lastModified: dayjs(rawLoanCatagory.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawLoanCatagory.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertLoanCatagoryToLoanCatagoryRawValue(
    loanCatagory: ILoanCatagory | (Partial<NewLoanCatagory> & LoanCatagoryFormDefaults)
  ): LoanCatagoryFormRawValue | PartialWithRequiredKeyOf<NewLoanCatagoryFormRawValue> {
    return {
      ...loanCatagory,
      lastModified: loanCatagory.lastModified ? loanCatagory.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: loanCatagory.createdOn ? loanCatagory.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
