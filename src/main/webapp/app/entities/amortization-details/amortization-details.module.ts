import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AmortizationDetailsComponent } from './list/amortization-details.component';
import { AmortizationDetailsDetailComponent } from './detail/amortization-details-detail.component';
import { AmortizationDetailsUpdateComponent } from './update/amortization-details-update.component';
import { AmortizationDetailsDeleteDialogComponent } from './delete/amortization-details-delete-dialog.component';
import { AmortizationDetailsRoutingModule } from './route/amortization-details-routing.module';

@NgModule({
  imports: [SharedModule, AmortizationDetailsRoutingModule],
  declarations: [
    AmortizationDetailsComponent,
    AmortizationDetailsDetailComponent,
    AmortizationDetailsUpdateComponent,
    AmortizationDetailsDeleteDialogComponent,
  ],
})
export class AmortizationDetailsModule {}
