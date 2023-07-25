import dayjs from 'dayjs/esm';
import { IAddress } from 'app/entities/address/address.model';
import { OrganisationType } from 'app/entities/enumerations/organisation-type.model';

export interface IOrganisation {
  id: number;
  orgName?: string | null;
  description?: string | null;
  regNumber?: string | null;
  gstin?: string | null;
  pan?: string | null;
  tan?: string | null;
  phone?: string | null;
  email?: string | null;
  webSite?: string | null;
  fax?: string | null;
  isActivate?: boolean | null;
  orgType?: OrganisationType | null;
  createdOn?: dayjs.Dayjs | null;
  createdBy?: string | null;
  isDeleted?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  freeField4?: string | null;
  freeField5?: string | null;
  address?: Pick<IAddress, 'id'> | null;
}

export type NewOrganisation = Omit<IOrganisation, 'id'> & { id: null };
