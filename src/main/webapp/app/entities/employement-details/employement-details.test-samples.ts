import dayjs from 'dayjs/esm';

import { Occupation } from 'app/entities/enumerations/occupation.model';
import { EmployementStatus } from 'app/entities/enumerations/employement-status.model';
import { CompanyType } from 'app/entities/enumerations/company-type.model';
import { ConstitutionType } from 'app/entities/enumerations/constitution-type.model';
import { IndustryType } from 'app/entities/enumerations/industry-type.model';

import { IEmployementDetails, NewEmployementDetails } from './employement-details.model';

export const sampleWithRequiredData: IEmployementDetails = {
  id: 11567,
};

export const sampleWithPartialData: IEmployementDetails = {
  id: 91529,
  duration: 'bandwidth Metal',
  prevCompanyName: 'protocol Facilitator Mouse',
  prevCompanyduration: 'system',
  orgType: CompanyType['PRIVATE_SECTOR'],
  industryType: IndustryType['MANUFACTURING'],
  partnerName1: 'deliverables SQL quantifying',
  partnerName2: 'microchip',
  isDeleted: true,
  lastModified: dayjs('2022-09-28T13:17'),
  lastModifiedBy: 'Generic eyeballs',
  createdOn: dayjs('2022-09-28T10:49'),
  freeField3: 'synthesize',
  freeField4: 'Manager',
  freeField5: 'Fresh Freeway',
};

export const sampleWithFullData: IEmployementDetails = {
  id: 43989,
  type: Occupation['SALARIED'],
  employerName: 'Concrete Supervisor optical',
  status: EmployementStatus['PROBATION'],
  designation: 'compressing Assistant',
  duration: 'solid Trail',
  employerAdd: 'parsing',
  prevCompanyName: 'firewall',
  prevCompanyduration: 'Agent Concrete',
  orgType: CompanyType['PRIVATE_SECTOR'],
  constitutionType: ConstitutionType['PVT_LTD_COMPANY'],
  industryType: IndustryType['MANUFACTURING'],
  businessRegNo: 'Specialist',
  compOwnerShip: 81117,
  partnerName1: 'Congo invoice',
  partnerName2: 'cultivate interface',
  partnerName3: 'Handmade Shoes',
  isDeleted: false,
  lastModified: dayjs('2022-09-28T12:56'),
  lastModifiedBy: 'Stream exploit calculating',
  createdBy: 'primary Expanded paradigm',
  createdOn: dayjs('2022-09-29T04:01'),
  freeField1: 'disintermediate',
  freeField2: 'Dollar navigating',
  freeField3: 'program',
  freeField4: 'Handcrafted Spurs Assimilated',
  freeField5: 'Landing Connecticut violet',
  freeField6: 'Metal Practical XML',
};

export const sampleWithNewData: NewEmployementDetails = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
