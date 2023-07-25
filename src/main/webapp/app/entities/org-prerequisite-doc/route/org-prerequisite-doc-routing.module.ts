import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { OrgPrerequisiteDocComponent } from '../list/org-prerequisite-doc.component';
import { OrgPrerequisiteDocDetailComponent } from '../detail/org-prerequisite-doc-detail.component';
import { OrgPrerequisiteDocUpdateComponent } from '../update/org-prerequisite-doc-update.component';
import { OrgPrerequisiteDocRoutingResolveService } from './org-prerequisite-doc-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const orgPrerequisiteDocRoute: Routes = [
  {
    path: '',
    component: OrgPrerequisiteDocComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OrgPrerequisiteDocDetailComponent,
    resolve: {
      orgPrerequisiteDoc: OrgPrerequisiteDocRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OrgPrerequisiteDocUpdateComponent,
    resolve: {
      orgPrerequisiteDoc: OrgPrerequisiteDocRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OrgPrerequisiteDocUpdateComponent,
    resolve: {
      orgPrerequisiteDoc: OrgPrerequisiteDocRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(orgPrerequisiteDocRoute)],
  exports: [RouterModule],
})
export class OrgPrerequisiteDocRoutingModule {}
