import dayjs from 'dayjs/esm';
import { ILoanAccount } from 'app/entities/loan-account/loan-account.model';
import { PaymentMode } from 'app/entities/enumerations/payment-mode.model';

export interface ILoanRepaymentDetails {
  id: number;
  repaymentDate?: dayjs.Dayjs | null;
  totalRepaymentAmt?: number | null;
  installmentNumber?: number | null;
  principlePaidAmt?: number | null;
  interestPaidAmt?: number | null;
  surChargeAmt?: number | null;
  overDueAmt?: number | null;
  balanceInterestAmt?: number | null;
  balancePrincipleAmt?: number | null;
  paymentMode?: PaymentMode | null;
  foreClosureChargeAmt?: number | null;
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

export type NewLoanRepaymentDetails = Omit<ILoanRepaymentDetails, 'id'> & { id: null };
