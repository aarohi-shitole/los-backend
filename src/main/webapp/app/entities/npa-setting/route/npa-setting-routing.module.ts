import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NpaSettingComponent } from '../list/npa-setting.component';
import { NpaSettingDetailComponent } from '../detail/npa-setting-detail.component';
import { NpaSettingUpdateComponent } from '../update/npa-setting-update.component';
import { NpaSettingRoutingResolveService } from './npa-setting-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const npaSettingRoute: Routes = [
  {
    path: '',
    component: NpaSettingComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NpaSettingDetailComponent,
    resolve: {
      npaSetting: NpaSettingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NpaSettingUpdateComponent,
    resolve: {
      npaSetting: NpaSettingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NpaSettingUpdateComponent,
    resolve: {
      npaSetting: NpaSettingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(npaSettingRoute)],
  exports: [RouterModule],
})
export class NpaSettingRoutingModule {}
