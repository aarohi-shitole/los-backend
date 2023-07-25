import dayjs from 'dayjs/esm';

import { PaymentMode } from 'app/entities/enumerations/payment-mode.model';

import { ILoanRepaymentDetails, NewLoanRepaymentDetails } from './loan-repayment-details.model';

export const sampleWithRequiredData: ILoanRepaymentDetails = {
  id: 2660,
};

export const sampleWithPartialData: ILoanRepaymentDetails = {
  id: 95571,
  totalRepaymentAmt: 73667,
  principlePaidAmt: 52063,
  interestPaidAmt: 99799,
  surChargeAmt: 29074,
  foreClosureChargeAmt: 2880,
  lastModified: dayjs('2022-09-28T14:20'),
  lastModifiedBy: 'withdrawal out-of-the-box Cotton',
  freeField1: 'mission-critical payment',
  freeField2: 'Loan',
  freeField3: 'Massachusetts Iraq',
  freeField4: 'payment Guam payment',
  freeField5: 'deploy Credit Account',
};

export const sampleWithFullData: ILoanRepaymentDetails = {
  id: 98123,
  repaymentDate: dayjs('2022-09-28T11:12'),
  totalRepaymentAmt: 89791,
  installmentNumber: 62452,
  principlePaidAmt: 75128,
  interestPaidAmt: 13727,
  surChargeAmt: 20821,
  overDueAmt: 43707,
  balanceInterestAmt: 55444,
  balancePrincipleAmt: 59512,
  paymentMode: PaymentMode['TRANSFER'],
  foreClosureChargeAmt: 20886,
  lastModified: dayjs('2022-09-28T15:46'),
  lastModifiedBy: 'SMTP',
  freeField1: 'Hat Shoes Malaysian',
  freeField2: 'Visionary Circles payment',
  freeField3: 'cross-platform Soap',
  freeField4: 'Frozen',
  freeField5: 'feed',
  freeField6: 'Configuration Handmade',
};

export const sampleWithNewData: NewLoanRepaymentDetails = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
