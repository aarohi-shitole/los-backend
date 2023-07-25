import dayjs from 'dayjs/esm';

import { LoanStatus } from 'app/entities/enumerations/loan-status.model';
import { StepperNumber } from 'app/entities/enumerations/stepper-number.model';

import { ILoanApplications, NewLoanApplications } from './loan-applications.model';

export const sampleWithRequiredData: ILoanApplications = {
  id: 31892,
};

export const sampleWithPartialData: ILoanApplications = {
  id: 95581,
  applicationNo: 'Plastic',
  isInsured: false,
  loanBenefitingArea: 54604,
  mortgageDeedNo: 18347,
  mortgageDate: dayjs('2022-09-29T03:25'),
  extentMorgageValue: 34574,
  downPaymentAmt: 91242,
  ltvRatio: 65238,
  processingFee: 79680,
  penalInterest: 49826,
  roi: 97527,
  sectionAmt: 94014,
  senctionRoi: 58207,
  securityDocUrl: 'state',
  lastModified: dayjs('2022-09-28T15:49'),
  freeField4: 'digital Lead',
  freeField6: 'Senegal Soap state',
};

export const sampleWithFullData: ILoanApplications = {
  id: 10534,
  applicationNo: 'multi-byte Outdoors',
  demandAmount: 99667,
  loanPurpose: 'Account',
  status: LoanStatus['DRAFT'],
  demandedLandAreaInHector: 9984,
  seasonOfCropLoan: 'Virginia compress vertical',
  loanSteps: StepperNumber['STEP_4'],
  isInsured: false,
  loanBenefitingArea: 39665,
  costOfInvestment: 676,
  mortgageDeedNo: 74207,
  mortgageDate: dayjs('2022-09-28T16:45'),
  extentMorgageValue: 68930,
  downPaymentAmt: 22764,
  ltvRatio: 96090,
  processingFee: 73262,
  penalInterest: 54736,
  moratorium: 'Islands North',
  roi: 84786,
  commityAmt: 3819,
  commityRoi: 3256,
  sectionAmt: 19663,
  senctionRoi: 61299,
  year: 'Alaska',
  assignedTo: 12331,
  assignedFrom: 28707,
  securityDocUrl: 'calculating Rubber',
  lastModified: dayjs('2022-09-28T21:01'),
  lastModifiedBy: 'Incredible Oman',
  freeField1: 'Wyoming Curve compressing',
  freeField2: 'optimizing JBOD',
  freeField3: 'Tactics',
  freeField4: 'Gourde deposit',
  freeField5: 'deposit Throughway',
  freeField6: 'schemas',
  freeField7: 'Chips',
};

export const sampleWithNewData: NewLoanApplications = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
