import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { INpaSetting } from '../npa-setting.model';
import { NpaSettingService } from '../service/npa-setting.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './npa-setting-delete-dialog.component.html',
})
export class NpaSettingDeleteDialogComponent {
  npaSetting?: INpaSetting;

  constructor(protected npaSettingService: NpaSettingService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.npaSettingService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
