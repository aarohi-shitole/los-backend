import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IVouchersHistory } from '../vouchers-history.model';
import { VouchersHistoryService } from '../service/vouchers-history.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './vouchers-history-delete-dialog.component.html',
})
export class VouchersHistoryDeleteDialogComponent {
  vouchersHistory?: IVouchersHistory;

  constructor(protected vouchersHistoryService: VouchersHistoryService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.vouchersHistoryService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
