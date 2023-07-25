import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { GuarantorComponent } from './list/guarantor.component';
import { GuarantorDetailComponent } from './detail/guarantor-detail.component';
import { GuarantorUpdateComponent } from './update/guarantor-update.component';
import { GuarantorDeleteDialogComponent } from './delete/guarantor-delete-dialog.component';
import { GuarantorRoutingModule } from './route/guarantor-routing.module';

@NgModule({
  imports: [SharedModule, GuarantorRoutingModule],
  declarations: [GuarantorComponent, GuarantorDetailComponent, GuarantorUpdateComponent, GuarantorDeleteDialogComponent],
})
export class GuarantorModule {}
