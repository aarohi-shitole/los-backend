import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ILoanCatagory } from '../loan-catagory.model';
import { LoanCatagoryService } from '../service/loan-catagory.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './loan-catagory-delete-dialog.component.html',
})
export class LoanCatagoryDeleteDialogComponent {
  loanCatagory?: ILoanCatagory;

  constructor(protected loanCatagoryService: LoanCatagoryService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.loanCatagoryService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
