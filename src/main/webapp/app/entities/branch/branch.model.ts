import dayjs from 'dayjs/esm';
import { IAddress } from 'app/entities/address/address.model';
import { IOrganisation } from 'app/entities/organisation/organisation.model';
import { IRegion } from 'app/entities/region/region.model';
import { BranchType } from 'app/entities/enumerations/branch-type.model';

export interface IBranch {
  id: number;
  branchName?: string | null;
  description?: string | null;
  branchcode?: string | null;
  ifscCode?: string | null;
  micrCode?: string | null;
  swiftCode?: string | null;
  ibanCode?: string | null;
  isHeadOffice?: boolean | null;
  routingTransitNo?: string | null;
  phone?: string | null;
  email?: string | null;
  webSite?: string | null;
  fax?: string | null;
  isActivate?: boolean | null;
  branchType?: BranchType | null;
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
  organisation?: Pick<IOrganisation, 'id'> | null;
  branch?: Pick<IRegion, 'id'> | null;
}

export type NewBranch = Omit<IBranch, 'id'> & { id: null };
