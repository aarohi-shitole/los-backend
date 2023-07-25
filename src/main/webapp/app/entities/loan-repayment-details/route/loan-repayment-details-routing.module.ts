import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LoanRepaymentDetailsComponent } from '../list/loan-repayment-details.component';
import { LoanRepaymentDetailsDetailComponent } from '../detail/loan-repayment-details-detail.component';
import { LoanRepaymentDetailsUpdateComponent } from '../update/loan-repayment-details-update.component';
import { LoanRepaymentDetailsRoutingResolveService } from './loan-repayment-details-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const loanRepaymentDetailsRoute: Routes = [
  {
    path: '',
    component: LoanRepaymentDetailsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LoanRepaymentDetailsDetailComponent,
    resolve: {
      loanRepaymentDetails: LoanRepaymentDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LoanRepaymentDetailsUpdateComponent,
    resolve: {
      loanRepaymentDetails: LoanRepaymentDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LoanRepaymentDetailsUpdateComponent,
    resolve: {
      loanRepaymentDetails: LoanRepaymentDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(loanRepaymentDetailsRoute)],
  exports: [RouterModule],
})
export class LoanRepaymentDetailsRoutingModule {}
