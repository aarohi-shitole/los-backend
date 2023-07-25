import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RemarkHistoryComponent } from './list/remark-history.component';
import { RemarkHistoryDetailComponent } from './detail/remark-history-detail.component';
import { RemarkHistoryUpdateComponent } from './update/remark-history-update.component';
import { RemarkHistoryDeleteDialogComponent } from './delete/remark-history-delete-dialog.component';
import { RemarkHistoryRoutingModule } from './route/remark-history-routing.module';

@NgModule({
  imports: [SharedModule, RemarkHistoryRoutingModule],
  declarations: [RemarkHistoryComponent, RemarkHistoryDetailComponent, RemarkHistoryUpdateComponent, RemarkHistoryDeleteDialogComponent],
})
export class RemarkHistoryModule {}
