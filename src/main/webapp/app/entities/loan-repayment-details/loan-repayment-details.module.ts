import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LoanRepaymentDetailsComponent } from './list/loan-repayment-details.component';
import { LoanRepaymentDetailsDetailComponent } from './detail/loan-repayment-details-detail.component';
import { LoanRepaymentDetailsUpdateComponent } from './update/loan-repayment-details-update.component';
import { LoanRepaymentDetailsDeleteDialogComponent } from './delete/loan-repayment-details-delete-dialog.component';
import { LoanRepaymentDetailsRoutingModule } from './route/loan-repayment-details-routing.module';

@NgModule({
  imports: [SharedModule, LoanRepaymentDetailsRoutingModule],
  declarations: [
    LoanRepaymentDetailsComponent,
    LoanRepaymentDetailsDetailComponent,
    LoanRepaymentDetailsUpdateComponent,
    LoanRepaymentDetailsDeleteDialogComponent,
  ],
})
export class LoanRepaymentDetailsModule {}
