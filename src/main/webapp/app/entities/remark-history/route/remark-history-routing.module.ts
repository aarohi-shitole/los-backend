import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RemarkHistoryComponent } from '../list/remark-history.component';
import { RemarkHistoryDetailComponent } from '../detail/remark-history-detail.component';
import { RemarkHistoryUpdateComponent } from '../update/remark-history-update.component';
import { RemarkHistoryRoutingResolveService } from './remark-history-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const remarkHistoryRoute: Routes = [
  {
    path: '',
    component: RemarkHistoryComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RemarkHistoryDetailComponent,
    resolve: {
      remarkHistory: RemarkHistoryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RemarkHistoryUpdateComponent,
    resolve: {
      remarkHistory: RemarkHistoryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RemarkHistoryUpdateComponent,
    resolve: {
      remarkHistory: RemarkHistoryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(remarkHistoryRoute)],
  exports: [RouterModule],
})
export class RemarkHistoryRoutingModule {}
