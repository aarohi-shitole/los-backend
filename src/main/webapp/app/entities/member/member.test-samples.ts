import dayjs from 'dayjs/esm';

import { Title } from 'app/entities/enumerations/title.model';
import { Gender } from 'app/entities/enumerations/gender.model';
import { ResidentalStatus } from 'app/entities/enumerations/residental-status.model';
import { MaritalStatus } from 'app/entities/enumerations/marital-status.model';
import { Occupation } from 'app/entities/enumerations/occupation.model';
import { Status } from 'app/entities/enumerations/status.model';
import { LoanStatus } from 'app/entities/enumerations/loan-status.model';
import { StepperNumber } from 'app/entities/enumerations/stepper-number.model';

import { IMember, NewMember } from './member.model';

export const sampleWithRequiredData: IMember = {
  id: 27280,
};

export const sampleWithPartialData: IMember = {
  id: 55945,
  firstName: 'Minerva',
  lastName: 'Quigley',
  motherName: 'Soft Dollar',
  email: 'Payton2@yahoo.com',
  alternateMobile: 'open-source Car',
  contactTimeFrom: 'Regional Borders circuit',
  custCategory: 'Architect',
  aadharCardNo: 'Mobility',
  panCard: 'Consultant Trail quantifying',
  passportExpiry: 'disintermediate',
  rationCard: 'Future-proofed',
  residenceStatus: ResidentalStatus['RESIDENT'],
  maritalStatus: MaritalStatus['OTHERS'],
  occupation: Occupation['SALARIED'],
  nationality: 'teal analyzing',
  noOfDependents: 40622,
  applicationDate: dayjs('2022-09-28T10:38'),
  hasAdharCardVerified: false,
  hasPanCardVerified: false,
  loanStatus: LoanStatus['CANCELLED'],
  enqRefrenceNo: 'reboot Bedfordshire',
  numberOfAssets: 18233,
  isDeleted: false,
  profileStepper: StepperNumber['STEP_3'],
  lastModified: dayjs('2022-09-28T08:15'),
  createdOn: dayjs('2022-09-28T22:23'),
  freeField1: 'adapter Toys Administrator',
};

export const sampleWithFullData: IMember = {
  id: 42445,
  title: Title['MRS'],
  firstName: 'Carole',
  middleName: 'compressing Solutions',
  lastName: 'Huels',
  memberId: 'Soft',
  membershipNo: 'firewall Refined open-source',
  fatherName: 'Iowa',
  motherName: 'Salad',
  gender: Gender['TRANSGENDER'],
  dob: dayjs('2022-09-28'),
  email: 'Jules_Brekke86@hotmail.com',
  mobileNo: 'architect Facilitator Lempira',
  alternateMobile: 'ROI',
  fax: 'Credit',
  contactTimeFrom: 'generate',
  contactTimeTo: 'violet Fish Outdoors',
  religion: 'indexing Michigan Administrator',
  custCategory: 'Walk Operative',
  cast: 'Home Checking Senior',
  aadharCardNo: 'Gloves relationships Compatible',
  panCard: 'purple payment Grass-roots',
  passportNo: 'Plastic orchestration',
  passportExpiry: 'Licensed',
  rationCard: 'executive',
  residenceStatus: ResidentalStatus['NON_RESIDENT'],
  maritalStatus: MaritalStatus['MARRIED'],
  familyMemberCount: 36482,
  occupation: Occupation['PROFESSIONAL'],
  nationality: 'Checking Rubber black',
  noOfDependents: 8370,
  applicationDate: dayjs('2022-09-28T18:47'),
  status: Status['DOCUMENT_VERIFIED'],
  highestQualification: 'compressing Chicken index',
  hasAdharCardVerified: false,
  hasPanCardVerified: true,
  loanStatus: LoanStatus['AWAITED'],
  enqRefrenceNo: 'synthesize Salad',
  numberOfAssets: 14216,
  isActive: false,
  isDeleted: false,
  profileStepper: StepperNumber['STEP_3'],
  lastModified: dayjs('2022-09-28T15:06'),
  lastModifiedBy: 'Bacon',
  createdBy: 'Beauty Sharable',
  createdOn: dayjs('2022-09-29T01:09'),
  freeField1: 'Loan Unbranded',
  freeField2: 'lavender optical',
  freeField3: 'Consultant programming Polarised',
  freeField4: 'eyeballs Liberia reboot',
  freeField5: 'distributed',
  freeField6: 'action-items',
};

export const sampleWithNewData: NewMember = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
