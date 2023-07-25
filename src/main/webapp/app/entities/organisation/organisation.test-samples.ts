import dayjs from 'dayjs/esm';

import { OrganisationType } from 'app/entities/enumerations/organisation-type.model';

import { IOrganisation, NewOrganisation } from './organisation.model';

export const sampleWithRequiredData: IOrganisation = {
  id: 2172,
  orgName: 'Associate Grocery XML',
  regNumber: 'Planner blue Pizza',
  gstin: 'groupware Cambridgeshire Generic',
};

export const sampleWithPartialData: IOrganisation = {
  id: 61936,
  orgName: 'Buckinghamshire well-modulated Pizza',
  description: 'microchip',
  regNumber: 'Checking Dalasi',
  gstin: 'Function-based',
  pan: 'Planner Dynamic Producer',
  phone: '(377) 754-0148 x9540',
  email: 'Scottie.Cremin90@yahoo.com',
  webSite: 'Rubber',
  isActivate: true,
  createdOn: dayjs('2022-09-28T16:57'),
  isDeleted: 'tolerance',
  lastModified: dayjs('2022-09-28T20:16'),
  freeField1: 'Leone Lanka Berkshire',
  freeField2: 'blockchains AGP',
  freeField3: 'pixel Peso',
};

export const sampleWithFullData: IOrganisation = {
  id: 98624,
  orgName: 'Cedi gold 5th',
  description: 'Darussalam multi-byte withdrawal',
  regNumber: 'generating Dollar) Silver',
  gstin: 'stable brand robust',
  pan: 'application',
  tan: 'grey Federation',
  phone: '1-608-733-3934 x03115',
  email: 'Sheila_Hilpert71@gmail.com',
  webSite: 'Account Specialist open-source',
  fax: 'withdrawal',
  isActivate: true,
  orgType: OrganisationType['URBAN_BANK'],
  createdOn: dayjs('2022-09-28T10:39'),
  createdBy: 'rich',
  isDeleted: 'mobile Senegal Legacy',
  lastModified: dayjs('2022-09-28T12:36'),
  lastModifiedBy: 'Granite architectures',
  freeField1: 'SMS',
  freeField2: 'Chicken',
  freeField3: 'schemas Manager Savings',
  freeField4: 'mission-critical North',
  freeField5: 'Rubber architect',
};

export const sampleWithNewData: NewOrganisation = {
  orgName: 'Internal Frozen',
  regNumber: 'reboot Future',
  gstin: 'Indonesia Borders circuit',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
