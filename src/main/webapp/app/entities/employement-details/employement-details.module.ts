import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EmployementDetailsComponent } from './list/employement-details.component';
import { EmployementDetailsDetailComponent } from './detail/employement-details-detail.component';
import { EmployementDetailsUpdateComponent } from './update/employement-details-update.component';
import { EmployementDetailsDeleteDialogComponent } from './delete/employement-details-delete-dialog.component';
import { EmployementDetailsRoutingModule } from './route/employement-details-routing.module';

@NgModule({
  imports: [SharedModule, EmployementDetailsRoutingModule],
  declarations: [
    EmployementDetailsComponent,
    EmployementDetailsDetailComponent,
    EmployementDetailsUpdateComponent,
    EmployementDetailsDeleteDialogComponent,
  ],
})
export class EmployementDetailsModule {}
