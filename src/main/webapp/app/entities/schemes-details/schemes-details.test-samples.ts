import dayjs from 'dayjs/esm';

import { ISchemesDetails, NewSchemesDetails } from './schemes-details.model';

export const sampleWithRequiredData: ISchemesDetails = {
  id: 3559,
};

export const sampleWithPartialData: ISchemesDetails = {
  id: 19514,
  fdDurationDays: 6610,
  rdDurationMonths: 66313,
  reinvestInterest: false,
  lastDayOfScheam: dayjs('2022-09-28T18:59'),
  createdBy: 'Granite',
  isDeleted: true,
  freeField3: 'program Diverse',
};

export const sampleWithFullData: ISchemesDetails = {
  id: 74274,
  fdDurationDays: 4220,
  interest: 25124,
  lockInPeriod: 2557,
  schemeName: 'Account',
  rdDurationMonths: 92558,
  reinvestInterest: true,
  minAmount: 93054,
  lastDayOfScheam: dayjs('2022-09-28T14:41'),
  lastModified: dayjs('2022-09-29T05:22'),
  lastModifiedBy: 'Money',
  createdBy: 'bandwidth vertical',
  createdOn: dayjs('2022-09-28T23:43'),
  isDeleted: true,
  freeField1: 'Markets Security',
  freeField2: 'mobile hacking',
  freeField3: 'Lira bandwidth-monitored withdrawal',
  freeField4: 'Peso',
  freeField5: 'embrace Extensions',
};

export const sampleWithNewData: NewSchemesDetails = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
