import dayjs from 'dayjs/esm';

import { LedgerClassification } from 'app/entities/enumerations/ledger-classification.model';

import { ILedgerAccounts, NewLedgerAccounts } from './ledger-accounts.model';

export const sampleWithRequiredData: ILedgerAccounts = {
  id: 17025,
};

export const sampleWithPartialData: ILedgerAccounts = {
  id: 67181,
  accountName: 'Home Loan Account',
  appCode: 'Shoes',
  ledgerClassification: LedgerClassification['BALANCE_SHEET'],
  level: 60639,
  year: 'Ariary SSL Rial',
  lastModifiedBy: 'Pataca',
  createdBy: 'implement violet Bike',
  createdOn: dayjs('2022-09-28T07:11'),
  freeField1: 'Forward',
  freeField2: 'Throughway Compatible',
  freeField5: 'GB 24/7 Money',
};

export const sampleWithFullData: ILedgerAccounts = {
  id: 78062,
  accountNo: 98163,
  accountName: 'Investment Account',
  accBalance: 19584,
  accHeadCode: 'Specialist circuit',
  ledgerCode: 'yellow Peru',
  appCode: 'JSON Mouse',
  ledgerClassification: LedgerClassification['BALANCE_SHEET'],
  level: 41523,
  year: 'Identity',
  accountType: 'overriding Money Keyboard',
  lastModified: dayjs('2022-09-28T20:10'),
  lastModifiedBy: 'Bhutanese Orchestrator quantify',
  createdBy: 'virtual Tennessee plum',
  createdOn: dayjs('2022-09-28T09:23'),
  isDeleted: true,
  freeField1: 'Parks Plastic Representative',
  freeField2: 'input 1080p',
  freeField3: 'reboot magenta Soft',
  freeField4: 'Awesome',
  freeField5: 'overriding disintermediate',
};

export const sampleWithNewData: NewLedgerAccounts = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
