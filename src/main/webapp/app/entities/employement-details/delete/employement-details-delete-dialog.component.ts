import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEmployementDetails } from '../employement-details.model';
import { EmployementDetailsService } from '../service/employement-details.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './employement-details-delete-dialog.component.html',
})
export class EmployementDetailsDeleteDialogComponent {
  employementDetails?: IEmployementDetails;

  constructor(protected employementDetailsService: EmployementDetailsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.employementDetailsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
