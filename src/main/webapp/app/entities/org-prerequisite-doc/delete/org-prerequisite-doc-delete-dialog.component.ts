import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IOrgPrerequisiteDoc } from '../org-prerequisite-doc.model';
import { OrgPrerequisiteDocService } from '../service/org-prerequisite-doc.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './org-prerequisite-doc-delete-dialog.component.html',
})
export class OrgPrerequisiteDocDeleteDialogComponent {
  orgPrerequisiteDoc?: IOrgPrerequisiteDoc;

  constructor(protected orgPrerequisiteDocService: OrgPrerequisiteDocService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.orgPrerequisiteDocService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
