import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMemberExistingfacility } from '../member-existingfacility.model';

@Component({
  selector: 'jhi-member-existingfacility-detail',
  templateUrl: './member-existingfacility-detail.component.html',
})
export class MemberExistingfacilityDetailComponent implements OnInit {
  memberExistingfacility: IMemberExistingfacility | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ memberExistingfacility }) => {
      this.memberExistingfacility = memberExistingfacility;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
