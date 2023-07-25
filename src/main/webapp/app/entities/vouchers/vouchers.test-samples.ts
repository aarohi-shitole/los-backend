import dayjs from 'dayjs/esm';

import { VoucherCode } from 'app/entities/enumerations/voucher-code.model';
import { PaymentMode } from 'app/entities/enumerations/payment-mode.model';

import { IVouchers, NewVouchers } from './vouchers.model';

export const sampleWithRequiredData: IVouchers = {
  id: 36341,
};

export const sampleWithPartialData: IVouchers = {
  id: 6628,
  code: VoucherCode['INVESTMENT'],
  mode: PaymentMode['TRANSFER'],
  lastModified: dayjs('2022-09-28T20:21'),
  lastModifiedBy: 'interactive',
  freeField2: 'SDD wireless',
  freeField3: 'Ways Future',
  freeField6: 'online Multi-tiered',
};

export const sampleWithFullData: IVouchers = {
  id: 44247,
  voucherDate: dayjs('2022-09-28T09:10'),
  voucherNo: 'withdrawal Customer',
  preparedBy: 'Designer Personal',
  code: VoucherCode['PURCHASE'],
  narration: 'up',
  authorisedBy: 'Cambridgeshire Sleek ROI',
  mode: PaymentMode['ONLINE'],
  isDeleted: false,
  lastModified: dayjs('2022-09-29T03:22'),
  lastModifiedBy: 'Reduced District Health',
  freeField1: 'port compelling',
  freeField2: 'Licensed Clothing cross-platform',
  freeField3: 'focus',
  freeField4: 'Pound',
  freeField5: 'Metal',
  freeField6: 'back-end Metal',
};

export const sampleWithNewData: NewVouchers = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
