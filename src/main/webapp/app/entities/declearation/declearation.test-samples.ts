import dayjs from 'dayjs/esm';

import { DeclearationType } from 'app/entities/enumerations/declearation-type.model';

import { IDeclearation, NewDeclearation } from './declearation.model';

export const sampleWithRequiredData: IDeclearation = {
  id: 84865,
};

export const sampleWithPartialData: IDeclearation = {
  id: 39910,
  tiltle: 'Vermont Account needs-based',
  type: DeclearationType['ORG_TERMS'],
  tag: 'Plastic USB Automotive',
  subType: 50103,
  createdOn: dayjs('2022-09-28T22:29'),
  freeField3: 'overriding teal Checking',
};

export const sampleWithFullData: IDeclearation = {
  id: 30534,
  tiltle: 'interactive Tactics',
  type: DeclearationType['ORG_TERMS'],
  description: 'Unbranded conglomeration',
  tag: 'matrix',
  subType: 2390,
  lastModified: dayjs('2022-09-29T05:48'),
  lastModifiedBy: 'optimize',
  createdBy: 'Dynamic ADP',
  createdOn: dayjs('2022-09-28T13:52'),
  isDeleted: true,
  freeField1: 'Buckinghamshire Buckinghamshire',
  freeField2: 'responsive overriding',
  freeField3: 'Arizona',
  freeField4: 'Intuitive',
};

export const sampleWithNewData: NewDeclearation = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
