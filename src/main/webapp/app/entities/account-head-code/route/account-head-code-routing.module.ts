import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AccountHeadCodeComponent } from '../list/account-head-code.component';
import { AccountHeadCodeDetailComponent } from '../detail/account-head-code-detail.component';
import { AccountHeadCodeUpdateComponent } from '../update/account-head-code-update.component';
import { AccountHeadCodeRoutingResolveService } from './account-head-code-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const accountHeadCodeRoute: Routes = [
  {
    path: '',
    component: AccountHeadCodeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AccountHeadCodeDetailComponent,
    resolve: {
      accountHeadCode: AccountHeadCodeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AccountHeadCodeUpdateComponent,
    resolve: {
      accountHeadCode: AccountHeadCodeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AccountHeadCodeUpdateComponent,
    resolve: {
      accountHeadCode: AccountHeadCodeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(accountHeadCodeRoute)],
  exports: [RouterModule],
})
export class AccountHeadCodeRoutingModule {}
