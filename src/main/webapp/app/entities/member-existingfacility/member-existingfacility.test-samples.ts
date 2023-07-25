import dayjs from 'dayjs/esm';

import { FacilityStatus } from 'app/entities/enumerations/facility-status.model';
import { CreditRating } from 'app/entities/enumerations/credit-rating.model';

import { IMemberExistingfacility, NewMemberExistingfacility } from './member-existingfacility.model';

export const sampleWithRequiredData: IMemberExistingfacility = {
  id: 17096,
};

export const sampleWithPartialData: IMemberExistingfacility = {
  id: 2012,
  facilitatedFrom: 'Avon',
  nature: 'Officer wireless Land',
  status: FacilityStatus['REGULAR'],
  rating: CreditRating['POOR'],
  lastModified: dayjs('2022-09-28T19:38'),
  lastModifiedBy: 'primary maroon customer',
  freeField1: 'Refined incremental',
  freeField2: 'invoice',
  freeField5: 'Tasty multi-byte payment',
};

export const sampleWithFullData: IMemberExistingfacility = {
  id: 5107,
  year: 38247,
  facilityName: 'Representative',
  facilitatedFrom: 'Ruble',
  nature: 'circuit Functionality',
  amtInLac: 34181,
  purpose: 'THX solid',
  sectionDate: dayjs('2022-09-28T22:56'),
  outstandingInLac: 64168,
  status: FacilityStatus['NPA'],
  rating: CreditRating['POOR'],
  isDeleted: true,
  lastModified: dayjs('2022-09-28T09:17'),
  lastModifiedBy: 'Guadeloupe',
  createdBy: 'Cambridgeshire Car Supervisor',
  createdOn: dayjs('2022-09-28T17:50'),
  freeField1: 'Nebraska Maryland',
  freeField2: 'database innovate',
  freeField3: 'Berkshire internet',
  freeField4: 'Intelligent',
  freeField5: 'Home',
  freeField6: 'SDD Automotive Enterprise-wide',
};

export const sampleWithNewData: NewMemberExistingfacility = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
