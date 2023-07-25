import dayjs from 'dayjs/esm';
import { IState } from 'app/entities/state/state.model';
import { IDistrict } from 'app/entities/district/district.model';
import { ITaluka } from 'app/entities/taluka/taluka.model';
import { ICity } from 'app/entities/city/city.model';
import { IProduct } from 'app/entities/product/product.model';

export interface IEnquiryDetails {
  id: number;
  customerName?: string | null;
  subName?: string | null;
  purpose?: string | null;
  mobileNo?: string | null;
  amount?: number | null;
  refrenceNo?: string | null;
  isDeleted?: boolean | null;
  isActive?: boolean | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  freeField4?: string | null;
  state?: Pick<IState, 'id'> | null;
  district?: Pick<IDistrict, 'id'> | null;
  taluka?: Pick<ITaluka, 'id'> | null;
  city?: Pick<ICity, 'id'> | null;
  product?: Pick<IProduct, 'id'> | null;
}

export type NewEnquiryDetails = Omit<IEnquiryDetails, 'id'> & { id: null };
