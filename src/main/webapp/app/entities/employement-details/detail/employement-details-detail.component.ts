import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmployementDetails } from '../employement-details.model';

@Component({
  selector: 'jhi-employement-details-detail',
  templateUrl: './employement-details-detail.component.html',
})
export class EmployementDetailsDetailComponent implements OnInit {
  employementDetails: IEmployementDetails | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employementDetails }) => {
      this.employementDetails = employementDetails;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
