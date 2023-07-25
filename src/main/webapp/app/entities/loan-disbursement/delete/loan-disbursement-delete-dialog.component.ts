import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ILoanDisbursement } from '../loan-disbursement.model';
import { LoanDisbursementService } from '../service/loan-disbursement.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './loan-disbursement-delete-dialog.component.html',
})
export class LoanDisbursementDeleteDialogComponent {
  loanDisbursement?: ILoanDisbursement;

  constructor(protected loanDisbursementService: LoanDisbursementService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.loanDisbursementService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
