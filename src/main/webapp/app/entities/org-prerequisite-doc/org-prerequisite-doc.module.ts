import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { OrgPrerequisiteDocComponent } from './list/org-prerequisite-doc.component';
import { OrgPrerequisiteDocDetailComponent } from './detail/org-prerequisite-doc-detail.component';
import { OrgPrerequisiteDocUpdateComponent } from './update/org-prerequisite-doc-update.component';
import { OrgPrerequisiteDocDeleteDialogComponent } from './delete/org-prerequisite-doc-delete-dialog.component';
import { OrgPrerequisiteDocRoutingModule } from './route/org-prerequisite-doc-routing.module';

@NgModule({
  imports: [SharedModule, OrgPrerequisiteDocRoutingModule],
  declarations: [
    OrgPrerequisiteDocComponent,
    OrgPrerequisiteDocDetailComponent,
    OrgPrerequisiteDocUpdateComponent,
    OrgPrerequisiteDocDeleteDialogComponent,
  ],
})
export class OrgPrerequisiteDocModule {}
