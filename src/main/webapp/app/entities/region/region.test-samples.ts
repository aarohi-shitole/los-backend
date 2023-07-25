import dayjs from 'dayjs/esm';

import { IRegion, NewRegion } from './region.model';

export const sampleWithRequiredData: IRegion = {
  id: 27363,
  regionName: 'back-end Practical',
};

export const sampleWithPartialData: IRegion = {
  id: 97181,
  regionName: 'instruction Metal salmon',
  isDeleted: true,
  freeField1: 'Bedfordshire',
  freeField2: 'Senior Administrator Grocery',
  freeField3: 'ROI',
  freeField5: 'Shoes overriding',
};

export const sampleWithFullData: IRegion = {
  id: 27858,
  regionName: 'Iowa Rubber',
  isDeleted: false,
  lastModified: dayjs('2022-09-29T00:23'),
  lastModifiedBy: 'Wyoming',
  freeField1: 'deposit Berkshire',
  freeField2: 'array Refined Ergonomic',
  freeField3: 'Chicken',
  freeField4: 'Buckinghamshire copy SMTP',
  freeField5: 'Computer back-end Planner',
};

export const sampleWithNewData: NewRegion = {
  regionName: 'Industrial',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
