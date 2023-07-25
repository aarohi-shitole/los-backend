import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MasterAgreementComponent } from './list/master-agreement.component';
import { MasterAgreementDetailComponent } from './detail/master-agreement-detail.component';
import { MasterAgreementUpdateComponent } from './update/master-agreement-update.component';
import { MasterAgreementDeleteDialogComponent } from './delete/master-agreement-delete-dialog.component';
import { MasterAgreementRoutingModule } from './route/master-agreement-routing.module';

@NgModule({
  imports: [SharedModule, MasterAgreementRoutingModule],
  declarations: [
    MasterAgreementComponent,
    MasterAgreementDetailComponent,
    MasterAgreementUpdateComponent,
    MasterAgreementDeleteDialogComponent,
  ],
})
export class MasterAgreementModule {}
