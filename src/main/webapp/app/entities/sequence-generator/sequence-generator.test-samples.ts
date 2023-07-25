import { ISequenceGenerator, NewSequenceGenerator } from './sequence-generator.model';

export const sampleWithRequiredData: ISequenceGenerator = {
  id: 56346,
};

export const sampleWithPartialData: ISequenceGenerator = {
  id: 36711,
  nextValLoanApp: 48092,
  nextValLoanAccount: 96752,
  freeField1: 66765,
  freeField2: 13765,
  freeField3: 36804,
  freeField4: 79883,
};

export const sampleWithFullData: ISequenceGenerator = {
  id: 98812,
  nextValMember: 86741,
  nextValLoanApp: 28744,
  nextValLoanAccount: 82179,
  nextValVoucher: 28134,
  freeField1: 7234,
  freeField2: 66623,
  freeField3: 40419,
  freeField4: 36627,
  freeField5: 24388,
};

export const sampleWithNewData: NewSequenceGenerator = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
