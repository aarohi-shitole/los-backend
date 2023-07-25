import dayjs from 'dayjs/esm';
import { ITaluka } from 'app/entities/taluka/taluka.model';

export interface ICity {
  id: number;
  cityName?: string | null;
  isDeleted?: boolean | null;
  lgdCode?: number | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  taluka?: Pick<ITaluka, 'id'> | null;
}

export type NewCity = Omit<ICity, 'id'> & { id: null };
