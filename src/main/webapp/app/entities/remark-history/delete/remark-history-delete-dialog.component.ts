import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRemarkHistory } from '../remark-history.model';
import { RemarkHistoryService } from '../service/remark-history.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './remark-history-delete-dialog.component.html',
})
export class RemarkHistoryDeleteDialogComponent {
  remarkHistory?: IRemarkHistory;

  constructor(protected remarkHistoryService: RemarkHistoryService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.remarkHistoryService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
