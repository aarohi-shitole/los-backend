import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ILedgerAccounts } from '../ledger-accounts.model';
import { LedgerAccountsService } from '../service/ledger-accounts.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './ledger-accounts-delete-dialog.component.html',
})
export class LedgerAccountsDeleteDialogComponent {
  ledgerAccounts?: ILedgerAccounts;

  constructor(protected ledgerAccountsService: LedgerAccountsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ledgerAccountsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
