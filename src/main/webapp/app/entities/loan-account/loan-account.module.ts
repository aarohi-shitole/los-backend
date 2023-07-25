import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LoanAccountComponent } from './list/loan-account.component';
import { LoanAccountDetailComponent } from './detail/loan-account-detail.component';
import { LoanAccountUpdateComponent } from './update/loan-account-update.component';
import { LoanAccountDeleteDialogComponent } from './delete/loan-account-delete-dialog.component';
import { LoanAccountRoutingModule } from './route/loan-account-routing.module';

@NgModule({
  imports: [SharedModule, LoanAccountRoutingModule],
  declarations: [LoanAccountComponent, LoanAccountDetailComponent, LoanAccountUpdateComponent, LoanAccountDeleteDialogComponent],
})
export class LoanAccountModule {}
