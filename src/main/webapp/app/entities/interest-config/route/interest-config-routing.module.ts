import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { InterestConfigComponent } from '../list/interest-config.component';
import { InterestConfigDetailComponent } from '../detail/interest-config-detail.component';
import { InterestConfigUpdateComponent } from '../update/interest-config-update.component';
import { InterestConfigRoutingResolveService } from './interest-config-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const interestConfigRoute: Routes = [
  {
    path: '',
    component: InterestConfigComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: InterestConfigDetailComponent,
    resolve: {
      interestConfig: InterestConfigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: InterestConfigUpdateComponent,
    resolve: {
      interestConfig: InterestConfigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: InterestConfigUpdateComponent,
    resolve: {
      interestConfig: InterestConfigRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(interestConfigRoute)],
  exports: [RouterModule],
})
export class InterestConfigRoutingModule {}
