import dayjs from 'dayjs/esm';
import { IOrganisation } from 'app/entities/organisation/organisation.model';
import { NpaClassification } from 'app/entities/enumerations/npa-classification.model';

export interface INpaSetting {
  id: number;
  npaClassification?: NpaClassification | null;
  durationStart?: number | null;
  durationEnd?: number | null;
  provision?: string | null;
  year?: number | null;
  interestRate?: number | null;
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
  organisation?: Pick<IOrganisation, 'id'> | null;
}

export type NewNpaSetting = Omit<INpaSetting, 'id'> & { id: null };
