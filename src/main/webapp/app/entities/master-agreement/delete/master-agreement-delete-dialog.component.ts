import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMasterAgreement } from '../master-agreement.model';
import { MasterAgreementService } from '../service/master-agreement.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './master-agreement-delete-dialog.component.html',
})
export class MasterAgreementDeleteDialogComponent {
  masterAgreement?: IMasterAgreement;

  constructor(protected masterAgreementService: MasterAgreementService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.masterAgreementService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
