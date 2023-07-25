import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LedgerAccountsComponent } from '../list/ledger-accounts.component';
import { LedgerAccountsDetailComponent } from '../detail/ledger-accounts-detail.component';
import { LedgerAccountsUpdateComponent } from '../update/ledger-accounts-update.component';
import { LedgerAccountsRoutingResolveService } from './ledger-accounts-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const ledgerAccountsRoute: Routes = [
  {
    path: '',
    component: LedgerAccountsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LedgerAccountsDetailComponent,
    resolve: {
      ledgerAccounts: LedgerAccountsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LedgerAccountsUpdateComponent,
    resolve: {
      ledgerAccounts: LedgerAccountsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LedgerAccountsUpdateComponent,
    resolve: {
      ledgerAccounts: LedgerAccountsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(ledgerAccountsRoute)],
  exports: [RouterModule],
})
export class LedgerAccountsRoutingModule {}
