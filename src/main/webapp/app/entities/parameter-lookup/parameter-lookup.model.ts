import dayjs from 'dayjs/esm';
import { IOrganisation } from 'app/entities/organisation/organisation.model';
import { ParameterLookupType } from 'app/entities/enumerations/parameter-lookup-type.model';

export interface IParameterLookup {
  id: number;
  type?: ParameterLookupType | null;
  name?: string | null;
  description?: string | null;
  value?: string | null;
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

export type NewParameterLookup = Omit<IParameterLookup, 'id'> & { id: null };
