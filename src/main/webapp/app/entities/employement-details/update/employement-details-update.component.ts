import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { EmployementDetailsFormService, EmployementDetailsFormGroup } from './employement-details-form.service';
import { IEmployementDetails } from '../employement-details.model';
import { EmployementDetailsService } from '../service/employement-details.service';
import { IMember } from 'app/entities/member/member.model';
import { MemberService } from 'app/entities/member/service/member.service';
import { Occupation } from 'app/entities/enumerations/occupation.model';
import { EmployementStatus } from 'app/entities/enumerations/employement-status.model';
import { CompanyType } from 'app/entities/enumerations/company-type.model';
import { ConstitutionType } from 'app/entities/enumerations/constitution-type.model';
import { IndustryType } from 'app/entities/enumerations/industry-type.model';

@Component({
  selector: 'jhi-employement-details-update',
  templateUrl: './employement-details-update.component.html',
})
export class EmployementDetailsUpdateComponent implements OnInit {
  isSaving = false;
  employementDetails: IEmployementDetails | null = null;
  occupationValues = Object.keys(Occupation);
  employementStatusValues = Object.keys(EmployementStatus);
  companyTypeValues = Object.keys(CompanyType);
  constitutionTypeValues = Object.keys(ConstitutionType);
  industryTypeValues = Object.keys(IndustryType);

  membersSharedCollection: IMember[] = [];

  editForm: EmployementDetailsFormGroup = this.employementDetailsFormService.createEmployementDetailsFormGroup();

  constructor(
    protected employementDetailsService: EmployementDetailsService,
    protected employementDetailsFormService: EmployementDetailsFormService,
    protected memberService: MemberService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareMember = (o1: IMember | null, o2: IMember | null): boolean => this.memberService.compareMember(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employementDetails }) => {
      this.employementDetails = employementDetails;
      if (employementDetails) {
        this.updateForm(employementDetails);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const employementDetails = this.employementDetailsFormService.getEmployementDetails(this.editForm);
    if (employementDetails.id !== null) {
      this.subscribeToSaveResponse(this.employementDetailsService.update(employementDetails));
    } else {
      this.subscribeToSaveResponse(this.employementDetailsService.create(employementDetails));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployementDetails>>): void {
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

  protected updateForm(employementDetails: IEmployementDetails): void {
    this.employementDetails = employementDetails;
    this.employementDetailsFormService.resetForm(this.editForm, employementDetails);

    this.membersSharedCollection = this.memberService.addMemberToCollectionIfMissing<IMember>(
      this.membersSharedCollection,
      employementDetails.member
    );
  }

  protected loadRelationshipsOptions(): void {
    this.memberService
      .query()
      .pipe(map((res: HttpResponse<IMember[]>) => res.body ?? []))
      .pipe(
        map((members: IMember[]) => this.memberService.addMemberToCollectionIfMissing<IMember>(members, this.employementDetails?.member))
      )
      .subscribe((members: IMember[]) => (this.membersSharedCollection = members));
  }
}
