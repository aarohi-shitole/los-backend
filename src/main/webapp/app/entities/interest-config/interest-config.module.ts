import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { InterestConfigComponent } from './list/interest-config.component';
import { InterestConfigDetailComponent } from './detail/interest-config-detail.component';
import { InterestConfigUpdateComponent } from './update/interest-config-update.component';
import { InterestConfigDeleteDialogComponent } from './delete/interest-config-delete-dialog.component';
import { InterestConfigRoutingModule } from './route/interest-config-routing.module';

@NgModule({
  imports: [SharedModule, InterestConfigRoutingModule],
  declarations: [
    InterestConfigComponent,
    InterestConfigDetailComponent,
    InterestConfigUpdateComponent,
    InterestConfigDeleteDialogComponent,
  ],
})
export class InterestConfigModule {}
