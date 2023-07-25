import dayjs from 'dayjs/esm';

import { AddressType } from 'app/entities/enumerations/address-type.model';

import { IAddress, NewAddress } from './address.model';

export const sampleWithRequiredData: IAddress = {
  id: 88754,
};

export const sampleWithPartialData: IAddress = {
  id: 56265,
  addrStatus: AddressType['CURRENT_ADDRESS'],
  addressLine1: 'Buckinghamshire interfaces',
  landMark: 'Persevering',
  pincode: 'Ameliorated',
  longitude: 79117,
  latitude: 80702,
  isPrefferedAdd: false,
  lastModified: dayjs('2022-09-28T23:07'),
  createdOn: dayjs('2022-09-29T02:36'),
  isDeleted: false,
  freeField1: 'hybrid',
  freeField2: 'Course Chair viral',
};

export const sampleWithFullData: IAddress = {
  id: 17340,
  addrStatus: AddressType['PERMANENT_ADDRESS'],
  addressLine1: 'synthesizing intranet',
  addressLine2: 'hack',
  addressLine3: 'dynamic Birr experiences',
  landMark: 'bus',
  pincode: 'Handmade',
  longitude: 26754,
  latitude: 53445,
  mapNevUrl: 'Loan solution',
  isPrefferedAdd: false,
  lastModified: dayjs('2022-09-28T15:09'),
  lastModifiedBy: 'haptic Hill',
  createdBy: 'Consultant',
  createdOn: dayjs('2022-09-28T08:55'),
  isDeleted: true,
  freeField1: 'Identity Games',
  freeField2: 'microchip Territories',
  freeField3: 'Dollar',
  freeField4: 'Kazakhstan',
  freeField5: 'digital',
};

export const sampleWithNewData: NewAddress = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
