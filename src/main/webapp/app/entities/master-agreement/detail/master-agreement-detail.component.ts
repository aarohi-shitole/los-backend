import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMasterAgreement } from '../master-agreement.model';

@Component({
  selector: 'jhi-master-agreement-detail',
  templateUrl: './master-agreement-detail.component.html',
})
export class MasterAgreementDetailComponent implements OnInit {
  masterAgreement: IMasterAgreement | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ masterAgreement }) => {
      this.masterAgreement = masterAgreement;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
