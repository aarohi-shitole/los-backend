import dayjs from 'dayjs/esm';

import { NpaClassification } from 'app/entities/enumerations/npa-classification.model';

import { INpaSetting, NewNpaSetting } from './npa-setting.model';

export const sampleWithRequiredData: INpaSetting = {
  id: 21269,
};

export const sampleWithPartialData: INpaSetting = {
  id: 49143,
  durationEnd: 83440,
  provision: 'mobile structure',
  year: 98014,
  createdOn: dayjs('2022-09-28T09:52'),
  isDeleted: false,
  freeField3: 'Lead Granite systems',
  freeField4: 'process neural',
  freeField5: 'repurpose Designer',
};

export const sampleWithFullData: INpaSetting = {
  id: 69880,
  npaClassification: NpaClassification['DOUBTFUL_2'],
  durationStart: 90826,
  durationEnd: 12371,
  provision: 'Avon utilize experiences',
  year: 45692,
  interestRate: 34119,
  lastModified: dayjs('2022-09-28T19:39'),
  lastModifiedBy: 'Coordinator',
  createdBy: 'Ergonomic transmitting monitor',
  createdOn: dayjs('2022-09-28T07:00'),
  isDeleted: false,
  freeField1: 'AGP',
  freeField2: 'sensor',
  freeField3: 'Agent lavender',
  freeField4: 'Multi-tiered TCP',
  freeField5: 'Investor payment Baby',
};

export const sampleWithNewData: NewNpaSetting = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
