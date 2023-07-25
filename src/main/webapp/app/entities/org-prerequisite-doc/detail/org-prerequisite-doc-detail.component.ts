import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrgPrerequisiteDoc } from '../org-prerequisite-doc.model';

@Component({
  selector: 'jhi-org-prerequisite-doc-detail',
  templateUrl: './org-prerequisite-doc-detail.component.html',
})
export class OrgPrerequisiteDocDetailComponent implements OnInit {
  orgPrerequisiteDoc: IOrgPrerequisiteDoc | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orgPrerequisiteDoc }) => {
      this.orgPrerequisiteDoc = orgPrerequisiteDoc;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
