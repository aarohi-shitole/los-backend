import dayjs from 'dayjs/esm';
import { IMember } from 'app/entities/member/member.model';
import { Occupation } from 'app/entities/enumerations/occupation.model';
import { EmployementStatus } from 'app/entities/enumerations/employement-status.model';
import { CompanyType } from 'app/entities/enumerations/company-type.model';
import { ConstitutionType } from 'app/entities/enumerations/constitution-type.model';
import { IndustryType } from 'app/entities/enumerations/industry-type.model';

export interface IEmployementDetails {
  id: number;
  type?: Occupation | null;
  employerName?: string | null;
  status?: EmployementStatus | null;
  designation?: string | null;
  duration?: string | null;
  employerAdd?: string | null;
  prevCompanyName?: string | null;
  prevCompanyduration?: string | null;
  orgType?: CompanyType | null;
  constitutionType?: ConstitutionType | null;
  industryType?: IndustryType | null;
  businessRegNo?: string | null;
  compOwnerShip?: number | null;
  partnerName1?: string | null;
  partnerName2?: string | null;
  partnerName3?: string | null;
  isDeleted?: boolean | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  freeField4?: string | null;
  freeField5?: string | null;
  freeField6?: string | null;
  member?: Pick<IMember, 'id'> | null;
}

export type NewEmployementDetails = Omit<IEmployementDetails, 'id'> & { id: null };
