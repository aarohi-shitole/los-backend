import dayjs from 'dayjs/esm';
import { IMember } from 'app/entities/member/member.model';
import { FacilityStatus } from 'app/entities/enumerations/facility-status.model';
import { CreditRating } from 'app/entities/enumerations/credit-rating.model';

export interface IMemberExistingfacility {
  id: number;
  year?: number | null;
  facilityName?: string | null;
  facilitatedFrom?: string | null;
  nature?: string | null;
  amtInLac?: number | null;
  purpose?: string | null;
  sectionDate?: dayjs.Dayjs | null;
  outstandingInLac?: number | null;
  status?: FacilityStatus | null;
  rating?: CreditRating | null;
  isDeleted?: boolean | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  freeField4?: string | null;
  freeField5?: string | null;
  freeField6?: string | null;
  member?: Pick<IMember, 'id'> | null;
}

export type NewMemberExistingfacility = Omit<IMemberExistingfacility, 'id'> & { id: null };
