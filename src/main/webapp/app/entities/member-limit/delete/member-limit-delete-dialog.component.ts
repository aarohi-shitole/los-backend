import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMemberLimit } from '../member-limit.model';
import { MemberLimitService } from '../service/member-limit.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './member-limit-delete-dialog.component.html',
})
export class MemberLimitDeleteDialogComponent {
  memberLimit?: IMemberLimit;

  constructor(protected memberLimitService: MemberLimitService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.memberLimitService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
