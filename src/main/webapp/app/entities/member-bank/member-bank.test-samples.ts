import dayjs from 'dayjs/esm';

import { IMemberBank, NewMemberBank } from './member-bank.model';

export const sampleWithRequiredData: IMemberBank = {
  id: 24673,
};

export const sampleWithPartialData: IMemberBank = {
  id: 11722,
  accHolderName: 'generate',
  ifsccode: 'Security hacking',
  swiftCode: 'Communications Land Dollar',
  lastModified: dayjs('2022-09-29T01:52'),
  lastModifiedBy: 'Fish cross-platform Bedfordshire',
  createdBy: 'Ball Algerian Facilitator',
  createdOn: dayjs('2022-09-28T19:51'),
  isDeleted: true,
  freeField1: 'intuitive Concrete Eritrea',
  freeField2: 'Chips',
  freeField3: 'Producer',
  freeField4: 'override generating Bedfordshire',
  freeField5: 'deliverables',
  freeField6: 'Handmade',
};

export const sampleWithFullData: IMemberBank = {
  id: 26091,
  bankName: 'schemas',
  branchName: 'compelling Savings Keyboard',
  accountNumber: 23202,
  accHolderName: 'facilitate port',
  ifsccode: 'National Shoes',
  micrCode: 'Direct well-modulated',
  swiftCode: 'Manager International index',
  lastModified: dayjs('2022-09-28T19:13'),
  lastModifiedBy: 'Implementation',
  createdBy: 'vortals',
  createdOn: dayjs('2022-09-28T15:37'),
  isActive: true,
  isDeleted: false,
  freeField1: 'Flats Texas',
  freeField2: 'Fresh',
  freeField3: 'directional Rufiyaa Mouse',
  freeField4: 'Communications Borders',
  freeField5: 'empower calculate Botswana',
  freeField6: 'plug-and-play bypassing',
};

export const sampleWithNewData: NewMemberBank = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
