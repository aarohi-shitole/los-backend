import dayjs from 'dayjs/esm';

import { LoanStatus } from 'app/entities/enumerations/loan-status.model';
import { NpaClassification } from 'app/entities/enumerations/npa-classification.model';

import { ILoanAccount, NewLoanAccount } from './loan-account.model';

export const sampleWithRequiredData: ILoanAccount = {
  id: 3977,
};

export const sampleWithPartialData: ILoanAccount = {
  id: 24260,
  loanCloserDate: dayjs('2022-09-28T12:28'),
  loanNpaClass: NpaClassification['SUB_STANDARD'],
  parentAccHeadCode: 'wireless',
  disbursementAmt: 19133,
  disbursementStatus: 'strategic Soap panel',
  moratorium: 'Leu California Metrics',
  freeField2: 'Berkshire',
  freeField5: 'Liaison communities Michigan',
};

export const sampleWithFullData: ILoanAccount = {
  id: 30681,
  loanAmount: 65129,
  applicationNo: 'Account capability',
  status: LoanStatus['PENDING'],
  loanStartDate: dayjs('2022-09-28T17:53'),
  loanEndDate: dayjs('2022-09-29T06:01'),
  loanPlannedClosureDate: dayjs('2022-09-28T18:05'),
  loanCloserDate: dayjs('2022-09-28T23:48'),
  emiStartDate: dayjs('2022-09-29T00:30'),
  loanNpaClass: NpaClassification['DOUBTFUL_1'],
  parentAccHeadCode: 'plum Loan',
  loanAccountName: 'redefine Brazil TCP',
  disbursementAmt: 87856,
  disbursementStatus: 'Japan',
  repaymentPeriod: 'Vatu UIC-Franc experiences',
  year: 'Nevada Tennessee',
  processingFee: 38943,
  moratorium: 'Plastic virtual salmon',
  roi: 87521,
  lastModified: dayjs('2022-09-28T20:03'),
  lastModifiedBy: 'Car implement Savings',
  freeField1: 'Principal e-services',
  freeField2: 'global Designer Frozen',
  freeField3: 'executive Minnesota array',
  freeField4: 'Ergonomic Senior',
  freeField5: 'Loan SDD',
  freeField6: 'hack Kyat',
};

export const sampleWithNewData: NewLoanAccount = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
