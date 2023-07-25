import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { MemberLimitFormService, MemberLimitFormGroup } from './member-limit-form.service';
import { IMemberLimit } from '../member-limit.model';
import { MemberLimitService } from '../service/member-limit.service';

@Component({
  selector: 'jhi-member-limit-update',
  templateUrl: './member-limit-update.component.html',
})
export class MemberLimitUpdateComponent implements OnInit {
  isSaving = false;
  memberLimit: IMemberLimit | null = null;

  editForm: MemberLimitFormGroup = this.memberLimitFormService.createMemberLimitFormGroup();

  constructor(
    protected memberLimitService: MemberLimitService,
    protected memberLimitFormService: MemberLimitFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ memberLimit }) => {
      this.memberLimit = memberLimit;
      if (memberLimit) {
        this.updateForm(memberLimit);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const memberLimit = this.memberLimitFormService.getMemberLimit(this.editForm);
    if (memberLimit.id !== null) {
      this.subscribeToSaveResponse(this.memberLimitService.update(memberLimit));
    } else {
      this.subscribeToSaveResponse(this.memberLimitService.create(memberLimit));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMemberLimit>>): void {
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

  protected updateForm(memberLimit: IMemberLimit): void {
    this.memberLimit = memberLimit;
    this.memberLimitFormService.resetForm(this.editForm, memberLimit);
  }
}
