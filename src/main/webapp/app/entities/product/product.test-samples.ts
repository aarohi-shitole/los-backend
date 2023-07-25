import dayjs from 'dayjs/esm';

import { IProduct, NewProduct } from './product.model';

export const sampleWithRequiredData: IProduct = {
  id: 77672,
};

export const sampleWithPartialData: IProduct = {
  id: 72582,
  amortizationMethod: 'hub',
  amortizationType: 'Customer',
  compoundingFreq: 'Plastic Incredible Agent',
  lastRowEMIThreshold: 67547,
  reschLockinPeriod: 26721,
  prepayBeforeInstNo: 33693,
  maxTenor: 70955,
  dropLineAmount: 36230,
  dropLinePerc: 3170,
  dropLineCycleDay: 55546,
  roi: 30768,
  isDeleted: true,
  lastModified: dayjs('2022-09-28T08:25'),
  lastModifiedBy: 'neural intuitive SDR',
  freeField1: 'Intelligent structure lime',
  freeField2: 'bandwidth magnetic',
  freeField4: 'enhance Berkshire payment',
  freeField6: 'Horizontal',
};

export const sampleWithFullData: IProduct = {
  id: 27616,
  prodCode: 'Franc',
  prodName: 'pixel Pound copy',
  bpiTreatmentFlag: 'Salad Dollar',
  amortizationMethod: 'Rapid',
  amortizationType: 'synergy Village',
  compoundingFreq: 'red Security',
  emiRounding: 'Planner',
  lastRowEMIThreshold: 61649,
  graceDays: 1052,
  reschLockinPeriod: 7306,
  prepayAfterInstNo: 84799,
  prepayBeforeInstNo: 13073,
  minInstallmentGapBetPrepay: 54717,
  minPrepayAmount: 77268,
  forecloseAfterInstNo: 93874,
  forecloseBeforeInstaNo: 72698,
  minTenor: 21399,
  maxTenor: 30393,
  minInstallmentAmount: 19093,
  maxInstallmentAmount: 3278,
  dropLineAmount: 63574,
  dropLineODYN: 'hack Shirt Adaptive',
  dropLinePerc: 15438,
  dropMode: 'viral Circles',
  dropLIneFreq: 'Account Car Green',
  dropLineCycleDay: 69801,
  roi: 54914,
  isDeleted: false,
  lastModified: dayjs('2022-09-28T15:57'),
  lastModifiedBy: 'Officer workforce',
  freeField1: 'Senior',
  freeField2: 'Handmade Rial indigo',
  freeField3: 'Franc back-end Global',
  freeField4: 'up Data',
  freeField5: 'Square',
  freeField6: 'Utah Markets value-added',
};

export const sampleWithNewData: NewProduct = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
