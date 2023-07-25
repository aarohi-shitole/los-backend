import dayjs from 'dayjs/esm';

import { IMemberLimit, NewMemberLimit } from './member-limit.model';

export const sampleWithRequiredData: IMemberLimit = {
  id: 27509,
};

export const sampleWithPartialData: IMemberLimit = {
  id: 10242,
  dtLimitSanctioned: dayjs('2022-09-28'),
  isActive: false,
  lastModified: dayjs('2022-09-28T17:26'),
  freeField1: 'Games calculating sensor',
  freeField3: 'Outdoors copy Solutions',
};

export const sampleWithFullData: IMemberLimit = {
  id: 81718,
  limitSanctionAmount: 58504,
  dtLimitSanctioned: dayjs('2022-09-29'),
  dtLimitExpired: dayjs('2022-09-28'),
  purpose: 'Pennsylvania Gourde',
  isDeleted: false,
  isActive: true,
  lastModified: dayjs('2022-09-28T15:19'),
  lastModifiedBy: 'Licensed',
  freeField1: 'Usability e-tailers',
  freeField2: 'Card Buckinghamshire',
  freeField3: 'Point North Pound',
  freeField4: 'virtual',
};

export const sampleWithNewData: NewMemberLimit = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
