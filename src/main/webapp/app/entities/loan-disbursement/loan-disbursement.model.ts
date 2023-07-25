import dayjs from 'dayjs/esm';
import { IProduct } from 'app/entities/product/product.model';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { ILoanAccount } from 'app/entities/loan-account/loan-account.model';
import { PaymentMode } from 'app/entities/enumerations/payment-mode.model';

export interface ILoanDisbursement {
  id: number;
  dtDisbDate?: dayjs.Dayjs | null;
  accountNo?: string | null;
  ifscCode?: string | null;
  disbAmount?: number | null;
  disbuNumber?: number | null;
  disbursementStatus?: string | null;
  paymentMode?: PaymentMode | null;
  utrNo?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  freeField4?: string | null;
  freeField5?: string | null;
  freeField6?: string | null;
  product?: Pick<IProduct, 'id'> | null;
  securityUser?: Pick<ISecurityUser, 'id'> | null;
  loanAccount?: Pick<ILoanAccount, 'id'> | null;
}

export type NewLoanDisbursement = Omit<ILoanDisbursement, 'id'> & { id: null };
