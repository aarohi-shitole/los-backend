import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EpayComponent } from './list/epay.component';
import { EpayDetailComponent } from './detail/epay-detail.component';
import { EpayUpdateComponent } from './update/epay-update.component';
import { EpayDeleteDialogComponent } from './delete/epay-delete-dialog.component';
import { EpayRoutingModule } from './route/epay-routing.module';

@NgModule({
  imports: [SharedModule, EpayRoutingModule],
  declarations: [EpayComponent, EpayDetailComponent, EpayUpdateComponent, EpayDeleteDialogComponent],
})
export class EpayModule {}
