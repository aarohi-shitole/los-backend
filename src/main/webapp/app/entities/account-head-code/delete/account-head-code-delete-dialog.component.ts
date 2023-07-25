import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAccountHeadCode } from '../account-head-code.model';
import { AccountHeadCodeService } from '../service/account-head-code.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './account-head-code-delete-dialog.component.html',
})
export class AccountHeadCodeDeleteDialogComponent {
  accountHeadCode?: IAccountHeadCode;

  constructor(protected accountHeadCodeService: AccountHeadCodeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.accountHeadCodeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
