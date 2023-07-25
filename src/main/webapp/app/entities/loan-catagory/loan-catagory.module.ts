import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LoanCatagoryComponent } from './list/loan-catagory.component';
import { LoanCatagoryDetailComponent } from './detail/loan-catagory-detail.component';
import { LoanCatagoryUpdateComponent } from './update/loan-catagory-update.component';
import { LoanCatagoryDeleteDialogComponent } from './delete/loan-catagory-delete-dialog.component';
import { LoanCatagoryRoutingModule } from './route/loan-catagory-routing.module';

@NgModule({
  imports: [SharedModule, LoanCatagoryRoutingModule],
  declarations: [LoanCatagoryComponent, LoanCatagoryDetailComponent, LoanCatagoryUpdateComponent, LoanCatagoryDeleteDialogComponent],
})
export class LoanCatagoryModule {}
