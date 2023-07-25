import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LoanApplicationsComponent } from './list/loan-applications.component';
import { LoanApplicationsDetailComponent } from './detail/loan-applications-detail.component';
import { LoanApplicationsUpdateComponent } from './update/loan-applications-update.component';
import { LoanApplicationsDeleteDialogComponent } from './delete/loan-applications-delete-dialog.component';
import { LoanApplicationsRoutingModule } from './route/loan-applications-routing.module';

@NgModule({
  imports: [SharedModule, LoanApplicationsRoutingModule],
  declarations: [
    LoanApplicationsComponent,
    LoanApplicationsDetailComponent,
    LoanApplicationsUpdateComponent,
    LoanApplicationsDeleteDialogComponent,
  ],
})
export class LoanApplicationsModule {}
