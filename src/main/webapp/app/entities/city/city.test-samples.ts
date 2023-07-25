import dayjs from 'dayjs/esm';

import { ICity, NewCity } from './city.model';

export const sampleWithRequiredData: ICity = {
  id: 79135,
  cityName: 'up multi-byte',
};

export const sampleWithPartialData: ICity = {
  id: 50634,
  cityName: 'Account Myanmar Account',
  lastModifiedBy: 'dot-com challenge Vista',
};

export const sampleWithFullData: ICity = {
  id: 90848,
  cityName: 'Course embrace',
  isDeleted: false,
  lgdCode: 97592,
  lastModified: dayjs('2022-09-29T04:56'),
  lastModifiedBy: 'Optional deposit Handcrafted',
};

export const sampleWithNewData: NewCity = {
  cityName: 'Automotive',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
