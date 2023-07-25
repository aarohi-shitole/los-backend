import dayjs from 'dayjs/esm';
import { IOrganisation } from 'app/entities/organisation/organisation.model';

export interface ISchemesDetails {
  id: number;
  fdDurationDays?: number | null;
  interest?: number | null;
  lockInPeriod?: number | null;
  schemeName?: string | null;
  rdDurationMonths?: number | null;
  reinvestInterest?: boolean | null;
  minAmount?: number | null;
  lastDayOfScheam?: dayjs.Dayjs | null;
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

export type NewSchemesDetails = Omit<ISchemesDetails, 'id'> & { id: null };
