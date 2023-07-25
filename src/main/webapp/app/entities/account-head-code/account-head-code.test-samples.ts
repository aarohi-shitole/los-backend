import dayjs from 'dayjs/esm';

import { MappingType } from 'app/entities/enumerations/mapping-type.model';

import { IAccountHeadCode, NewAccountHeadCode } from './account-head-code.model';

export const sampleWithRequiredData: IAccountHeadCode = {
  id: 49597,
};

export const sampleWithPartialData: IAccountHeadCode = {
  id: 93881,
  headCodeName: '1080p',
  createdBy: 'FTP',
  createdOn: dayjs('2022-09-29T04:02'),
  isDeleted: true,
  freeField2: 'Technician Auto',
  freeField3: 'Buckinghamshire Cape Euro',
  freeField5: 'Marketing',
};

export const sampleWithFullData: IAccountHeadCode = {
  id: 27787,
  type: MappingType['BORROWING'],
  value: 'compress Loop',
  headCodeName: 'Executive Alaska',
  lastModified: dayjs('2022-09-28T16:40'),
  lastModifiedBy: 'core invoice',
  createdBy: 'red Generic',
  createdOn: dayjs('2022-09-28T21:47'),
  isDeleted: false,
  freeField1: 'Mouse Savings',
  freeField2: 'Malaysian multi-byte',
  freeField3: 'TCP Bedfordshire',
  freeField4: 'synthesizing',
  freeField5: 'Bolivia online initiative',
};

export const sampleWithNewData: NewAccountHeadCode = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
