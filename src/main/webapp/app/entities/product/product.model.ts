import dayjs from 'dayjs/esm';
import { ILoanCatagory } from 'app/entities/loan-catagory/loan-catagory.model';
import { IOrganisation } from 'app/entities/organisation/organisation.model';
import { ILedgerAccounts } from 'app/entities/ledger-accounts/ledger-accounts.model';

export interface IProduct {
  id: number;
  prodCode?: string | null;
  prodName?: string | null;
  bpiTreatmentFlag?: string | null;
  amortizationMethod?: string | null;
  amortizationType?: string | null;
  compoundingFreq?: string | null;
  emiRounding?: string | null;
  lastRowEMIThreshold?: number | null;
  graceDays?: number | null;
  reschLockinPeriod?: number | null;
  prepayAfterInstNo?: number | null;
  prepayBeforeInstNo?: number | null;
  minInstallmentGapBetPrepay?: number | null;
  minPrepayAmount?: number | null;
  forecloseAfterInstNo?: number | null;
  forecloseBeforeInstaNo?: number | null;
  minTenor?: number | null;
  maxTenor?: number | null;
  minInstallmentAmount?: number | null;
  maxInstallmentAmount?: number | null;
  dropLineAmount?: number | null;
  dropLineODYN?: string | null;
  dropLinePerc?: number | null;
  dropMode?: string | null;
  dropLIneFreq?: string | null;
  dropLineCycleDay?: number | null;
  roi?: number | null;
  isDeleted?: boolean | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  freeField4?: string | null;
  freeField5?: string | null;
  freeField6?: string | null;
  loanCatagory?: Pick<ILoanCatagory, 'id'> | null;
  organisation?: Pick<IOrganisation, 'id'> | null;
  ledgerAccounts?: Pick<ILedgerAccounts, 'id'> | null;
}

export type NewProduct = Omit<IProduct, 'id'> & { id: null };
