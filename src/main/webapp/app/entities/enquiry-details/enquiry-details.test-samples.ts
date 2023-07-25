import dayjs from 'dayjs/esm';

import { IEnquiryDetails, NewEnquiryDetails } from './enquiry-details.model';

export const sampleWithRequiredData: IEnquiryDetails = {
  id: 34538,
};

export const sampleWithPartialData: IEnquiryDetails = {
  id: 27779,
  customerName: 'Australian',
  subName: 'Outdoors Officer',
  purpose: 'convergence state Missouri',
  amount: 73403,
  isDeleted: false,
  lastModified: dayjs('2022-09-29T04:22'),
  freeField2: 'ivory',
  freeField3: 'Small',
  freeField4: 'navigating',
};

export const sampleWithFullData: IEnquiryDetails = {
  id: 98240,
  customerName: 'Pound Creative',
  subName: 'Rapid Luxembourg',
  purpose: 'Garden Phased Account',
  mobileNo: '24/7',
  amount: 32155,
  refrenceNo: 'Tasty',
  isDeleted: true,
  isActive: false,
  lastModified: dayjs('2022-09-28T16:14'),
  lastModifiedBy: 'Camp',
  freeField1: 'Future invoice Computers',
  freeField2: 'programming parsing Buckinghamshire',
  freeField3: 'Balanced encoding',
  freeField4: 'black Administrator',
};

export const sampleWithNewData: NewEnquiryDetails = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
