import dayjs from 'dayjs/esm';

import { RepaymentFreqency } from 'app/entities/enumerations/repayment-freqency.model';

import { IEpay, NewEpay } from './epay.model';

export const sampleWithRequiredData: IEpay = {
  id: 93154,
};

export const sampleWithPartialData: IEpay = {
  id: 84888,
  instrumentType: 'Customer-focused asynchronous RSS',
  accountNo: 'productize payment bypassing',
  installmentAmount: 40832,
  maxInstallmentAmount: 93220,
  mandateRefNo: 'Fresh COM Research',
  mandateType: 'embrace Chair',
  frequency: RepaymentFreqency['QUARTERLY'],
  ifscCode: 'Maine interfaces projection',
  isActive: false,
  lastModifiedBy: 'New Pennsylvania',
  freeField1: 'granular Florida productize',
};

export const sampleWithFullData: IEpay = {
  id: 55296,
  instrumentType: 'firewall salmon synthesize',
  dtFromDate: dayjs('2022-09-28'),
  dtToDate: dayjs('2022-09-28'),
  bankCode: 'exploit pink Computer',
  bankBranchCode: 'Synchronised Extended program',
  accountType: 'Dynamic Horizontal',
  accountNo: 'Virginia',
  maxCeilAmount: 43897,
  installmentAmount: 7968,
  maxInstallmentAmount: 61725,
  mandateRefNo: 'relationships withdrawal',
  depositBank: 'content-based North',
  mandateType: 'Practical program utilisation',
  frequency: RepaymentFreqency['QUARTERLY'],
  ifscCode: 'Associate Malta Romania',
  utrNo: 'recontextualize',
  isDeleted: false,
  isActive: true,
  lastModified: dayjs('2022-09-28T20:20'),
  lastModifiedBy: 'Consultant Incredible ivory',
  freeField1: 'connecting',
  freeField2: 'redefine yellow Kids',
  freeField3: 'Wooden transitional service-desk',
  freeField4: 'District Usability administration',
};

export const sampleWithNewData: NewEpay = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
