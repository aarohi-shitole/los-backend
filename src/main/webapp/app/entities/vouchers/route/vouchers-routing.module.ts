import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { VouchersComponent } from '../list/vouchers.component';
import { VouchersDetailComponent } from '../detail/vouchers-detail.component';
import { VouchersUpdateComponent } from '../update/vouchers-update.component';
import { VouchersRoutingResolveService } from './vouchers-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const vouchersRoute: Routes = [
  {
    path: '',
    component: VouchersComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VouchersDetailComponent,
    resolve: {
      vouchers: VouchersRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VouchersUpdateComponent,
    resolve: {
      vouchers: VouchersRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VouchersUpdateComponent,
    resolve: {
      vouchers: VouchersRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(vouchersRoute)],
  exports: [RouterModule],
})
export class VouchersRoutingModule {}
