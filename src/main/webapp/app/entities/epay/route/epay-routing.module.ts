import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EpayComponent } from '../list/epay.component';
import { EpayDetailComponent } from '../detail/epay-detail.component';
import { EpayUpdateComponent } from '../update/epay-update.component';
import { EpayRoutingResolveService } from './epay-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const epayRoute: Routes = [
  {
    path: '',
    component: EpayComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EpayDetailComponent,
    resolve: {
      epay: EpayRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EpayUpdateComponent,
    resolve: {
      epay: EpayRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EpayUpdateComponent,
    resolve: {
      epay: EpayRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(epayRoute)],
  exports: [RouterModule],
})
export class EpayRoutingModule {}
