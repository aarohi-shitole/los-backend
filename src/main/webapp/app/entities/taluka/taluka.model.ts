import dayjs from 'dayjs/esm';
import { IDistrict } from 'app/entities/district/district.model';

export interface ITaluka {
  id: number;
  talukaName?: string | null;
  isDeleted?: boolean | null;
  lgdCode?: number | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  district?: Pick<IDistrict, 'id'> | null;
}

export type NewTaluka = Omit<ITaluka, 'id'> & { id: null };
