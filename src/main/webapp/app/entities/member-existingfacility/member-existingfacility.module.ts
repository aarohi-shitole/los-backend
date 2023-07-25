import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MemberExistingfacilityComponent } from './list/member-existingfacility.component';
import { MemberExistingfacilityDetailComponent } from './detail/member-existingfacility-detail.component';
import { MemberExistingfacilityUpdateComponent } from './update/member-existingfacility-update.component';
import { MemberExistingfacilityDeleteDialogComponent } from './delete/member-existingfacility-delete-dialog.component';
import { MemberExistingfacilityRoutingModule } from './route/member-existingfacility-routing.module';

@NgModule({
  imports: [SharedModule, MemberExistingfacilityRoutingModule],
  declarations: [
    MemberExistingfacilityComponent,
    MemberExistingfacilityDetailComponent,
    MemberExistingfacilityUpdateComponent,
    MemberExistingfacilityDeleteDialogComponent,
  ],
})
export class MemberExistingfacilityModule {}
