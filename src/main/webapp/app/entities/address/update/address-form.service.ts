import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAddress, NewAddress } from '../address.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAddress for edit and NewAddressFormGroupInput for create.
 */
type AddressFormGroupInput = IAddress | PartialWithRequiredKeyOf<NewAddress>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IAddress | NewAddress> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

type AddressFormRawValue = FormValueOf<IAddress>;

type NewAddressFormRawValue = FormValueOf<NewAddress>;

type AddressFormDefaults = Pick<NewAddress, 'id' | 'isPrefferedAdd' | 'lastModified' | 'createdOn' | 'isDeleted'>;

type AddressFormGroupContent = {
  id: FormControl<AddressFormRawValue['id'] | NewAddress['id']>;
  addrStatus: FormControl<AddressFormRawValue['addrStatus']>;
  addressLine1: FormControl<AddressFormRawValue['addressLine1']>;
  addressLine2: FormControl<AddressFormRawValue['addressLine2']>;
  addressLine3: FormControl<AddressFormRawValue['addressLine3']>;
  landMark: FormControl<AddressFormRawValue['landMark']>;
  pincode: FormControl<AddressFormRawValue['pincode']>;
  longitude: FormControl<AddressFormRawValue['longitude']>;
  latitude: FormControl<AddressFormRawValue['latitude']>;
  mapNevUrl: FormControl<AddressFormRawValue['mapNevUrl']>;
  isPrefferedAdd: FormControl<AddressFormRawValue['isPrefferedAdd']>;
  lastModified: FormControl<AddressFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<AddressFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<AddressFormRawValue['createdBy']>;
  createdOn: FormControl<AddressFormRawValue['createdOn']>;
  isDeleted: FormControl<AddressFormRawValue['isDeleted']>;
  freeField1: FormControl<AddressFormRawValue['freeField1']>;
  freeField2: FormControl<AddressFormRawValue['freeField2']>;
  freeField3: FormControl<AddressFormRawValue['freeField3']>;
  freeField4: FormControl<AddressFormRawValue['freeField4']>;
  freeField5: FormControl<AddressFormRawValue['freeField5']>;
  state: FormControl<AddressFormRawValue['state']>;
  district: FormControl<AddressFormRawValue['district']>;
  taluka: FormControl<AddressFormRawValue['taluka']>;
  city: FormControl<AddressFormRawValue['city']>;
  member: FormControl<AddressFormRawValue['member']>;
  securityUser: FormControl<AddressFormRawValue['securityUser']>;
};

export type AddressFormGroup = FormGroup<AddressFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AddressFormService {
  createAddressFormGroup(address: AddressFormGroupInput = { id: null }): AddressFormGroup {
    const addressRawValue = this.convertAddressToAddressRawValue({
      ...this.getFormDefaults(),
      ...address,
    });
    return new FormGroup<AddressFormGroupContent>({
      id: new FormControl(
        { value: addressRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      addrStatus: new FormControl(addressRawValue.addrStatus),
      addressLine1: new FormControl(addressRawValue.addressLine1),
      addressLine2: new FormControl(addressRawValue.addressLine2),
      addressLine3: new FormControl(addressRawValue.addressLine3),
      landMark: new FormControl(addressRawValue.landMark),
      pincode: new FormControl(addressRawValue.pincode),
      longitude: new FormControl(addressRawValue.longitude),
      latitude: new FormControl(addressRawValue.latitude),
      mapNevUrl: new FormControl(addressRawValue.mapNevUrl),
      isPrefferedAdd: new FormControl(addressRawValue.isPrefferedAdd),
      lastModified: new FormControl(addressRawValue.lastModified),
      lastModifiedBy: new FormControl(addressRawValue.lastModifiedBy),
      createdBy: new FormControl(addressRawValue.createdBy),
      createdOn: new FormControl(addressRawValue.createdOn),
      isDeleted: new FormControl(addressRawValue.isDeleted),
      freeField1: new FormControl(addressRawValue.freeField1),
      freeField2: new FormControl(addressRawValue.freeField2),
      freeField3: new FormControl(addressRawValue.freeField3),
      freeField4: new FormControl(addressRawValue.freeField4),
      freeField5: new FormControl(addressRawValue.freeField5),
      state: new FormControl(addressRawValue.state),
      district: new FormControl(addressRawValue.district),
      taluka: new FormControl(addressRawValue.taluka),
      city: new FormControl(addressRawValue.city),
      member: new FormControl(addressRawValue.member),
      securityUser: new FormControl(addressRawValue.securityUser),
    });
  }

  getAddress(form: AddressFormGroup): IAddress | NewAddress {
    return this.convertAddressRawValueToAddress(form.getRawValue() as AddressFormRawValue | NewAddressFormRawValue);
  }

  resetForm(form: AddressFormGroup, address: AddressFormGroupInput): void {
    const addressRawValue = this.convertAddressToAddressRawValue({ ...this.getFormDefaults(), ...address });
    form.reset(
      {
        ...addressRawValue,
        id: { value: addressRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AddressFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      isPrefferedAdd: false,
      lastModified: currentTime,
      createdOn: currentTime,
      isDeleted: false,
    };
  }

  private convertAddressRawValueToAddress(rawAddress: AddressFormRawValue | NewAddressFormRawValue): IAddress | NewAddress {
    return {
      ...rawAddress,
      lastModified: dayjs(rawAddress.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawAddress.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertAddressToAddressRawValue(
    address: IAddress | (Partial<NewAddress> & AddressFormDefaults)
  ): AddressFormRawValue | PartialWithRequiredKeyOf<NewAddressFormRawValue> {
    return {
      ...address,
      lastModified: address.lastModified ? address.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: address.createdOn ? address.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
