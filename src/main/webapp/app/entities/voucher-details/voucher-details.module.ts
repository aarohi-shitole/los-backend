import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { VoucherDetailsComponent } from './list/voucher-details.component';
import { VoucherDetailsDetailComponent } from './detail/voucher-details-detail.component';
import { VoucherDetailsUpdateComponent } from './update/voucher-details-update.component';
import { VoucherDetailsDeleteDialogComponent } from './delete/voucher-details-delete-dialog.component';
import { VoucherDetailsRoutingModule } from './route/voucher-details-routing.module';

@NgModule({
  imports: [SharedModule, VoucherDetailsRoutingModule],
  declarations: [
    VoucherDetailsComponent,
    VoucherDetailsDetailComponent,
    VoucherDetailsUpdateComponent,
    VoucherDetailsDeleteDialogComponent,
  ],
})
export class VoucherDetailsModule {}
