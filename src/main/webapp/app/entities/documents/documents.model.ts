import dayjs from 'dayjs/esm';
import { IMember } from 'app/entities/member/member.model';
import { IGuarantor } from 'app/entities/guarantor/guarantor.model';

export interface IDocuments {
  id: number;
  docType?: string | null;
  docSubType?: string | null;
  tag?: string | null;
  fileName?: string | null;
  filePath?: string | null;
  fileUrl?: string | null;
  refrenceId?: number | null;
  hasVerified?: boolean | null;
  remark?: string | null;
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
  member?: Pick<IMember, 'id'> | null;
  guarantor?: Pick<IGuarantor, 'id'> | null;
}

export type NewDocuments = Omit<IDocuments, 'id'> & { id: null };
