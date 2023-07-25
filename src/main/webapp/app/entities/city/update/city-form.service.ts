import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICity, NewCity } from '../city.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICity for edit and NewCityFormGroupInput for create.
 */
type CityFormGroupInput = ICity | PartialWithRequiredKeyOf<NewCity>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ICity | NewCity> = Omit<T, 'lastModified'> & {
  lastModified?: string | null;
};

type CityFormRawValue = FormValueOf<ICity>;

type NewCityFormRawValue = FormValueOf<NewCity>;

type CityFormDefaults = Pick<NewCity, 'id' | 'isDeleted' | 'lastModified'>;

type CityFormGroupContent = {
  id: FormControl<CityFormRawValue['id'] | NewCity['id']>;
  cityName: FormControl<CityFormRawValue['cityName']>;
  isDeleted: FormControl<CityFormRawValue['isDeleted']>;
  lgdCode: FormControl<CityFormRawValue['lgdCode']>;
  lastModified: FormControl<CityFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<CityFormRawValue['lastModifiedBy']>;
  taluka: FormControl<CityFormRawValue['taluka']>;
};

export type CityFormGroup = FormGroup<CityFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CityFormService {
  createCityFormGroup(city: CityFormGroupInput = { id: null }): CityFormGroup {
    const cityRawValue = this.convertCityToCityRawValue({
      ...this.getFormDefaults(),
      ...city,
    });
    return new FormGroup<CityFormGroupContent>({
      id: new FormControl(
        { value: cityRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      cityName: new FormControl(cityRawValue.cityName, {
        validators: [Validators.required],
      }),
      isDeleted: new FormControl(cityRawValue.isDeleted),
      lgdCode: new FormControl(cityRawValue.lgdCode),
      lastModified: new FormControl(cityRawValue.lastModified),
      lastModifiedBy: new FormControl(cityRawValue.lastModifiedBy),
      taluka: new FormControl(cityRawValue.taluka),
    });
  }

  getCity(form: CityFormGroup): ICity | NewCity {
    return this.convertCityRawValueToCity(form.getRawValue() as CityFormRawValue | NewCityFormRawValue);
  }

  resetForm(form: CityFormGroup, city: CityFormGroupInput): void {
    const cityRawValue = this.convertCityToCityRawValue({ ...this.getFormDefaults(), ...city });
    form.reset(
      {
        ...cityRawValue,
        id: { value: cityRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CityFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      isDeleted: false,
      lastModified: currentTime,
    };
  }

  private convertCityRawValueToCity(rawCity: CityFormRawValue | NewCityFormRawValue): ICity | NewCity {
    return {
      ...rawCity,
      lastModified: dayjs(rawCity.lastModified, DATE_TIME_FORMAT),
    };
  }

  private convertCityToCityRawValue(
    city: ICity | (Partial<NewCity> & CityFormDefaults)
  ): CityFormRawValue | PartialWithRequiredKeyOf<NewCityFormRawValue> {
    return {
      ...city,
      lastModified: city.lastModified ? city.lastModified.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
