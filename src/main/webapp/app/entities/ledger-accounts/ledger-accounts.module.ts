import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LedgerAccountsComponent } from './list/ledger-accounts.component';
import { LedgerAccountsDetailComponent } from './detail/ledger-accounts-detail.component';
import { LedgerAccountsUpdateComponent } from './update/ledger-accounts-update.component';
import { LedgerAccountsDeleteDialogComponent } from './delete/ledger-accounts-delete-dialog.component';
import { LedgerAccountsRoutingModule } from './route/ledger-accounts-routing.module';

@NgModule({
  imports: [SharedModule, LedgerAccountsRoutingModule],
  declarations: [
    LedgerAccountsComponent,
    LedgerAccountsDetailComponent,
    LedgerAccountsUpdateComponent,
    LedgerAccountsDeleteDialogComponent,
  ],
})
export class LedgerAccountsModule {}
