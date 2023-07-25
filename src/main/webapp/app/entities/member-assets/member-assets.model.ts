import dayjs from 'dayjs/esm';
import { IMember } from 'app/entities/member/member.model';
import { ILoanApplications } from 'app/entities/loan-applications/loan-applications.model';
import { AssetType } from 'app/entities/enumerations/asset-type.model';
import { AssetNature } from 'app/entities/enumerations/asset-nature.model';

export interface IMemberAssets {
  id: number;
  assetCost?: number | null;
  assetType?: AssetType | null;
  areaInSqFt?: number | null;
  assetNature?: AssetNature | null;
  address?: string | null;
  landMark?: string | null;
  assetOwner?: string | null;
  completionYear?: string | null;
  marketValue?: number | null;
  isInsured?: boolean | null;
  isUnderUsed?: boolean | null;
  isOwned?: boolean | null;
  landType?: string | null;
  landGatNo?: string | null;
  landAreaInHector?: number | null;
  jindagiPatrakNo?: string | null;
  jindagiAmount?: number | null;
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
  freeField6?: string | null;
  member?: Pick<IMember, 'id'> | null;
  loanApplications?: Pick<ILoanApplications, 'id'> | null;
}

export type NewMemberAssets = Omit<IMemberAssets, 'id'> & { id: null };
