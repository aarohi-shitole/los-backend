import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { MemberAssetsFormService, MemberAssetsFormGroup } from './member-assets-form.service';
import { IMemberAssets } from '../member-assets.model';
import { MemberAssetsService } from '../service/member-assets.service';
import { IMember } from 'app/entities/member/member.model';
import { MemberService } from 'app/entities/member/service/member.service';
import { ILoanApplications } from 'app/entities/loan-applications/loan-applications.model';
import { LoanApplicationsService } from 'app/entities/loan-applications/service/loan-applications.service';
import { AssetType } from 'app/entities/enumerations/asset-type.model';
import { AssetNature } from 'app/entities/enumerations/asset-nature.model';

@Component({
  selector: 'jhi-member-assets-update',
  templateUrl: './member-assets-update.component.html',
})
export class MemberAssetsUpdateComponent implements OnInit {
  isSaving = false;
  memberAssets: IMemberAssets | null = null;
  assetTypeValues = Object.keys(AssetType);
  assetNatureValues = Object.keys(AssetNature);

  membersSharedCollection: IMember[] = [];
  loanApplicationsSharedCollection: ILoanApplications[] = [];

  editForm: MemberAssetsFormGroup = this.memberAssetsFormService.createMemberAssetsFormGroup();

  constructor(
    protected memberAssetsService: MemberAssetsService,
    protected memberAssetsFormService: MemberAssetsFormService,
    protected memberService: MemberService,
    protected loanApplicationsService: LoanApplicationsService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareMember = (o1: IMember | null, o2: IMember | null): boolean => this.memberService.compareMember(o1, o2);

  compareLoanApplications = (o1: ILoanApplications | null, o2: ILoanApplications | null): boolean =>
    this.loanApplicationsService.compareLoanApplications(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ memberAssets }) => {
      this.memberAssets = memberAssets;
      if (memberAssets) {
        this.updateForm(memberAssets);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const memberAssets = this.memberAssetsFormService.getMemberAssets(this.editForm);
    if (memberAssets.id !== null) {
      this.subscribeToSaveResponse(this.memberAssetsService.update(memberAssets));
    } else {
      this.subscribeToSaveResponse(this.memberAssetsService.create(memberAssets));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMemberAssets>>): void {
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

  protected updateForm(memberAssets: IMemberAssets): void {
    this.memberAssets = memberAssets;
    this.memberAssetsFormService.resetForm(this.editForm, memberAssets);

    this.membersSharedCollection = this.memberService.addMemberToCollectionIfMissing<IMember>(
      this.membersSharedCollection,
      memberAssets.member
    );
    this.loanApplicationsSharedCollection = this.loanApplicationsService.addLoanApplicationsToCollectionIfMissing<ILoanApplications>(
      this.loanApplicationsSharedCollection,
      memberAssets.loanApplications
    );
  }

  protected loadRelationshipsOptions(): void {
    this.memberService
      .query()
      .pipe(map((res: HttpResponse<IMember[]>) => res.body ?? []))
      .pipe(map((members: IMember[]) => this.memberService.addMemberToCollectionIfMissing<IMember>(members, this.memberAssets?.member)))
      .subscribe((members: IMember[]) => (this.membersSharedCollection = members));

    this.loanApplicationsService
      .query()
      .pipe(map((res: HttpResponse<ILoanApplications[]>) => res.body ?? []))
      .pipe(
        map((loanApplications: ILoanApplications[]) =>
          this.loanApplicationsService.addLoanApplicationsToCollectionIfMissing<ILoanApplications>(
            loanApplications,
            this.memberAssets?.loanApplications
          )
        )
      )
      .subscribe((loanApplications: ILoanApplications[]) => (this.loanApplicationsSharedCollection = loanApplications));
  }
}
