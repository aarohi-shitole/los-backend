import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ILoanAccount } from '../loan-account.model';
import { LoanAccountService } from '../service/loan-account.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './loan-account-delete-dialog.component.html',
})
export class LoanAccountDeleteDialogComponent {
  loanAccount?: ILoanAccount;

  constructor(protected loanAccountService: LoanAccountService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.loanAccountService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
