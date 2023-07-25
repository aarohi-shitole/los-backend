import dayjs from 'dayjs/esm';
import { IProduct } from 'app/entities/product/product.model';
import { InterestType } from 'app/entities/enumerations/interest-type.model';
import { RepaymentFreqency } from 'app/entities/enumerations/repayment-freqency.model';

export interface IInterestConfig {
  id: number;
  startDate?: dayjs.Dayjs | null;
  endDate?: dayjs.Dayjs | null;
  interestBasis?: string | null;
  emiBasis?: string | null;
  interestType?: InterestType | null;
  intrAccrualFreq?: RepaymentFreqency | null;
  penalInterestRate?: number | null;
  penalInterestBasis?: string | null;
  penalAccountingBasis?: string | null;
  minInterestRate?: number | null;
  maxInterestRate?: number | null;
  extendedInterestRate?: number | null;
  surchargeRate?: number | null;
  isDeleted?: boolean | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  freeField4?: string | null;
  freeField5?: string | null;
  freeField6?: string | null;
  product?: Pick<IProduct, 'id'> | null;
}

export type NewInterestConfig = Omit<IInterestConfig, 'id'> & { id: null };
