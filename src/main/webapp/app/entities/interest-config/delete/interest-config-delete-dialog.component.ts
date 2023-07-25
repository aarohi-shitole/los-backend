import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IInterestConfig } from '../interest-config.model';
import { InterestConfigService } from '../service/interest-config.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './interest-config-delete-dialog.component.html',
})
export class InterestConfigDeleteDialogComponent {
  interestConfig?: IInterestConfig;

  constructor(protected interestConfigService: InterestConfigService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.interestConfigService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
