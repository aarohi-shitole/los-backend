import dayjs from 'dayjs/esm';

import { IAmortizationDetails, NewAmortizationDetails } from './amortization-details.model';

export const sampleWithRequiredData: IAmortizationDetails = {
  id: 48347,
};

export const sampleWithPartialData: IAmortizationDetails = {
  id: 5843,
  totalOutstandngInterestAmt: 59739,
  paidPrincipleAmt: 3034,
  paidInterestAmt: 74371,
  balPrincipleAmt: 64379,
  balInterestAmt: 37159,
  loanEmiAmt: 13980,
  principleEMI: 16087,
  roi: 27722,
  year: 'intranet',
  lastModifiedBy: 'Officer Functionality',
  freeField3: 'Sum Checking',
  freeField4: 'Krona',
  freeField6: 'Paradigm Data',
};

export const sampleWithFullData: IAmortizationDetails = {
  id: 94127,
  installmentDate: dayjs('2022-09-29T02:26'),
  totalOutstandingPrincipleAmt: 53952,
  totalOutstandngInterestAmt: 94743,
  paidPrincipleAmt: 11659,
  paidInterestAmt: 87398,
  balPrincipleAmt: 97157,
  balInterestAmt: 21695,
  loanEmiAmt: 82755,
  principleEMI: 10388,
  roi: 74997,
  paymentStatus: 'Grocery paradigm',
  year: 'Facilitator',
  lastModified: dayjs('2022-09-28T17:27'),
  lastModifiedBy: 'Cotton',
  freeField1: 'Ergonomic standardization hacking',
  freeField2: 'blue Kentucky driver',
  freeField3: 'Account collaborative',
  freeField4: 'Bedfordshire port',
  freeField5: 'infrastructure salmon payment',
  freeField6: 'Account',
};

export const sampleWithNewData: NewAmortizationDetails = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
