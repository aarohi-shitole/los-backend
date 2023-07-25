import dayjs from 'dayjs/esm';

import { IDocuments, NewDocuments } from './documents.model';

export const sampleWithRequiredData: IDocuments = {
  id: 7443,
};

export const sampleWithPartialData: IDocuments = {
  id: 80525,
  tag: 'XSS Berkshire Indiana',
  fileName: 'dynamic',
  filePath: 'coherent',
  fileUrl: 'RAM Berkshire Synchronised',
  refrenceId: 72670,
  hasVerified: true,
  lastModified: dayjs('2022-09-28T23:31'),
  lastModifiedBy: 'Re-engineered quantify',
  createdBy: 'overriding Union Oklahoma',
  createdOn: dayjs('2022-09-28T20:32'),
  freeField2: 'Ergonomic',
  freeField3: 'leading-edge Manager Unbranded',
};

export const sampleWithFullData: IDocuments = {
  id: 28277,
  docType: 'target',
  docSubType: 'Islands',
  tag: 'Alaska',
  fileName: 'multi-state silver',
  filePath: 'Missouri',
  fileUrl: 'innovate',
  refrenceId: 51369,
  hasVerified: false,
  remark: 'bypassing eyeballs',
  lastModified: dayjs('2022-09-28T18:47'),
  lastModifiedBy: 'Bacon CSS',
  createdBy: 'Applications',
  createdOn: dayjs('2022-09-28T08:22'),
  isDeleted: false,
  freeField1: 'mobile architect Bedfordshire',
  freeField2: 'Cotton solid',
  freeField3: 'Profound',
  freeField4: 'Maryland interface input',
  freeField5: 'platforms bus Metal',
};

export const sampleWithNewData: NewDocuments = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
