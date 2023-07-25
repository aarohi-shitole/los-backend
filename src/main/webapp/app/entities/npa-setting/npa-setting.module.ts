import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { NpaSettingComponent } from './list/npa-setting.component';
import { NpaSettingDetailComponent } from './detail/npa-setting-detail.component';
import { NpaSettingUpdateComponent } from './update/npa-setting-update.component';
import { NpaSettingDeleteDialogComponent } from './delete/npa-setting-delete-dialog.component';
import { NpaSettingRoutingModule } from './route/npa-setting-routing.module';

@NgModule({
  imports: [SharedModule, NpaSettingRoutingModule],
  declarations: [NpaSettingComponent, NpaSettingDetailComponent, NpaSettingUpdateComponent, NpaSettingDeleteDialogComponent],
})
export class NpaSettingModule {}
