import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { VouchersComponent } from './list/vouchers.component';
import { VouchersDetailComponent } from './detail/vouchers-detail.component';
import { VouchersUpdateComponent } from './update/vouchers-update.component';
import { VouchersDeleteDialogComponent } from './delete/vouchers-delete-dialog.component';
import { VouchersRoutingModule } from './route/vouchers-routing.module';

@NgModule({
  imports: [SharedModule, VouchersRoutingModule],
  declarations: [VouchersComponent, VouchersDetailComponent, VouchersUpdateComponent, VouchersDeleteDialogComponent],
})
export class VouchersModule {}
