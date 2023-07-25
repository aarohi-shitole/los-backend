import dayjs from 'dayjs/esm';

import { AssetType } from 'app/entities/enumerations/asset-type.model';
import { AssetNature } from 'app/entities/enumerations/asset-nature.model';

import { IMemberAssets, NewMemberAssets } from './member-assets.model';

export const sampleWithRequiredData: IMemberAssets = {
  id: 35816,
};

export const sampleWithPartialData: IMemberAssets = {
  id: 6650,
  assetCost: 93863,
  areaInSqFt: 2773,
  address: 'generating',
  marketValue: 95549,
  isInsured: true,
  landType: 'Rupiah',
  landAreaInHector: 36302,
  jindagiPatrakNo: 'Nebraska Southern',
  createdBy: '1080p hacking',
  createdOn: dayjs('2022-09-28T18:29'),
  isDeleted: true,
  freeField2: 'convergence withdrawal',
  freeField4: 'Creek partnerships',
  freeField5: 'encompassing Web Portugal',
};

export const sampleWithFullData: IMemberAssets = {
  id: 50374,
  assetCost: 52026,
  assetType: AssetType['INVESTMENT'],
  areaInSqFt: 72636,
  assetNature: AssetNature['OTHER'],
  address: 'Cedi multi-byte Regional',
  landMark: 'B2C',
  assetOwner: 'withdrawal Cuba',
  completionYear: 'orchestration Branding Mozambique',
  marketValue: 43324,
  isInsured: true,
  isUnderUsed: true,
  isOwned: false,
  landType: 'firmware Communications',
  landGatNo: 'Rubber',
  landAreaInHector: 47698,
  jindagiPatrakNo: 'robust',
  jindagiAmount: 77231,
  lastModified: dayjs('2022-09-28T10:06'),
  lastModifiedBy: 'Refined',
  createdBy: 'systems',
  createdOn: dayjs('2022-09-29T05:15'),
  isDeleted: false,
  freeField1: 'Soft transparent',
  freeField2: 'Metal Persistent',
  freeField3: 'Executive New users',
  freeField4: 'access',
  freeField5: 'connect',
  freeField6: 'Handmade Somoni Generic',
};

export const sampleWithNewData: NewMemberAssets = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
