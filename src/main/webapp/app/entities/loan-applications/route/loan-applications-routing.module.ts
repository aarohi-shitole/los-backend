import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LoanApplicationsComponent } from '../list/loan-applications.component';
import { LoanApplicationsDetailComponent } from '../detail/loan-applications-detail.component';
import { LoanApplicationsUpdateComponent } from '../update/loan-applications-update.component';
import { LoanApplicationsRoutingResolveService } from './loan-applications-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const loanApplicationsRoute: Routes = [
  {
    path: '',
    component: LoanApplicationsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LoanApplicationsDetailComponent,
    resolve: {
      loanApplications: LoanApplicationsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LoanApplicationsUpdateComponent,
    resolve: {
      loanApplications: LoanApplicationsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LoanApplicationsUpdateComponent,
    resolve: {
      loanApplications: LoanApplicationsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(loanApplicationsRoute)],
  exports: [RouterModule],
})
export class LoanApplicationsRoutingModule {}
