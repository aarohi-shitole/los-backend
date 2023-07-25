import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LoanDisbursementComponent } from '../list/loan-disbursement.component';
import { LoanDisbursementDetailComponent } from '../detail/loan-disbursement-detail.component';
import { LoanDisbursementUpdateComponent } from '../update/loan-disbursement-update.component';
import { LoanDisbursementRoutingResolveService } from './loan-disbursement-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const loanDisbursementRoute: Routes = [
  {
    path: '',
    component: LoanDisbursementComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LoanDisbursementDetailComponent,
    resolve: {
      loanDisbursement: LoanDisbursementRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LoanDisbursementUpdateComponent,
    resolve: {
      loanDisbursement: LoanDisbursementRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LoanDisbursementUpdateComponent,
    resolve: {
      loanDisbursement: LoanDisbursementRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(loanDisbursementRoute)],
  exports: [RouterModule],
})
export class LoanDisbursementRoutingModule {}
