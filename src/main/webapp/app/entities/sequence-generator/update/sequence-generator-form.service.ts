import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ISequenceGenerator, NewSequenceGenerator } from '../sequence-generator.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISequenceGenerator for edit and NewSequenceGeneratorFormGroupInput for create.
 */
type SequenceGeneratorFormGroupInput = ISequenceGenerator | PartialWithRequiredKeyOf<NewSequenceGenerator>;

type SequenceGeneratorFormDefaults = Pick<NewSequenceGenerator, 'id'>;

type SequenceGeneratorFormGroupContent = {
  id: FormControl<ISequenceGenerator['id'] | NewSequenceGenerator['id']>;
  nextValMember: FormControl<ISequenceGenerator['nextValMember']>;
  nextValLoanApp: FormControl<ISequenceGenerator['nextValLoanApp']>;
  nextValLoanAccount: FormControl<ISequenceGenerator['nextValLoanAccount']>;
  nextValVoucher: FormControl<ISequenceGenerator['nextValVoucher']>;
  freeField1: FormControl<ISequenceGenerator['freeField1']>;
  freeField2: FormControl<ISequenceGenerator['freeField2']>;
  freeField3: FormControl<ISequenceGenerator['freeField3']>;
  freeField4: FormControl<ISequenceGenerator['freeField4']>;
  freeField5: FormControl<ISequenceGenerator['freeField5']>;
  branch: FormControl<ISequenceGenerator['branch']>;
};

export type SequenceGeneratorFormGroup = FormGroup<SequenceGeneratorFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SequenceGeneratorFormService {
  createSequenceGeneratorFormGroup(sequenceGenerator: SequenceGeneratorFormGroupInput = { id: null }): SequenceGeneratorFormGroup {
    const sequenceGeneratorRawValue = {
      ...this.getFormDefaults(),
      ...sequenceGenerator,
    };
    return new FormGroup<SequenceGeneratorFormGroupContent>({
      id: new FormControl(
        { value: sequenceGeneratorRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      nextValMember: new FormControl(sequenceGeneratorRawValue.nextValMember),
      nextValLoanApp: new FormControl(sequenceGeneratorRawValue.nextValLoanApp),
      nextValLoanAccount: new FormControl(sequenceGeneratorRawValue.nextValLoanAccount),
      nextValVoucher: new FormControl(sequenceGeneratorRawValue.nextValVoucher),
      freeField1: new FormControl(sequenceGeneratorRawValue.freeField1),
      freeField2: new FormControl(sequenceGeneratorRawValue.freeField2),
      freeField3: new FormControl(sequenceGeneratorRawValue.freeField3),
      freeField4: new FormControl(sequenceGeneratorRawValue.freeField4),
      freeField5: new FormControl(sequenceGeneratorRawValue.freeField5),
      branch: new FormControl(sequenceGeneratorRawValue.branch),
    });
  }

  getSequenceGenerator(form: SequenceGeneratorFormGroup): ISequenceGenerator | NewSequenceGenerator {
    return form.getRawValue() as ISequenceGenerator | NewSequenceGenerator;
  }

  resetForm(form: SequenceGeneratorFormGroup, sequenceGenerator: SequenceGeneratorFormGroupInput): void {
    const sequenceGeneratorRawValue = { ...this.getFormDefaults(), ...sequenceGenerator };
    form.reset(
      {
        ...sequenceGeneratorRawValue,
        id: { value: sequenceGeneratorRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): SequenceGeneratorFormDefaults {
    return {
      id: null,
    };
  }
}
