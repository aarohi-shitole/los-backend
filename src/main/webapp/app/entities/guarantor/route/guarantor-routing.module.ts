import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { GuarantorComponent } from '../list/guarantor.component';
import { GuarantorDetailComponent } from '../detail/guarantor-detail.component';
import { GuarantorUpdateComponent } from '../update/guarantor-update.component';
import { GuarantorRoutingResolveService } from './guarantor-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const guarantorRoute: Routes = [
  {
    path: '',
    component: GuarantorComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GuarantorDetailComponent,
    resolve: {
      guarantor: GuarantorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GuarantorUpdateComponent,
    resolve: {
      guarantor: GuarantorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GuarantorUpdateComponent,
    resolve: {
      guarantor: GuarantorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(guarantorRoute)],
  exports: [RouterModule],
})
export class GuarantorRoutingModule {}
