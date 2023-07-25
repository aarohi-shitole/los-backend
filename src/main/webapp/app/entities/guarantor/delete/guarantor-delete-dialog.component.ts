import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IGuarantor } from '../guarantor.model';
import { GuarantorService } from '../service/guarantor.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './guarantor-delete-dialog.component.html',
})
export class GuarantorDeleteDialogComponent {
  guarantor?: IGuarantor;

  constructor(protected guarantorService: GuarantorService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.guarantorService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
