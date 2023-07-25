import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MasterAgreementComponent } from '../list/master-agreement.component';
import { MasterAgreementDetailComponent } from '../detail/master-agreement-detail.component';
import { MasterAgreementUpdateComponent } from '../update/master-agreement-update.component';
import { MasterAgreementRoutingResolveService } from './master-agreement-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const masterAgreementRoute: Routes = [
  {
    path: '',
    component: MasterAgreementComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MasterAgreementDetailComponent,
    resolve: {
      masterAgreement: MasterAgreementRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MasterAgreementUpdateComponent,
    resolve: {
      masterAgreement: MasterAgreementRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MasterAgreementUpdateComponent,
    resolve: {
      masterAgreement: MasterAgreementRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(masterAgreementRoute)],
  exports: [RouterModule],
})
export class MasterAgreementRoutingModule {}
