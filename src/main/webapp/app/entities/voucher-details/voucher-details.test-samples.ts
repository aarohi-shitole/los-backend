import dayjs from 'dayjs/esm';

import { IVoucherDetails, NewVoucherDetails } from './voucher-details.model';

export const sampleWithRequiredData: IVoucherDetails = {
  id: 66695,
};

export const sampleWithPartialData: IVoucherDetails = {
  id: 78662,
  accHeadCode: 'Savings Beauty standardization',
  transferAmt: 69254,
  freeField2: 'niches Dollar Dakota',
  freeField3: 'actuating Money',
  freeField5: 'Rubber Representative',
  freeField6: 'encoding',
};

export const sampleWithFullData: IVoucherDetails = {
  id: 41254,
  accHeadCode: 'Mills',
  creditAccount: 'innovative Hawaii',
  debitAccount: 'Computer expedite payment',
  transferAmt: 93836,
  isDeleted: false,
  lastModified: dayjs('2022-09-28T10:04'),
  lastModifiedBy: 'Concrete Kentucky Jersey',
  freeField1: 'neural',
  freeField2: 'alarm',
  freeField3: 'Bahamas Wooden',
  freeField4: 'Jordanian Belgium',
  freeField5: 'Pants rich',
  freeField6: 'solid Vietnam',
};

export const sampleWithNewData: NewVoucherDetails = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
