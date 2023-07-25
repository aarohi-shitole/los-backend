import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INpaSetting } from '../npa-setting.model';

@Component({
  selector: 'jhi-npa-setting-detail',
  templateUrl: './npa-setting-detail.component.html',
})
export class NpaSettingDetailComponent implements OnInit {
  npaSetting: INpaSetting | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ npaSetting }) => {
      this.npaSetting = npaSetting;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
