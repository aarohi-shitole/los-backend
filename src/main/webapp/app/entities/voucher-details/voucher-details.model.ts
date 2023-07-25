import dayjs from 'dayjs/esm';

export interface IVoucherDetails {
  id: number;
  accHeadCode?: string | null;
  creditAccount?: string | null;
  debitAccount?: string | null;
  transferAmt?: number | null;
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

export type NewVoucherDetails = Omit<IVoucherDetails, 'id'> & { id: null };
