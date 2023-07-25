import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { MemberExistingfacilityFormService, MemberExistingfacilityFormGroup } from './member-existingfacility-form.service';
import { IMemberExistingfacility } from '../member-existingfacility.model';
import { MemberExistingfacilityService } from '../service/member-existingfacility.service';
import { IMember } from 'app/entities/member/member.model';
import { MemberService } from 'app/entities/member/service/member.service';
import { FacilityStatus } from 'app/entities/enumerations/facility-status.model';
import { CreditRating } from 'app/entities/enumerations/credit-rating.model';

@Component({
  selector: 'jhi-member-existingfacility-update',
  templateUrl: './member-existingfacility-update.component.html',
})
export class MemberExistingfacilityUpdateComponent implements OnInit {
  isSaving = false;
  memberExistingfacility: IMemberExistingfacility | null = null;
  facilityStatusValues = Object.keys(FacilityStatus);
  creditRatingValues = Object.keys(CreditRating);

  membersSharedCollection: IMember[] = [];

  editForm: MemberExistingfacilityFormGroup = this.memberExistingfacilityFormService.createMemberExistingfacilityFormGroup();

  constructor(
    protected memberExistingfacilityService: MemberExistingfacilityService,
    protected memberExistingfacilityFormService: MemberExistingfacilityFormService,
    protected memberService: MemberService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareMember = (o1: IMember | null, o2: IMember | null): boolean => this.memberService.compareMember(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ memberExistingfacility }) => {
      this.memberExistingfacility = memberExistingfacility;
      if (memberExistingfacility) {
        this.updateForm(memberExistingfacility);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const memberExistingfacility = this.memberExistingfacilityFormService.getMemberExistingfacility(this.editForm);
    if (memberExistingfacility.id !== null) {
      this.subscribeToSaveResponse(this.memberExistingfacilityService.update(memberExistingfacility));
    } else {
      this.subscribeToSaveResponse(this.memberExistingfacilityService.create(memberExistingfacility));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMemberExistingfacility>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(memberExistingfacility: IMemberExistingfacility): void {
    this.memberExistingfacility = memberExistingfacility;
    this.memberExistingfacilityFormService.resetForm(this.editForm, memberExistingfacility);

    this.membersSharedCollection = this.memberService.addMemberToCollectionIfMissing<IMember>(
      this.membersSharedCollection,
      memberExistingfacility.member
    );
  }

  protected loadRelationshipsOptions(): void {
    this.memberService
      .query()
      .pipe(map((res: HttpResponse<IMember[]>) => res.body ?? []))
      .pipe(
        map((members: IMember[]) =>
          this.memberService.addMemberToCollectionIfMissing<IMember>(members, this.memberExistingfacility?.member)
        )
      )
      .subscribe((members: IMember[]) => (this.membersSharedCollection = members));
  }
}
