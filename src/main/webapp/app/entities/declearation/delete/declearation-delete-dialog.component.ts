import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDeclearation } from '../declearation.model';
import { DeclearationService } from '../service/declearation.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './declearation-delete-dialog.component.html',
})
export class DeclearationDeleteDialogComponent {
  declearation?: IDeclearation;

  constructor(protected declearationService: DeclearationService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.declearationService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
