import dayjs from 'dayjs/esm';
import { IOrganisation } from 'app/entities/organisation/organisation.model';
import { DeclearationType } from 'app/entities/enumerations/declearation-type.model';

export interface IDeclearation {
  id: number;
  tiltle?: string | null;
  type?: DeclearationType | null;
  description?: string | null;
  tag?: string | null;
  subType?: number | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  isDeleted?: boolean | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  freeField4?: string | null;
  organisation?: Pick<IOrganisation, 'id'> | null;
}

export type NewDeclearation = Omit<IDeclearation, 'id'> & { id: null };
