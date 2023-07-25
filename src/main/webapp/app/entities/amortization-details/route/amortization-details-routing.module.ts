import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AmortizationDetailsComponent } from '../list/amortization-details.component';
import { AmortizationDetailsDetailComponent } from '../detail/amortization-details-detail.component';
import { AmortizationDetailsUpdateComponent } from '../update/amortization-details-update.component';
import { AmortizationDetailsRoutingResolveService } from './amortization-details-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const amortizationDetailsRoute: Routes = [
  {
    path: '',
    component: AmortizationDetailsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AmortizationDetailsDetailComponent,
    resolve: {
      amortizationDetails: AmortizationDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AmortizationDetailsUpdateComponent,
    resolve: {
      amortizationDetails: AmortizationDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AmortizationDetailsUpdateComponent,
    resolve: {
      amortizationDetails: AmortizationDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(amortizationDetailsRoute)],
  exports: [RouterModule],
})
export class AmortizationDetailsRoutingModule {}
