import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { IncomeDetailsComponent } from '../list/income-details.component';
import { IncomeDetailsDetailComponent } from '../detail/income-details-detail.component';
import { IncomeDetailsUpdateComponent } from '../update/income-details-update.component';
import { IncomeDetailsRoutingResolveService } from './income-details-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const incomeDetailsRoute: Routes = [
  {
    path: '',
    component: IncomeDetailsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: IncomeDetailsDetailComponent,
    resolve: {
      incomeDetails: IncomeDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: IncomeDetailsUpdateComponent,
    resolve: {
      incomeDetails: IncomeDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: IncomeDetailsUpdateComponent,
    resolve: {
      incomeDetails: IncomeDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(incomeDetailsRoute)],
  exports: [RouterModule],
})
export class IncomeDetailsRoutingModule {}
