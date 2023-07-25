import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MemberLimitComponent } from './list/member-limit.component';
import { MemberLimitDetailComponent } from './detail/member-limit-detail.component';
import { MemberLimitUpdateComponent } from './update/member-limit-update.component';
import { MemberLimitDeleteDialogComponent } from './delete/member-limit-delete-dialog.component';
import { MemberLimitRoutingModule } from './route/member-limit-routing.module';

@NgModule({
  imports: [SharedModule, MemberLimitRoutingModule],
  declarations: [MemberLimitComponent, MemberLimitDetailComponent, MemberLimitUpdateComponent, MemberLimitDeleteDialogComponent],
})
export class MemberLimitModule {}
