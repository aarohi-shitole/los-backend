import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ILoanRepaymentDetails } from '../loan-repayment-details.model';
import { LoanRepaymentDetailsService } from '../service/loan-repayment-details.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './loan-repayment-details-delete-dialog.component.html',
})
export class LoanRepaymentDetailsDeleteDialogComponent {
  loanRepaymentDetails?: ILoanRepaymentDetails;

  constructor(protected loanRepaymentDetailsService: LoanRepaymentDetailsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.loanRepaymentDetailsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
