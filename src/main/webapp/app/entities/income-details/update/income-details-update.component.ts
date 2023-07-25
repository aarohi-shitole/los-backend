import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IncomeDetailsFormService, IncomeDetailsFormGroup } from './income-details-form.service';
import { IIncomeDetails } from '../income-details.model';
import { IncomeDetailsService } from '../service/income-details.service';
import { IMember } from 'app/entities/member/member.model';
import { MemberService } from 'app/entities/member/service/member.service';
import { IncomeType } from 'app/entities/enumerations/income-type.model';

@Component({
  selector: 'jhi-income-details-update',
  templateUrl: './income-details-update.component.html',
})
export class IncomeDetailsUpdateComponent implements OnInit {
  isSaving = false;
  incomeDetails: IIncomeDetails | null = null;
  incomeTypeValues = Object.keys(IncomeType);

  membersSharedCollection: IMember[] = [];

  editForm: IncomeDetailsFormGroup = this.incomeDetailsFormService.createIncomeDetailsFormGroup();

  constructor(
    protected incomeDetailsService: IncomeDetailsService,
    protected incomeDetailsFormService: IncomeDetailsFormService,
    protected memberService: MemberService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareMember = (o1: IMember | null, o2: IMember | null): boolean => this.memberService.compareMember(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ incomeDetails }) => {
      this.incomeDetails = incomeDetails;
      if (incomeDetails) {
        this.updateForm(incomeDetails);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const incomeDetails = this.incomeDetailsFormService.getIncomeDetails(this.editForm);
    if (incomeDetails.id !== null) {
      this.subscribeToSaveResponse(this.incomeDetailsService.update(incomeDetails));
    } else {
      this.subscribeToSaveResponse(this.incomeDetailsService.create(incomeDetails));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIncomeDetails>>): void {
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

  protected updateForm(incomeDetails: IIncomeDetails): void {
    this.incomeDetails = incomeDetails;
    this.incomeDetailsFormService.resetForm(this.editForm, incomeDetails);

    this.membersSharedCollection = this.memberService.addMemberToCollectionIfMissing<IMember>(
      this.membersSharedCollection,
      incomeDetails.member
    );
  }

  protected loadRelationshipsOptions(): void {
    this.memberService
      .query()
      .pipe(map((res: HttpResponse<IMember[]>) => res.body ?? []))
      .pipe(map((members: IMember[]) => this.memberService.addMemberToCollectionIfMissing<IMember>(members, this.incomeDetails?.member)))
      .subscribe((members: IMember[]) => (this.membersSharedCollection = members));
  }
}
