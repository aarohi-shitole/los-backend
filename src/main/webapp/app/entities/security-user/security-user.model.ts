import dayjs from 'dayjs/esm';
import { IBranch } from 'app/entities/branch/branch.model';
import { ISecurityPermission } from 'app/entities/security-permission/security-permission.model';
import { ISecurityRole } from 'app/entities/security-role/security-role.model';

export interface ISecurityUser {
  id: number;
  firstName?: string | null;
  lastName?: string | null;
  designation?: string | null;
  username?: string | null;
  passwordHash?: string | null;
  mobileNo?: string | null;
  email?: string | null;
  description?: string | null;
  department?: string | null;
  imageUrl?: string | null;
  imageUrlContentType?: string | null;
  isActivated?: boolean | null;
  langKey?: string | null;
  activationKey?: string | null;
  resetKey?: string | null;
  resetDate?: dayjs.Dayjs | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  freeField4?: string | null;
  branch?: Pick<IBranch, 'id'> | null;
  securityPermissions?: Pick<ISecurityPermission, 'id' | 'permissionName'>[] | null;
  securityRoles?: Pick<ISecurityRole, 'id' | 'roleName'>[] | null;
}

export type NewSecurityUser = Omit<ISecurityUser, 'id'> & { id: null };
