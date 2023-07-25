import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IIncomeDetails } from '../income-details.model';
import { IncomeDetailsService } from '../service/income-details.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './income-details-delete-dialog.component.html',
})
export class IncomeDetailsDeleteDialogComponent {
  incomeDetails?: IIncomeDetails;

  constructor(protected incomeDetailsService: IncomeDetailsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.incomeDetailsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
