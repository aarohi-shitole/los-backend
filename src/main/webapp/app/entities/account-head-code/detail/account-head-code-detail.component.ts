import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAccountHeadCode } from '../account-head-code.model';

@Component({
  selector: 'jhi-account-head-code-detail',
  templateUrl: './account-head-code-detail.component.html',
})
export class AccountHeadCodeDetailComponent implements OnInit {
  accountHeadCode: IAccountHeadCode | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accountHeadCode }) => {
      this.accountHeadCode = accountHeadCode;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
