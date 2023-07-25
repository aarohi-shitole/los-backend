import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { VouchersHistoryComponent } from '../list/vouchers-history.component';
import { VouchersHistoryDetailComponent } from '../detail/vouchers-history-detail.component';
import { VouchersHistoryUpdateComponent } from '../update/vouchers-history-update.component';
import { VouchersHistoryRoutingResolveService } from './vouchers-history-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const vouchersHistoryRoute: Routes = [
  {
    path: '',
    component: VouchersHistoryComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VouchersHistoryDetailComponent,
    resolve: {
      vouchersHistory: VouchersHistoryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VouchersHistoryUpdateComponent,
    resolve: {
      vouchersHistory: VouchersHistoryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VouchersHistoryUpdateComponent,
    resolve: {
      vouchersHistory: VouchersHistoryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(vouchersHistoryRoute)],
  exports: [RouterModule],
})
export class VouchersHistoryRoutingModule {}
