import dayjs from 'dayjs/esm';

import { ITaluka, NewTaluka } from './taluka.model';

export const sampleWithRequiredData: ITaluka = {
  id: 3499,
  talukaName: 'Handmade quantify web-readiness',
};

export const sampleWithPartialData: ITaluka = {
  id: 56140,
  talukaName: 'Reactive engage synergize',
  isDeleted: false,
  lgdCode: 36326,
  lastModified: dayjs('2022-09-28T15:30'),
  lastModifiedBy: 'collaborative Missouri',
};

export const sampleWithFullData: ITaluka = {
  id: 65445,
  talukaName: 'Focused generating',
  isDeleted: false,
  lgdCode: 87017,
  lastModified: dayjs('2022-09-28T15:57'),
  lastModifiedBy: 'Granite mobile',
};

export const sampleWithNewData: NewTaluka = {
  talukaName: 'primary Keyboard',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
