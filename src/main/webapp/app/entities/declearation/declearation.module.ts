import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DeclearationComponent } from './list/declearation.component';
import { DeclearationDetailComponent } from './detail/declearation-detail.component';
import { DeclearationUpdateComponent } from './update/declearation-update.component';
import { DeclearationDeleteDialogComponent } from './delete/declearation-delete-dialog.component';
import { DeclearationRoutingModule } from './route/declearation-routing.module';

@NgModule({
  imports: [SharedModule, DeclearationRoutingModule],
  declarations: [DeclearationComponent, DeclearationDetailComponent, DeclearationUpdateComponent, DeclearationDeleteDialogComponent],
})
export class DeclearationModule {}
