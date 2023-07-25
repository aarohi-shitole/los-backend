import dayjs from 'dayjs/esm';
import { IMember } from 'app/entities/member/member.model';

export interface IMemberBank {
  id: number;
  bankName?: string | null;
  branchName?: string | null;
  accountNumber?: number | null;
  accHolderName?: string | null;
  ifsccode?: string | null;
  micrCode?: string | null;
  swiftCode?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  isActive?: boolean | null;
  isDeleted?: boolean | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  freeField4?: string | null;
  freeField5?: string | null;
  freeField6?: string | null;
  member?: Pick<IMember, 'id'> | null;
}

export type NewMemberBank = Omit<IMemberBank, 'id'> & { id: null };
