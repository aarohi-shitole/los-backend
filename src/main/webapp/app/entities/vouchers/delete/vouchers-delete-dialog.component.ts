import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IVouchers } from '../vouchers.model';
import { VouchersService } from '../service/vouchers.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './vouchers-delete-dialog.component.html',
})
export class VouchersDeleteDialogComponent {
  vouchers?: IVouchers;

  constructor(protected vouchersService: VouchersService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.vouchersService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
