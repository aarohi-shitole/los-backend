import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMemberAssets } from '../member-assets.model';

@Component({
  selector: 'jhi-member-assets-detail',
  templateUrl: './member-assets-detail.component.html',
})
export class MemberAssetsDetailComponent implements OnInit {
  memberAssets: IMemberAssets | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ memberAssets }) => {
      this.memberAssets = memberAssets;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
