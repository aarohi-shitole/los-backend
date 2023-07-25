import dayjs from 'dayjs/esm';
import { IProduct } from 'app/entities/product/product.model';
import { IOrganisation } from 'app/entities/organisation/organisation.model';

export interface IOrgPrerequisiteDoc {
  id: number;
  docDesc?: string | null;
  docName?: string | null;
  docCatagory?: string | null;
  ismandatory?: boolean | null;
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
  product?: Pick<IProduct, 'id'> | null;
  organisation?: Pick<IOrganisation, 'id'> | null;
}

export type NewOrgPrerequisiteDoc = Omit<IOrgPrerequisiteDoc, 'id'> & { id: null };
