import dayjs from 'dayjs/esm';
import { IState } from 'app/entities/state/state.model';

export interface IDistrict {
  id: number;
  districtName?: string | null;
  isDeleted?: boolean | null;
  lgdCode?: number | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  state?: Pick<IState, 'id'> | null;
}

export type NewDistrict = Omit<IDistrict, 'id'> & { id: null };
