import dayjs from 'dayjs/esm';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { ILoanApplications } from 'app/entities/loan-applications/loan-applications.model';

export interface IRemarkHistory {
  id: number;
  remark?: string | null;
  loanAmt?: number | null;
  modifiedAmt?: number | null;
  loanInterest?: number | null;
  modifiedInterest?: number | null;
  createdOn?: dayjs.Dayjs | null;
  createdBy?: string | null;
  branch?: string | null;
  applicationNo?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  freeField4?: string | null;
  freeField5?: string | null;
  freeField6?: string | null;
  securityUser?: Pick<ISecurityUser, 'id'> | null;
  loanApplications?: Pick<ILoanApplications, 'id'> | null;
}

export type NewRemarkHistory = Omit<IRemarkHistory, 'id'> & { id: null };
