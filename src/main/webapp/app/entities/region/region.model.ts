import dayjs from 'dayjs/esm';
import { IOrganisation } from 'app/entities/organisation/organisation.model';

export interface IRegion {
  id: number;
  regionName?: string | null;
  isDeleted?: boolean | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  freeField4?: string | null;
  freeField5?: string | null;
  organisation?: Pick<IOrganisation, 'id'> | null;
}

export type NewRegion = Omit<IRegion, 'id'> & { id: null };
