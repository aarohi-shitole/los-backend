import dayjs from 'dayjs/esm';
import { IMember } from 'app/entities/member/member.model';
import { IncomeType } from 'app/entities/enumerations/income-type.model';

export interface IIncomeDetails {
  id: number;
  year?: string | null;
  grossIncome?: number | null;
  expenses?: number | null;
  netIncome?: number | null;
  paidTaxes?: number | null;
  cashSurplus?: number | null;
  incomeType?: IncomeType | null;
  isDeleted?: boolean | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  freeField4?: string | null;
  freeField5?: string | null;
  freeField6?: string | null;
  member?: Pick<IMember, 'id'> | null;
}

export type NewIncomeDetails = Omit<IIncomeDetails, 'id'> & { id: null };
