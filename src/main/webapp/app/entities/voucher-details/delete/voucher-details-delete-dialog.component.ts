import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IVoucherDetails } from '../voucher-details.model';
import { VoucherDetailsService } from '../service/voucher-details.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './voucher-details-delete-dialog.component.html',
})
export class VoucherDetailsDeleteDialogComponent {
  voucherDetails?: IVoucherDetails;

  constructor(protected voucherDetailsService: VoucherDetailsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.voucherDetailsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
