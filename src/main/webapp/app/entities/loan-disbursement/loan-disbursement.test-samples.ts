import dayjs from 'dayjs/esm';

import { PaymentMode } from 'app/entities/enumerations/payment-mode.model';

import { ILoanDisbursement, NewLoanDisbursement } from './loan-disbursement.model';

export const sampleWithRequiredData: ILoanDisbursement = {
  id: 89028,
};

export const sampleWithPartialData: ILoanDisbursement = {
  id: 41858,
  accountNo: 'Small Multi-tiered',
  ifscCode: 'envisioneer',
  disbursementStatus: 'Concrete Practical Avon',
  paymentMode: PaymentMode['TRANSFER'],
  lastModified: dayjs('2022-09-28T22:27'),
  freeField2: 'Planner',
  freeField3: 'synthesizing',
  freeField5: 'Quality',
};

export const sampleWithFullData: ILoanDisbursement = {
  id: 79621,
  dtDisbDate: dayjs('2022-09-28T10:37'),
  accountNo: 'Colorado Flat Trail',
  ifscCode: 'payment digital Principal',
  disbAmount: 31389,
  disbuNumber: 40421,
  disbursementStatus: 'system back-end',
  paymentMode: PaymentMode['ONLINE'],
  utrNo: 'cross-platform',
  lastModified: dayjs('2022-09-28T12:05'),
  lastModifiedBy: 'administration navigate parsing',
  freeField1: 'TCP',
  freeField2: 'holistic Fish Practical',
  freeField3: 'bandwidth Market multimedia',
  freeField4: 'Outdoors Macao',
  freeField5: 'Baby',
  freeField6: 'JBOD local flexibility',
};

export const sampleWithNewData: NewLoanDisbursement = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
