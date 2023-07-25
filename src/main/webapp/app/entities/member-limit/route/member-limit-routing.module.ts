import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MemberLimitComponent } from '../list/member-limit.component';
import { MemberLimitDetailComponent } from '../detail/member-limit-detail.component';
import { MemberLimitUpdateComponent } from '../update/member-limit-update.component';
import { MemberLimitRoutingResolveService } from './member-limit-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const memberLimitRoute: Routes = [
  {
    path: '',
    component: MemberLimitComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MemberLimitDetailComponent,
    resolve: {
      memberLimit: MemberLimitRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MemberLimitUpdateComponent,
    resolve: {
      memberLimit: MemberLimitRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MemberLimitUpdateComponent,
    resolve: {
      memberLimit: MemberLimitRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(memberLimitRoute)],
  exports: [RouterModule],
})
export class MemberLimitRoutingModule {}
