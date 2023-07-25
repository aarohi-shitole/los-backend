import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDeclearation } from '../declearation.model';

@Component({
  selector: 'jhi-declearation-detail',
  templateUrl: './declearation-detail.component.html',
})
export class DeclearationDetailComponent implements OnInit {
  declearation: IDeclearation | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ declearation }) => {
      this.declearation = declearation;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
