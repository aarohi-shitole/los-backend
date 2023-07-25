import dayjs from 'dayjs/esm';

export interface ILoanCatagory {
  id: number;
  productName?: string | null;
  decription?: string | null;
  value?: string | null;
  code?: string | null;
  offer?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  isDeleted?: boolean | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  freeField4?: string | null;
}

export type NewLoanCatagory = Omit<ILoanCatagory, 'id'> & { id: null };
