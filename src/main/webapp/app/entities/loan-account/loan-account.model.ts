import dayjs from 'dayjs/esm';
import { ILoanApplications } from 'app/entities/loan-applications/loan-applications.model';
import { IMember } from 'app/entities/member/member.model';
import { IProduct } from 'app/entities/product/product.model';
import { LoanStatus } from 'app/entities/enumerations/loan-status.model';
import { NpaClassification } from 'app/entities/enumerations/npa-classification.model';

export interface ILoanAccount {
  id: number;
  loanAmount?: number | null;
  applicationNo?: string | null;
  status?: LoanStatus | null;
  loanStartDate?: dayjs.Dayjs | null;
  loanEndDate?: dayjs.Dayjs | null;
  loanPlannedClosureDate?: dayjs.Dayjs | null;
  loanCloserDate?: dayjs.Dayjs | null;
  emiStartDate?: dayjs.Dayjs | null;
  loanNpaClass?: NpaClassification | null;
  parentAccHeadCode?: string | null;
  loanAccountName?: string | null;
  disbursementAmt?: number | null;
  disbursementStatus?: string | null;
  repaymentPeriod?: string | null;
  year?: string | null;
  processingFee?: number | null;
  moratorium?: string | null;
  roi?: number | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  freeField4?: string | null;
  freeField5?: string | null;
  freeField6?: string | null;
  loanApplications?: Pick<ILoanApplications, 'id'> | null;
  member?: Pick<IMember, 'id'> | null;
  product?: Pick<IProduct, 'id'> | null;
}

export type NewLoanAccount = Omit<ILoanAccount, 'id'> & { id: null };
