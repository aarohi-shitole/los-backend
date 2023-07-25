import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SchemesDetailsComponent } from './list/schemes-details.component';
import { SchemesDetailsDetailComponent } from './detail/schemes-details-detail.component';
import { SchemesDetailsUpdateComponent } from './update/schemes-details-update.component';
import { SchemesDetailsDeleteDialogComponent } from './delete/schemes-details-delete-dialog.component';
import { SchemesDetailsRoutingModule } from './route/schemes-details-routing.module';

@NgModule({
  imports: [SharedModule, SchemesDetailsRoutingModule],
  declarations: [
    SchemesDetailsComponent,
    SchemesDetailsDetailComponent,
    SchemesDetailsUpdateComponent,
    SchemesDetailsDeleteDialogComponent,
  ],
})
export class SchemesDetailsModule {}
