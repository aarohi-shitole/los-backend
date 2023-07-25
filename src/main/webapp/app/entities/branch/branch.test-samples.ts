import dayjs from 'dayjs/esm';

import { BranchType } from 'app/entities/enumerations/branch-type.model';

import { IBranch, NewBranch } from './branch.model';

export const sampleWithRequiredData: IBranch = {
  id: 244,
  branchName: 'blue hack Intelligent',
};

export const sampleWithPartialData: IBranch = {
  id: 94584,
  branchName: 'Center Pants matrix',
  description: 'Dram Seamless microchip',
  ibanCode: 'Point integrated',
  phone: '617-786-3639 x22738',
  email: 'Alvera21@gmail.com',
  branchType: BranchType['BRANCH'],
  createdOn: dayjs('2022-09-28T19:46'),
  isDeleted: 'invoice protocol',
  lastModifiedBy: 'Junctions',
  freeField1: 'Savings Buckinghamshire',
  freeField2: 'Car copying',
  freeField4: 'Music payment Handmade',
  freeField5: 'Solutions',
};

export const sampleWithFullData: IBranch = {
  id: 25469,
  branchName: 'ROI Buckinghamshire',
  description: 'Cheese',
  branchcode: 'leverage payment Orchestrator',
  ifscCode: 'Borders panel',
  micrCode: 'Incredible SAS world-class',
  swiftCode: 'Director',
  ibanCode: 'web silver',
  isHeadOffice: false,
  routingTransitNo: 'systemic',
  phone: '902-748-9290 x278',
  email: 'Dedrick36@yahoo.com',
  webSite: 'Shilling Organized',
  fax: 'Berkshire',
  isActivate: true,
  branchType: BranchType['BRANCH'],
  createdOn: dayjs('2022-09-28T17:11'),
  createdBy: 'Tenge HTTP Gorgeous',
  isDeleted: 'Pants',
  lastModified: dayjs('2022-09-28T11:23'),
  lastModifiedBy: 'Intelligent',
  freeField1: 'Malawi Sleek Hollow',
  freeField2: 'magenta',
  freeField3: 'up e-business Infrastructure',
  freeField4: 'HDD Response',
  freeField5: 'Toys Jordanian',
};

export const sampleWithNewData: NewBranch = {
  branchName: 'Verde Garden',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
