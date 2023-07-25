import dayjs from 'dayjs/esm';

import { IOrgPrerequisiteDoc, NewOrgPrerequisiteDoc } from './org-prerequisite-doc.model';

export const sampleWithRequiredData: IOrgPrerequisiteDoc = {
  id: 24955,
};

export const sampleWithPartialData: IOrgPrerequisiteDoc = {
  id: 99272,
  docDesc: 'Tactics transmit',
  ismandatory: false,
  lastModifiedBy: 'orchid Shirt Dynamic',
  createdBy: 'Chips',
  createdOn: dayjs('2022-09-29T00:18'),
  freeField1: 'teal Frozen',
  freeField2: 'withdrawal Re-engineered Account',
  freeField3: 'Montana Beauty Rubber',
  freeField4: 'Tala blockchains France',
};

export const sampleWithFullData: IOrgPrerequisiteDoc = {
  id: 10271,
  docDesc: 'red',
  docName: 'circuit grey',
  docCatagory: 'Account middleware',
  ismandatory: true,
  lastModified: dayjs('2022-09-28T11:19'),
  lastModifiedBy: 'deposit Rustic',
  createdBy: 'face plum',
  createdOn: dayjs('2022-09-28T12:02'),
  isDeleted: false,
  freeField1: 'South Rico',
  freeField2: 'Rubber',
  freeField3: 'Licensed Mauritius',
  freeField4: 'program',
  freeField5: 'Peso',
};

export const sampleWithNewData: NewOrgPrerequisiteDoc = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
