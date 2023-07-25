import dayjs from 'dayjs/esm';

import { VoucherCode } from 'app/entities/enumerations/voucher-code.model';

import { IVouchersHistory, NewVouchersHistory } from './vouchers-history.model';

export const sampleWithRequiredData: IVouchersHistory = {
  id: 84975,
};

export const sampleWithPartialData: IVouchersHistory = {
  id: 64298,
  createdOn: dayjs('2022-09-28T23:35'),
  code: VoucherCode['EXPENSE'],
  freeField2: 'Central Missouri payment',
  freeField3: 'bluetooth',
  freeField4: 'Investment SCSI Solutions',
  freeField5: 'Rupee',
};

export const sampleWithFullData: IVouchersHistory = {
  id: 47701,
  createdOn: dayjs('2022-09-28T19:23'),
  createdBy: 'SMS platforms',
  voucherDate: dayjs('2022-09-29T02:09'),
  code: VoucherCode['SAVINGS'],
  freeField1: 'Frozen',
  freeField2: 'JSON Meadows',
  freeField3: 'Shirt Angola calculating',
  freeField4: 'optimize Salvador Account',
  freeField5: 'Managed',
  freeField6: 'Rustic Rubber',
};

export const sampleWithNewData: NewVouchersHistory = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
