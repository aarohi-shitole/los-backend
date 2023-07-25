import dayjs from 'dayjs/esm';

export interface IMemberLimit {
  id: number;
  limitSanctionAmount?: number | null;
  dtLimitSanctioned?: dayjs.Dayjs | null;
  dtLimitExpired?: dayjs.Dayjs | null;
  purpose?: string | null;
  isDeleted?: boolean | null;
  isActive?: boolean | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  freeField4?: string | null;
}

export type NewMemberLimit = Omit<IMemberLimit, 'id'> & { id: null };
