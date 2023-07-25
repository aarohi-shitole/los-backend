import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EnquiryDetailsComponent } from './list/enquiry-details.component';
import { EnquiryDetailsDetailComponent } from './detail/enquiry-details-detail.component';
import { EnquiryDetailsUpdateComponent } from './update/enquiry-details-update.component';
import { EnquiryDetailsDeleteDialogComponent } from './delete/enquiry-details-delete-dialog.component';
import { EnquiryDetailsRoutingModule } from './route/enquiry-details-routing.module';

@NgModule({
  imports: [SharedModule, EnquiryDetailsRoutingModule],
  declarations: [
    EnquiryDetailsComponent,
    EnquiryDetailsDetailComponent,
    EnquiryDetailsUpdateComponent,
    EnquiryDetailsDeleteDialogComponent,
  ],
})
export class EnquiryDetailsModule {}
