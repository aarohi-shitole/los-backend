import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInterestConfig } from '../interest-config.model';

@Component({
  selector: 'jhi-interest-config-detail',
  templateUrl: './interest-config-detail.component.html',
})
export class InterestConfigDetailComponent implements OnInit {
  interestConfig: IInterestConfig | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ interestConfig }) => {
      this.interestConfig = interestConfig;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
