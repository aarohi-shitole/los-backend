import dayjs from 'dayjs/esm';

import { Title } from 'app/entities/enumerations/title.model';
import { Gender } from 'app/entities/enumerations/gender.model';
import { Occupation } from 'app/entities/enumerations/occupation.model';
import { MaritalStatus } from 'app/entities/enumerations/marital-status.model';

import { IGuarantor, NewGuarantor } from './guarantor.model';

export const sampleWithRequiredData: IGuarantor = {
  id: 54104,
};

export const sampleWithPartialData: IGuarantor = {
  id: 56235,
  firstName: 'Friedrich',
  membershipNo: 'West quantify',
  dob: dayjs('2022-09-28'),
  email: 'Gerda_Jacobi@gmail.com',
  houseOwner: 'Wisconsin Money',
  occupation: Occupation['BUSINESS'],
  employerNameAdd: 'solution Sharable blue',
  soclibilType: 'reintermediate Kentucky',
  otherlibilAmt: 10463,
  hasPanVerified: true,
  numberOfAssets: 40551,
  isDeleted: true,
  lastModified: dayjs('2022-09-28T20:39'),
  lastModifiedBy: 'Ball',
  createdBy: 'Awesome Hat',
  freeField5: 'TCP Steel moderator',
  freeField6: 'Metrics bypass Suriname',
};

export const sampleWithFullData: IGuarantor = {
  id: 18367,
  title: Title['MISS'],
  firstName: 'Rusty',
  middleName: 'Indian Data SCSI',
  lastName: 'Shields',
  membershipNo: 'Beauty',
  gender: Gender['TRANSGENDER'],
  dob: dayjs('2022-09-28'),
  email: 'Chase6@gmail.com',
  mobileNo: 'efficient',
  houseOwner: 'state Nevis',
  occupation: Occupation['SERVICE'],
  employerNameAdd: 'Hat Shoes array',
  soclibilAmt: 97914,
  soclibilType: 'Senior Human',
  otherlibilAmt: 43671,
  otherlibilType: 'connect teal',
  aadharCardNo: 'Fresh',
  panCard: 'Handmade copying Horizontal',
  maritalStatus: MaritalStatus['DIVORCED'],
  hasAdharVerified: true,
  hasPanVerified: false,
  numberOfAssets: 37955,
  grossAnnualInc: 36036,
  netIncome: 77676,
  isIncomeTaxPayer: false,
  isActive: true,
  isDeleted: true,
  lastModified: dayjs('2022-09-28T14:31'),
  lastModifiedBy: 'Borders Regional',
  createdBy: 'haptic Chile drive',
  createdOn: dayjs('2022-09-28T09:14'),
  freeField1: 'generate virtual utilize',
  freeField2: 'Sports',
  freeField3: 'Markets Movies',
  freeField4: 'Fresh',
  freeField5: 'Lek Somalia',
  freeField6: 'Garden Gloves Isle',
};

export const sampleWithNewData: NewGuarantor = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
