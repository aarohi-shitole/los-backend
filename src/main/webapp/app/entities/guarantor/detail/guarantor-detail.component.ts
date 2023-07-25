import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGuarantor } from '../guarantor.model';

@Component({
  selector: 'jhi-guarantor-detail',
  templateUrl: './guarantor-detail.component.html',
})
export class GuarantorDetailComponent implements OnInit {
  guarantor: IGuarantor | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ guarantor }) => {
      this.guarantor = guarantor;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
