import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { MemberFormService, MemberFormGroup } from './member-form.service';
import { IMember } from '../member.model';
import { MemberService } from '../service/member.service';
import { IEnquiryDetails } from 'app/entities/enquiry-details/enquiry-details.model';
import { EnquiryDetailsService } from 'app/entities/enquiry-details/service/enquiry-details.service';
import { IBranch } from 'app/entities/branch/branch.model';
import { BranchService } from 'app/entities/branch/service/branch.service';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { SecurityUserService } from 'app/entities/security-user/service/security-user.service';
import { Title } from 'app/entities/enumerations/title.model';
import { Gender } from 'app/entities/enumerations/gender.model';
import { ResidentalStatus } from 'app/entities/enumerations/residental-status.model';
import { MaritalStatus } from 'app/entities/enumerations/marital-status.model';
import { Occupation } from 'app/entities/enumerations/occupation.model';
import { Status } from 'app/entities/enumerations/status.model';
import { LoanStatus } from 'app/entities/enumerations/loan-status.model';
import { StepperNumber } from 'app/entities/enumerations/stepper-number.model';

@Component({
  selector: 'jhi-member-update',
  templateUrl: './member-update.component.html',
})
export class MemberUpdateComponent implements OnInit {
  isSaving = false;
  member: IMember | null = null;
  titleValues = Object.keys(Title);
  genderValues = Object.keys(Gender);
  residentalStatusValues = Object.keys(ResidentalStatus);
  maritalStatusValues = Object.keys(MaritalStatus);
  occupationValues = Object.keys(Occupation);
  statusValues = Object.keys(Status);
  loanStatusValues = Object.keys(LoanStatus);
  stepperNumberValues = Object.keys(StepperNumber);

  enquiryDetailsSharedCollection: IEnquiryDetails[] = [];
  branchesSharedCollection: IBranch[] = [];
  membersSharedCollection: IMember[] = [];
  securityUsersSharedCollection: ISecurityUser[] = [];

  editForm: MemberFormGroup = this.memberFormService.createMemberFormGroup();

  constructor(
    protected memberService: MemberService,
    protected memberFormService: MemberFormService,
    protected enquiryDetailsService: EnquiryDetailsService,
    protected branchService: BranchService,
    protected securityUserService: SecurityUserService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareEnquiryDetails = (o1: IEnquiryDetails | null, o2: IEnquiryDetails | null): boolean =>
    this.enquiryDetailsService.compareEnquiryDetails(o1, o2);

  compareBranch = (o1: IBranch | null, o2: IBranch | null): boolean => this.branchService.compareBranch(o1, o2);

  compareMember = (o1: IMember | null, o2: IMember | null): boolean => this.memberService.compareMember(o1, o2);

  compareSecurityUser = (o1: ISecurityUser | null, o2: ISecurityUser | null): boolean =>
    this.securityUserService.compareSecurityUser(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ member }) => {
      this.member = member;
      if (member) {
        this.updateForm(member);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const member = this.memberFormService.getMember(this.editForm);
    if (member.id !== null) {
      this.subscribeToSaveResponse(this.memberService.update(member));
    } else {
      this.subscribeToSaveResponse(this.memberService.create(member));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMember>>): void {
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

  protected updateForm(member: IMember): void {
    this.member = member;
    this.memberFormService.resetForm(this.editForm, member);

    this.enquiryDetailsSharedCollection = this.enquiryDetailsService.addEnquiryDetailsToCollectionIfMissing<IEnquiryDetails>(
      this.enquiryDetailsSharedCollection,
      member.enquiryDetails
    );
    this.branchesSharedCollection = this.branchService.addBranchToCollectionIfMissing<IBranch>(
      this.branchesSharedCollection,
      member.branch
    );
    this.membersSharedCollection = this.memberService.addMemberToCollectionIfMissing<IMember>(this.membersSharedCollection, member.member);
    this.securityUsersSharedCollection = this.securityUserService.addSecurityUserToCollectionIfMissing<ISecurityUser>(
      this.securityUsersSharedCollection,
      member.securityUser
    );
  }

  protected loadRelationshipsOptions(): void {
    this.enquiryDetailsService
      .query()
      .pipe(map((res: HttpResponse<IEnquiryDetails[]>) => res.body ?? []))
      .pipe(
        map((enquiryDetails: IEnquiryDetails[]) =>
          this.enquiryDetailsService.addEnquiryDetailsToCollectionIfMissing<IEnquiryDetails>(enquiryDetails, this.member?.enquiryDetails)
        )
      )
      .subscribe((enquiryDetails: IEnquiryDetails[]) => (this.enquiryDetailsSharedCollection = enquiryDetails));

    this.branchService
      .query()
      .pipe(map((res: HttpResponse<IBranch[]>) => res.body ?? []))
      .pipe(map((branches: IBranch[]) => this.branchService.addBranchToCollectionIfMissing<IBranch>(branches, this.member?.branch)))
      .subscribe((branches: IBranch[]) => (this.branchesSharedCollection = branches));

    this.memberService
      .query()
      .pipe(map((res: HttpResponse<IMember[]>) => res.body ?? []))
      .pipe(map((members: IMember[]) => this.memberService.addMemberToCollectionIfMissing<IMember>(members, this.member?.member)))
      .subscribe((members: IMember[]) => (this.membersSharedCollection = members));

    this.securityUserService
      .query()
      .pipe(map((res: HttpResponse<ISecurityUser[]>) => res.body ?? []))
      .pipe(
        map((securityUsers: ISecurityUser[]) =>
          this.securityUserService.addSecurityUserToCollectionIfMissing<ISecurityUser>(securityUsers, this.member?.securityUser)
        )
      )
      .subscribe((securityUsers: ISecurityUser[]) => (this.securityUsersSharedCollection = securityUsers));
  }
}
