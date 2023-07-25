import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EmployementDetailsComponent } from '../list/employement-details.component';
import { EmployementDetailsDetailComponent } from '../detail/employement-details-detail.component';
import { EmployementDetailsUpdateComponent } from '../update/employement-details-update.component';
import { EmployementDetailsRoutingResolveService } from './employement-details-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const employementDetailsRoute: Routes = [
  {
    path: '',
    component: EmployementDetailsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EmployementDetailsDetailComponent,
    resolve: {
      employementDetails: EmployementDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EmployementDetailsUpdateComponent,
    resolve: {
      employementDetails: EmployementDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EmployementDetailsUpdateComponent,
    resolve: {
      employementDetails: EmployementDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(employementDetailsRoute)],
  exports: [RouterModule],
})
export class EmployementDetailsRoutingModule {}
