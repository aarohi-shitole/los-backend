import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { VouchersHistoryComponent } from './list/vouchers-history.component';
import { VouchersHistoryDetailComponent } from './detail/vouchers-history-detail.component';
import { VouchersHistoryUpdateComponent } from './update/vouchers-history-update.component';
import { VouchersHistoryDeleteDialogComponent } from './delete/vouchers-history-delete-dialog.component';
import { VouchersHistoryRoutingModule } from './route/vouchers-history-routing.module';

@NgModule({
  imports: [SharedModule, VouchersHistoryRoutingModule],
  declarations: [
    VouchersHistoryComponent,
    VouchersHistoryDetailComponent,
    VouchersHistoryUpdateComponent,
    VouchersHistoryDeleteDialogComponent,
  ],
})
export class VouchersHistoryModule {}
