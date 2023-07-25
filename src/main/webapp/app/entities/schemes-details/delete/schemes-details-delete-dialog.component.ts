import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISchemesDetails } from '../schemes-details.model';
import { SchemesDetailsService } from '../service/schemes-details.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './schemes-details-delete-dialog.component.html',
})
export class SchemesDetailsDeleteDialogComponent {
  schemesDetails?: ISchemesDetails;

  constructor(protected schemesDetailsService: SchemesDetailsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.schemesDetailsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
