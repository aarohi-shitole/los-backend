import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { NotificationType } from 'app/entities/enumerations/notification-type.model';

export interface INotification {
  id: number;
  massage?: string | null;
  notificationType?: NotificationType | null;
  isActionRequired?: boolean | null;
  freeField1?: string | null;
  freeField2?: string | null;
  lastModified?: string | null;
  lastModifiedBy?: string | null;
  securityUser?: Pick<ISecurityUser, 'id'> | null;
}

export type NewNotification = Omit<INotification, 'id'> & { id: null };
