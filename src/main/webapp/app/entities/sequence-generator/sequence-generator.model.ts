import { IBranch } from 'app/entities/branch/branch.model';

export interface ISequenceGenerator {
  id: number;
  nextValMember?: number | null;
  nextValLoanApp?: number | null;
  nextValLoanAccount?: number | null;
  nextValVoucher?: number | null;
  freeField1?: number | null;
  freeField2?: number | null;
  freeField3?: number | null;
  freeField4?: number | null;
  freeField5?: number | null;
  branch?: Pick<IBranch, 'id'> | null;
}

export type NewSequenceGenerator = Omit<ISequenceGenerator, 'id'> & { id: null };
