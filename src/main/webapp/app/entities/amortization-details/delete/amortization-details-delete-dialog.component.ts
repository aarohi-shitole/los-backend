import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAmortizationDetails } from '../amortization-details.model';
import { AmortizationDetailsService } from '../service/amortization-details.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './amortization-details-delete-dialog.component.html',
})
export class AmortizationDetailsDeleteDialogComponent {
  amortizationDetails?: IAmortizationDetails;

  constructor(protected amortizationDetailsService: AmortizationDetailsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.amortizationDetailsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
