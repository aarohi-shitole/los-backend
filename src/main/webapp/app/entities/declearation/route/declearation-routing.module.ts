import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DeclearationComponent } from '../list/declearation.component';
import { DeclearationDetailComponent } from '../detail/declearation-detail.component';
import { DeclearationUpdateComponent } from '../update/declearation-update.component';
import { DeclearationRoutingResolveService } from './declearation-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const declearationRoute: Routes = [
  {
    path: '',
    component: DeclearationComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DeclearationDetailComponent,
    resolve: {
      declearation: DeclearationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DeclearationUpdateComponent,
    resolve: {
      declearation: DeclearationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DeclearationUpdateComponent,
    resolve: {
      declearation: DeclearationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(declearationRoute)],
  exports: [RouterModule],
})
export class DeclearationRoutingModule {}
