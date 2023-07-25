import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EnquiryDetailsComponent } from '../list/enquiry-details.component';
import { EnquiryDetailsDetailComponent } from '../detail/enquiry-details-detail.component';
import { EnquiryDetailsUpdateComponent } from '../update/enquiry-details-update.component';
import { EnquiryDetailsRoutingResolveService } from './enquiry-details-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const enquiryDetailsRoute: Routes = [
  {
    path: '',
    component: EnquiryDetailsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EnquiryDetailsDetailComponent,
    resolve: {
      enquiryDetails: EnquiryDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EnquiryDetailsUpdateComponent,
    resolve: {
      enquiryDetails: EnquiryDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EnquiryDetailsUpdateComponent,
    resolve: {
      enquiryDetails: EnquiryDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(enquiryDetailsRoute)],
  exports: [RouterModule],
})
export class EnquiryDetailsRoutingModule {}
