import dayjs from 'dayjs/esm';

import { ILoanCatagory, NewLoanCatagory } from './loan-catagory.model';

export const sampleWithRequiredData: ILoanCatagory = {
  id: 84991,
};

export const sampleWithPartialData: ILoanCatagory = {
  id: 55058,
  code: 'Island',
  offer: 'Papua Factors',
  lastModified: dayjs('2022-09-28T11:24'),
  lastModifiedBy: 'asymmetric program Agent',
  createdBy: 'quantifying turn-key Center',
  freeField2: 'technologies wireless',
  freeField3: 'Generic Automotive',
  freeField4: 'sensor supply-chains indigo',
};

export const sampleWithFullData: ILoanCatagory = {
  id: 90915,
  productName: 'migration Ford',
  decription: 'invoice Gloves',
  value: 'TCP open-source Concrete',
  code: 'mobile feed Assistant',
  offer: 'Representative JSON',
  lastModified: dayjs('2022-09-28T21:36'),
  lastModifiedBy: 'Tunisian',
  createdBy: 'content-based lavender Dakota',
  createdOn: dayjs('2022-09-28T10:00'),
  isDeleted: false,
  freeField1: 'SQL Soft',
  freeField2: 'Product',
  freeField3: 'Computer',
  freeField4: 'Engineer Granite Vision-oriented',
};

export const sampleWithNewData: NewLoanCatagory = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
