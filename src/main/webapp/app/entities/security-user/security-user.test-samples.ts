import dayjs from 'dayjs/esm';

import { ISecurityUser, NewSecurityUser } from './security-user.model';

export const sampleWithRequiredData: ISecurityUser = {
  id: 67981,
  username: 'panel',
  passwordHash: 'Massachusetts',
};

export const sampleWithPartialData: ISecurityUser = {
  id: 69570,
  firstName: 'Theodore',
  lastName: 'Robel',
  designation: 'Loan Pound',
  username: 'Visionary RSS even-keeled',
  passwordHash: 'Hawaii Kansas',
  email: 'Lesly.Mohr@gmail.com',
  description: 'purple',
  resetKey: 'magnetic',
  resetDate: dayjs('2022-09-28T18:14'),
  createdBy: 'SAS',
  freeField1: 'Plastic New copy',
  freeField2: 'Keyboard Kids',
  freeField3: 'Corporate',
};

export const sampleWithFullData: ISecurityUser = {
  id: 12779,
  firstName: 'Chesley',
  lastName: 'Tillman',
  designation: 'Intelligent violet Litas',
  username: 'Program',
  passwordHash: 'Pound',
  mobileNo: 'utilize matrix instruction',
  email: 'Raina_Dooley69@yahoo.com',
  description: 'invoice relationships 24/7',
  department: 'olive Generic',
  imageUrl: '../fake-data/blob/hipster.png',
  imageUrlContentType: 'unknown',
  isActivated: false,
  langKey: 'Tanzanian',
  activationKey: 'Club capacitor black',
  resetKey: 'hacking Won',
  resetDate: dayjs('2022-09-29T03:22'),
  createdBy: 'BCEAO',
  createdOn: dayjs('2022-09-28T09:32'),
  lastModified: dayjs('2022-09-28T17:37'),
  lastModifiedBy: 'Licensed',
  freeField1: '1080p bluetooth',
  freeField2: 'Barbados Corners',
  freeField3: 'Salad Frozen',
  freeField4: 'enhance',
};

export const sampleWithNewData: NewSecurityUser = {
  username: 'AI',
  passwordHash: 'Adaptive Architect',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
