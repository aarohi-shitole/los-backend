import dayjs from 'dayjs/esm';

import { IncomeType } from 'app/entities/enumerations/income-type.model';

import { IIncomeDetails, NewIncomeDetails } from './income-details.model';

export const sampleWithRequiredData: IIncomeDetails = {
  id: 49223,
};

export const sampleWithPartialData: IIncomeDetails = {
  id: 10294,
  year: 'transmitter firewall Savings',
  grossIncome: 97623,
  cashSurplus: 42789,
  incomeType: IncomeType['PRINCIPAL_SOURCE'],
  isDeleted: false,
  lastModifiedBy: 'withdrawal Awesome',
  freeField3: 'incubate Strategist',
};

export const sampleWithFullData: IIncomeDetails = {
  id: 25981,
  year: 'Clothing Monitored',
  grossIncome: 11563,
  expenses: 3309,
  netIncome: 62322,
  paidTaxes: 10511,
  cashSurplus: 40721,
  incomeType: IncomeType['OTHER_SOURCE'],
  isDeleted: true,
  lastModified: dayjs('2022-09-28T19:13'),
  lastModifiedBy: 'Loan Soap e-tailers',
  createdBy: 'Fundamental Steel interactive',
  createdOn: dayjs('2022-09-28T21:32'),
  freeField1: 'Specialist Chair and',
  freeField2: 'Lead Account',
  freeField3: 'invoice',
  freeField4: 'GB Clothing',
  freeField5: 'Re-engineered',
  freeField6: 'Account synthesizing Metrics',
};

export const sampleWithNewData: NewIncomeDetails = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
