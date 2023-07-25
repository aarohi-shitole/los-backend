import dayjs from 'dayjs/esm';
import { IState } from 'app/entities/state/state.model';
import { IDistrict } from 'app/entities/district/district.model';
import { ITaluka } from 'app/entities/taluka/taluka.model';
import { ICity } from 'app/entities/city/city.model';
import { IMember } from 'app/entities/member/member.model';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { AddressType } from 'app/entities/enumerations/address-type.model';

export interface IAddress {
  id: number;
  addrStatus?: AddressType | null;
  addressLine1?: string | null;
  addressLine2?: string | null;
  addressLine3?: string | null;
  landMark?: string | null;
  pincode?: string | null;
  longitude?: number | null;
  latitude?: number | null;
  mapNevUrl?: string | null;
  isPrefferedAdd?: boolean | null;
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
  state?: Pick<IState, 'id'> | null;
  district?: Pick<IDistrict, 'id'> | null;
  taluka?: Pick<ITaluka, 'id'> | null;
  city?: Pick<ICity, 'id'> | null;
  member?: Pick<IMember, 'id'> | null;
  securityUser?: Pick<ISecurityUser, 'id'> | null;
}

export type NewAddress = Omit<IAddress, 'id'> & { id: null };
