import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SchemesDetailsComponent } from '../list/schemes-details.component';
import { SchemesDetailsDetailComponent } from '../detail/schemes-details-detail.component';
import { SchemesDetailsUpdateComponent } from '../update/schemes-details-update.component';
import { SchemesDetailsRoutingResolveService } from './schemes-details-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const schemesDetailsRoute: Routes = [
  {
    path: '',
    component: SchemesDetailsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SchemesDetailsDetailComponent,
    resolve: {
      schemesDetails: SchemesDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SchemesDetailsUpdateComponent,
    resolve: {
      schemesDetails: SchemesDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SchemesDetailsUpdateComponent,
    resolve: {
      schemesDetails: SchemesDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(schemesDetailsRoute)],
  exports: [RouterModule],
})
export class SchemesDetailsRoutingModule {}
