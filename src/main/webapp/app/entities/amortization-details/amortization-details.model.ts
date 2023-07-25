import dayjs from 'dayjs/esm';
import { ILoanAccount } from 'app/entities/loan-account/loan-account.model';

export interface IAmortizationDetails {
  id: number;
  installmentDate?: dayjs.Dayjs | null;
  totalOutstandingPrincipleAmt?: number | null;
  totalOutstandngInterestAmt?: number | null;
  paidPrincipleAmt?: number | null;
  paidInterestAmt?: number | null;
  balPrincipleAmt?: number | null;
  balInterestAmt?: number | null;
  loanEmiAmt?: number | null;
  principleEMI?: number | null;
  roi?: number | null;
  paymentStatus?: string | null;
  year?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  freeField4?: string | null;
  freeField5?: string | null;
  freeField6?: string | null;
  loanAccount?: Pick<ILoanAccount, 'id'> | null;
}

export type NewAmortizationDetails = Omit<IAmortizationDetails, 'id'> & { id: null };
