import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMemberExistingfacility } from '../member-existingfacility.model';
import { MemberExistingfacilityService } from '../service/member-existingfacility.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './member-existingfacility-delete-dialog.component.html',
})
export class MemberExistingfacilityDeleteDialogComponent {
  memberExistingfacility?: IMemberExistingfacility;

  constructor(protected memberExistingfacilityService: MemberExistingfacilityService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.memberExistingfacilityService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
