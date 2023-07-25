import dayjs from 'dayjs/esm';

import { RepaymentFreqency } from 'app/entities/enumerations/repayment-freqency.model';

import { IMasterAgreement, NewMasterAgreement } from './master-agreement.model';

export const sampleWithRequiredData: IMasterAgreement = {
  id: 78156,
};

export const sampleWithPartialData: IMasterAgreement = {
  id: 5444,
  productCode: 'Chips Valley Shoes',
  servState: 'Industrial seize',
  prefRepayMode: 'Cross-group Kong',
  tdsCode: 'Account turn-key',
  tenor: 'Netherlands Borders',
  lastModified: dayjs('2022-09-28T19:09'),
  freeField1: 'magnetic solid Industrial',
  freeField3: 'Rand infrastructures neural',
  freeField4: 'Plaza bluetooth Mouse',
};

export const sampleWithFullData: IMasterAgreement = {
  id: 7172,
  memberId: 'Pizza',
  portfolioCode: 'Public-key Web',
  productCode: 'Licensed Georgia service-desk',
  homeBranch: 'turquoise parsing',
  servBranch: 'array Franc Michigan',
  homeState: 'generate Salad Avon',
  servState: 'Ameliorated end-to-end',
  gstExempted: 'efficient transmit',
  prefRepayMode: 'niches',
  tdsCode: 'protocol programming Borders',
  tdsRate: 'Internal',
  sanctionedAmount: 47708,
  originationApplnNo: 'Lesotho envisioneer Lead',
  cycleDay: 34768,
  tenor: 'Money Michigan',
  interestRate: 47607,
  repayFreq: RepaymentFreqency['QUARTERLY'],
  isDeleted: false,
  lastModified: dayjs('2022-09-28T18:04'),
  lastModifiedBy: 'Avon',
  freeField1: 'Computer object-oriented',
  freeField2: 'navigate hack Soap',
  freeField3: 'Rhode',
  freeField4: 'application primary Clothing',
};

export const sampleWithNewData: NewMasterAgreement = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
