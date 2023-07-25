import dayjs from 'dayjs/esm';
import { VoucherCode } from 'app/entities/enumerations/voucher-code.model';
import { PaymentMode } from 'app/entities/enumerations/payment-mode.model';

export interface IVouchers {
  id: number;
  voucherDate?: dayjs.Dayjs | null;
  voucherNo?: string | null;
  preparedBy?: string | null;
  code?: VoucherCode | null;
  narration?: string | null;
  authorisedBy?: string | null;
  mode?: PaymentMode | null;
  isDeleted?: boolean | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  freeField4?: string | null;
  freeField5?: string | null;
  freeField6?: string | null;
}

export type NewVouchers = Omit<IVouchers, 'id'> & { id: null };
