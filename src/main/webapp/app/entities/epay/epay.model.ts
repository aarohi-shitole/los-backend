import dayjs from 'dayjs/esm';
import { RepaymentFreqency } from 'app/entities/enumerations/repayment-freqency.model';

export interface IEpay {
  id: number;
  instrumentType?: string | null;
  dtFromDate?: dayjs.Dayjs | null;
  dtToDate?: dayjs.Dayjs | null;
  bankCode?: string | null;
  bankBranchCode?: string | null;
  accountType?: string | null;
  accountNo?: string | null;
  maxCeilAmount?: number | null;
  installmentAmount?: number | null;
  maxInstallmentAmount?: number | null;
  mandateRefNo?: string | null;
  depositBank?: string | null;
  mandateType?: string | null;
  frequency?: RepaymentFreqency | null;
  ifscCode?: string | null;
  utrNo?: string | null;
  isDeleted?: boolean | null;
  isActive?: boolean | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  freeField4?: string | null;
}

export type NewEpay = Omit<IEpay, 'id'> & { id: null };
