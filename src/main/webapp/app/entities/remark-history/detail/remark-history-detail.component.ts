import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRemarkHistory } from '../remark-history.model';

@Component({
  selector: 'jhi-remark-history-detail',
  templateUrl: './remark-history-detail.component.html',
})
export class RemarkHistoryDetailComponent implements OnInit {
  remarkHistory: IRemarkHistory | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ remarkHistory }) => {
      this.remarkHistory = remarkHistory;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
