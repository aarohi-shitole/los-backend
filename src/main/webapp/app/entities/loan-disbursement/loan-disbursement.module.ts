import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LoanDisbursementComponent } from './list/loan-disbursement.component';
import { LoanDisbursementDetailComponent } from './detail/loan-disbursement-detail.component';
import { LoanDisbursementUpdateComponent } from './update/loan-disbursement-update.component';
import { LoanDisbursementDeleteDialogComponent } from './delete/loan-disbursement-delete-dialog.component';
import { LoanDisbursementRoutingModule } from './route/loan-disbursement-routing.module';

@NgModule({
  imports: [SharedModule, LoanDisbursementRoutingModule],
  declarations: [
    LoanDisbursementComponent,
    LoanDisbursementDetailComponent,
    LoanDisbursementUpdateComponent,
    LoanDisbursementDeleteDialogComponent,
  ],
})
export class LoanDisbursementModule {}
