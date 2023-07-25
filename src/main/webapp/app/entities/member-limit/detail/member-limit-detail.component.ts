import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMemberLimit } from '../member-limit.model';

@Component({
  selector: 'jhi-member-limit-detail',
  templateUrl: './member-limit-detail.component.html',
})
export class MemberLimitDetailComponent implements OnInit {
  memberLimit: IMemberLimit | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ memberLimit }) => {
      this.memberLimit = memberLimit;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
