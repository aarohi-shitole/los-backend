import dayjs from 'dayjs/esm';

import { InterestType } from 'app/entities/enumerations/interest-type.model';
import { RepaymentFreqency } from 'app/entities/enumerations/repayment-freqency.model';

import { IInterestConfig, NewInterestConfig } from './interest-config.model';

export const sampleWithRequiredData: IInterestConfig = {
  id: 56909,
};

export const sampleWithPartialData: IInterestConfig = {
  id: 82908,
  startDate: dayjs('2022-09-28T10:35'),
  emiBasis: 'deposit',
  intrAccrualFreq: RepaymentFreqency['HALF_YEARLY'],
  penalInterestRate: 16690,
  penalAccountingBasis: 'Intelligent Renminbi e-tailers',
  extendedInterestRate: 38749,
  lastModifiedBy: 'SQL neural-net metrics',
  freeField1: 'Music',
  freeField2: 'seamless Future',
  freeField3: "e-tailers Pa'anga",
  freeField4: 'Circles',
  freeField5: 'Regional',
  freeField6: 'Turks',
};

export const sampleWithFullData: IInterestConfig = {
  id: 91403,
  startDate: dayjs('2022-09-28T23:34'),
  endDate: dayjs('2022-09-28T22:30'),
  interestBasis: 'redundant bandwidth',
  emiBasis: 'Account optimal',
  interestType: InterestType['FLOATING'],
  intrAccrualFreq: RepaymentFreqency['QUARTERLY'],
  penalInterestRate: 64265,
  penalInterestBasis: 'redefine recontextualize',
  penalAccountingBasis: 'pricing maximize',
  minInterestRate: 61542,
  maxInterestRate: 56751,
  extendedInterestRate: 58017,
  surchargeRate: 35090,
  isDeleted: true,
  lastModified: dayjs('2022-09-29T03:46'),
  lastModifiedBy: 'deliver Fully-configurable exploit',
  freeField1: 'Frozen Technician Awesome',
  freeField2: 'overriding bypassing partnerships',
  freeField3: 'Shoes concept Views',
  freeField4: 'Krona Intelligent',
  freeField5: 'wireless',
  freeField6: 'Coordinator',
};

export const sampleWithNewData: NewInterestConfig = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
