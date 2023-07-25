import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LoanCatagoryComponent } from '../list/loan-catagory.component';
import { LoanCatagoryDetailComponent } from '../detail/loan-catagory-detail.component';
import { LoanCatagoryUpdateComponent } from '../update/loan-catagory-update.component';
import { LoanCatagoryRoutingResolveService } from './loan-catagory-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const loanCatagoryRoute: Routes = [
  {
    path: '',
    component: LoanCatagoryComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LoanCatagoryDetailComponent,
    resolve: {
      loanCatagory: LoanCatagoryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LoanCatagoryUpdateComponent,
    resolve: {
      loanCatagory: LoanCatagoryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LoanCatagoryUpdateComponent,
    resolve: {
      loanCatagory: LoanCatagoryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(loanCatagoryRoute)],
  exports: [RouterModule],
})
export class LoanCatagoryRoutingModule {}
