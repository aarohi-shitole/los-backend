import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { IncomeDetailsComponent } from './list/income-details.component';
import { IncomeDetailsDetailComponent } from './detail/income-details-detail.component';
import { IncomeDetailsUpdateComponent } from './update/income-details-update.component';
import { IncomeDetailsDeleteDialogComponent } from './delete/income-details-delete-dialog.component';
import { IncomeDetailsRoutingModule } from './route/income-details-routing.module';

@NgModule({
  imports: [SharedModule, IncomeDetailsRoutingModule],
  declarations: [IncomeDetailsComponent, IncomeDetailsDetailComponent, IncomeDetailsUpdateComponent, IncomeDetailsDeleteDialogComponent],
})
export class IncomeDetailsModule {}
