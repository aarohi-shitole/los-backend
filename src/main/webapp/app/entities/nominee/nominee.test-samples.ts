import dayjs from 'dayjs/esm';

import { INominee, NewNominee } from './nominee.model';

export const sampleWithRequiredData: INominee = {
  id: 87924,
};

export const sampleWithPartialData: INominee = {
  id: 78374,
  lastName: 'Dietrich',
  guardianName: 'Shoes',
  aadharCard: 'neural Cordoba',
  lastModified: dayjs('2022-09-28T11:37'),
  lastModifiedBy: 'hybrid',
  isActive: false,
  freeField1: 'Handcrafted implementation',
};

export const sampleWithFullData: INominee = {
  id: 65121,
  firstName: 'Jason',
  lastName: 'Lubowitz',
  fatherName: 'Account Intelligent',
  motherName: 'Steel',
  guardianName: 'payment',
  gender: 'Cambridgeshire Handcrafted',
  dob: dayjs('2022-09-28'),
  aadharCard: 'ADP payment markets',
  nomineeDeclareDate: dayjs('2022-09-28T23:17'),
  relation: 'turquoise',
  permanentAddr: 'Creek Tactics',
  nomineePercentage: 5471,
  lastModified: dayjs('2022-09-28T20:10'),
  lastModifiedBy: 'Planner Internal',
  createdBy: 'multi-byte Bedfordshire',
  createdOn: dayjs('2022-09-28T06:51'),
  isActive: true,
  isDeleted: true,
  freeField1: 'Loan Coves Plastic',
  freeField2: 'input',
  freeField3: 'Computer Mayotte',
  freeField4: 'improvement Books',
};

export const sampleWithNewData: NewNominee = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
