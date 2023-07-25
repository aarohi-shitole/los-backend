import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MemberExistingfacilityComponent } from '../list/member-existingfacility.component';
import { MemberExistingfacilityDetailComponent } from '../detail/member-existingfacility-detail.component';
import { MemberExistingfacilityUpdateComponent } from '../update/member-existingfacility-update.component';
import { MemberExistingfacilityRoutingResolveService } from './member-existingfacility-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const memberExistingfacilityRoute: Routes = [
  {
    path: '',
    component: MemberExistingfacilityComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MemberExistingfacilityDetailComponent,
    resolve: {
      memberExistingfacility: MemberExistingfacilityRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MemberExistingfacilityUpdateComponent,
    resolve: {
      memberExistingfacility: MemberExistingfacilityRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MemberExistingfacilityUpdateComponent,
    resolve: {
      memberExistingfacility: MemberExistingfacilityRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(memberExistingfacilityRoute)],
  exports: [RouterModule],
})
export class MemberExistingfacilityRoutingModule {}
