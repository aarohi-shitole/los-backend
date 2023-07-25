import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AccountHeadCodeComponent } from './list/account-head-code.component';
import { AccountHeadCodeDetailComponent } from './detail/account-head-code-detail.component';
import { AccountHeadCodeUpdateComponent } from './update/account-head-code-update.component';
import { AccountHeadCodeDeleteDialogComponent } from './delete/account-head-code-delete-dialog.component';
import { AccountHeadCodeRoutingModule } from './route/account-head-code-routing.module';

@NgModule({
  imports: [SharedModule, AccountHeadCodeRoutingModule],
  declarations: [
    AccountHeadCodeComponent,
    AccountHeadCodeDetailComponent,
    AccountHeadCodeUpdateComponent,
    AccountHeadCodeDeleteDialogComponent,
  ],
})
export class AccountHeadCodeModule {}
