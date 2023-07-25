import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISchemesDetails } from '../schemes-details.model';

@Component({
  selector: 'jhi-schemes-details-detail',
  templateUrl: './schemes-details-detail.component.html',
})
export class SchemesDetailsDetailComponent implements OnInit {
  schemesDetails: ISchemesDetails | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ schemesDetails }) => {
      this.schemesDetails = schemesDetails;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
