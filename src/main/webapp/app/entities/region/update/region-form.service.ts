import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IRegion, NewRegion } from '../region.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRegion for edit and NewRegionFormGroupInput for create.
 */
type RegionFormGroupInput = IRegion | PartialWithRequiredKeyOf<NewRegion>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IRegion | NewRegion> = Omit<T, 'lastModified'> & {
  lastModified?: string | null;
};

type RegionFormRawValue = FormValueOf<IRegion>;

type NewRegionFormRawValue = FormValueOf<NewRegion>;

type RegionFormDefaults = Pick<NewRegion, 'id' | 'isDeleted' | 'lastModified'>;

type RegionFormGroupContent = {
  id: FormControl<RegionFormRawValue['id'] | NewRegion['id']>;
  regionName: FormControl<RegionFormRawValue['regionName']>;
  isDeleted: FormControl<RegionFormRawValue['isDeleted']>;
  lastModified: FormControl<RegionFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<RegionFormRawValue['lastModifiedBy']>;
  freeField1: FormControl<RegionFormRawValue['freeField1']>;
  freeField2: FormControl<RegionFormRawValue['freeField2']>;
  freeField3: FormControl<RegionFormRawValue['freeField3']>;
  freeField4: FormControl<RegionFormRawValue['freeField4']>;
  freeField5: FormControl<RegionFormRawValue['freeField5']>;
  organisation: FormControl<RegionFormRawValue['organisation']>;
};

export type RegionFormGroup = FormGroup<RegionFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RegionFormService {
  createRegionFormGroup(region: RegionFormGroupInput = { id: null }): RegionFormGroup {
    const regionRawValue = this.convertRegionToRegionRawValue({
      ...this.getFormDefaults(),
      ...region,
    });
    return new FormGroup<RegionFormGroupContent>({
      id: new FormControl(
        { value: regionRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      regionName: new FormControl(regionRawValue.regionName, {
        validators: [Validators.required],
      }),
      isDeleted: new FormControl(regionRawValue.isDeleted),
      lastModified: new FormControl(regionRawValue.lastModified),
      lastModifiedBy: new FormControl(regionRawValue.lastModifiedBy),
      freeField1: new FormControl(regionRawValue.freeField1),
      freeField2: new FormControl(regionRawValue.freeField2),
      freeField3: new FormControl(regionRawValue.freeField3),
      freeField4: new FormControl(regionRawValue.freeField4),
      freeField5: new FormControl(regionRawValue.freeField5),
      organisation: new FormControl(regionRawValue.organisation),
    });
  }

  getRegion(form: RegionFormGroup): IRegion | NewRegion {
    return this.convertRegionRawValueToRegion(form.getRawValue() as RegionFormRawValue | NewRegionFormRawValue);
  }

  resetForm(form: RegionFormGroup, region: RegionFormGroupInput): void {
    const regionRawValue = this.convertRegionToRegionRawValue({ ...this.getFormDefaults(), ...region });
    form.reset(
      {
        ...regionRawValue,
        id: { value: regionRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): RegionFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      isDeleted: false,
      lastModified: currentTime,
    };
  }

  private convertRegionRawValueToRegion(rawRegion: RegionFormRawValue | NewRegionFormRawValue): IRegion | NewRegion {
    return {
      ...rawRegion,
      lastModified: dayjs(rawRegion.lastModified, DATE_TIME_FORMAT),
    };
  }

  private convertRegionToRegionRawValue(
    region: IRegion | (Partial<NewRegion> & RegionFormDefaults)
  ): RegionFormRawValue | PartialWithRequiredKeyOf<NewRegionFormRawValue> {
    return {
      ...region,
      lastModified: region.lastModified ? region.lastModified.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
