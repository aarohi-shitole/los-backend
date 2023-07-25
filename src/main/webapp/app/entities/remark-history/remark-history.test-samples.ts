import dayjs from 'dayjs/esm';

import { IRemarkHistory, NewRemarkHistory } from './remark-history.model';

export const sampleWithRequiredData: IRemarkHistory = {
  id: 755,
};

export const sampleWithPartialData: IRemarkHistory = {
  id: 30865,
  remark: 'Refined',
  createdOn: dayjs('2022-09-29T05:12'),
  branch: 'Agent Chief incremental',
  freeField6: 'Hat Russian adapter',
};

export const sampleWithFullData: IRemarkHistory = {
  id: 2249,
  remark: 'deposit Pizza',
  loanAmt: 28187,
  modifiedAmt: 3552,
  loanInterest: 89788,
  modifiedInterest: 725,
  createdOn: dayjs('2022-09-28T23:10'),
  createdBy: 'circuit Directives Account',
  branch: 'Bacon deposit invoice',
  applicationNo: 'Reverse-engineered Garden',
  lastModified: dayjs('2022-09-28T06:15'),
  lastModifiedBy: 'networks front-end',
  freeField1: 'Investment Factors functionalities',
  freeField2: 'Electronics',
  freeField3: 'Future platforms',
  freeField4: 'Cambridgeshire Soft',
  freeField5: 'Central calculating',
  freeField6: 'e-business',
};

export const sampleWithNewData: NewRemarkHistory = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
