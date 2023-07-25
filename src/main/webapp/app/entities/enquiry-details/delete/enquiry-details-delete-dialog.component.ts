import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEnquiryDetails } from '../enquiry-details.model';
import { EnquiryDetailsService } from '../service/enquiry-details.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './enquiry-details-delete-dialog.component.html',
})
export class EnquiryDetailsDeleteDialogComponent {
  enquiryDetails?: IEnquiryDetails;

  constructor(protected enquiryDetailsService: EnquiryDetailsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.enquiryDetailsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
