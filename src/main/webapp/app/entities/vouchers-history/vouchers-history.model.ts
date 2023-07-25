import dayjs from 'dayjs/esm';
import { VoucherCode } from 'app/entities/enumerations/voucher-code.model';

export interface IVouchersHistory {
  id: number;
  createdOn?: dayjs.Dayjs | null;
  createdBy?: string | null;
  voucherDate?: dayjs.Dayjs | null;
  code?: VoucherCode | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  freeField4?: string | null;
  freeField5?: string | null;
  freeField6?: string | null;
}

export type NewVouchersHistory = Omit<IVouchersHistory, 'id'> & { id: null };
