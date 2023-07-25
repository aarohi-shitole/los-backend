import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LoanAccountComponent } from '../list/loan-account.component';
import { LoanAccountDetailComponent } from '../detail/loan-account-detail.component';
import { LoanAccountUpdateComponent } from '../update/loan-account-update.component';
import { LoanAccountRoutingResolveService } from './loan-account-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const loanAccountRoute: Routes = [
  {
    path: '',
    component: LoanAccountComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LoanAccountDetailComponent,
    resolve: {
      loanAccount: LoanAccountRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LoanAccountUpdateComponent,
    resolve: {
      loanAccount: LoanAccountRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LoanAccountUpdateComponent,
    resolve: {
      loanAccount: LoanAccountRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(loanAccountRoute)],
  exports: [RouterModule],
})
export class LoanAccountRoutingModule {}
