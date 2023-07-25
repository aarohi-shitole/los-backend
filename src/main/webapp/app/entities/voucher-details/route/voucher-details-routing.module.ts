import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { VoucherDetailsComponent } from '../list/voucher-details.component';
import { VoucherDetailsDetailComponent } from '../detail/voucher-details-detail.component';
import { VoucherDetailsUpdateComponent } from '../update/voucher-details-update.component';
import { VoucherDetailsRoutingResolveService } from './voucher-details-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const voucherDetailsRoute: Routes = [
  {
    path: '',
    component: VoucherDetailsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VoucherDetailsDetailComponent,
    resolve: {
      voucherDetails: VoucherDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VoucherDetailsUpdateComponent,
    resolve: {
      voucherDetails: VoucherDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VoucherDetailsUpdateComponent,
    resolve: {
      voucherDetails: VoucherDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(voucherDetailsRoute)],
  exports: [RouterModule],
})
export class VoucherDetailsRoutingModule {}
