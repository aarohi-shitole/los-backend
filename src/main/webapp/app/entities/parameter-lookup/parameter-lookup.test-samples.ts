import dayjs from 'dayjs/esm';

import { ParameterLookupType } from 'app/entities/enumerations/parameter-lookup-type.model';

import { IParameterLookup, NewParameterLookup } from './parameter-lookup.model';

export const sampleWithRequiredData: IParameterLookup = {
  id: 5221,
};

export const sampleWithPartialData: IParameterLookup = {
  id: 84786,
  description: 'Wooden',
  value: 'efficient SSL',
  lastModified: dayjs('2022-09-29T00:42'),
  createdOn: dayjs('2022-09-28T17:31'),
  freeField4: 'Fresh Cotton',
};

export const sampleWithFullData: IParameterLookup = {
  id: 1316,
  type: ParameterLookupType['RELIGION'],
  name: 'Krone',
  description: 'recontextualize hack Concrete',
  value: 'mobile Sports',
  lastModified: dayjs('2022-09-29T05:26'),
  lastModifiedBy: 'Granite',
  createdBy: 'value-added',
  createdOn: dayjs('2022-09-28T14:53'),
  isDeleted: true,
  freeField1: 'Bike transmitting',
  freeField2: 'Account Tasty',
  freeField3: 'Fresh',
  freeField4: 'system expedite Loan',
};

export const sampleWithNewData: NewParameterLookup = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
