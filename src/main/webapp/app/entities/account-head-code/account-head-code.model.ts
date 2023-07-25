import dayjs from 'dayjs/esm';
import { ILedgerAccounts } from 'app/entities/ledger-accounts/ledger-accounts.model';
import { MappingType } from 'app/entities/enumerations/mapping-type.model';

export interface IAccountHeadCode {
  id: number;
  type?: MappingType | null;
  value?: string | null;
  headCodeName?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  isDeleted?: boolean | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  freeField4?: string | null;
  freeField5?: string | null;
  ledgerAccounts?: Pick<ILedgerAccounts, 'id'> | null;
}

export type NewAccountHeadCode = Omit<IAccountHeadCode, 'id'> & { id: null };
