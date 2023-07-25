import dayjs from 'dayjs/esm';
import { RepaymentFreqency } from 'app/entities/enumerations/repayment-freqency.model';

export interface IMasterAgreement {
  id: number;
  memberId?: string | null;
  portfolioCode?: string | null;
  productCode?: string | null;
  homeBranch?: string | null;
  servBranch?: string | null;
  homeState?: string | null;
  servState?: string | null;
  gstExempted?: string | null;
  prefRepayMode?: string | null;
  tdsCode?: string | null;
  tdsRate?: string | null;
  sanctionedAmount?: number | null;
  originationApplnNo?: string | null;
  cycleDay?: number | null;
  tenor?: string | null;
  interestRate?: number | null;
  repayFreq?: RepaymentFreqency | null;
  isDeleted?: boolean | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  freeField4?: string | null;
}

export type NewMasterAgreement = Omit<IMasterAgreement, 'id'> & { id: null };
