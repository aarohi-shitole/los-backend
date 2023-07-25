import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { GuarantorFormService, GuarantorFormGroup } from './guarantor-form.service';
import { IGuarantor } from '../guarantor.model';
import { GuarantorService } from '../service/guarantor.service';
import { IMemberAssets } from 'app/entities/member-assets/member-assets.model';
import { MemberAssetsService } from 'app/entities/member-assets/service/member-assets.service';
import { IEmployementDetails } from 'app/entities/employement-details/employement-details.model';
import { EmployementDetailsService } from 'app/entities/employement-details/service/employement-details.service';
import { IMember } from 'app/entities/member/member.model';
import { MemberService } from 'app/entities/member/service/member.service';
import { Title } from 'app/entities/enumerations/title.model';
import { Gender } from 'app/entities/enumerations/gender.model';
import { Occupation } from 'app/entities/enumerations/occupation.model';
import { MaritalStatus } from 'app/entities/enumerations/marital-status.model';

@Component({
  selector: 'jhi-guarantor-update',
  templateUrl: './guarantor-update.component.html',
})
export class GuarantorUpdateComponent implements OnInit {
  isSaving = false;
  guarantor: IGuarantor | null = null;
  titleValues = Object.keys(Title);
  genderValues = Object.keys(Gender);
  occupationValues = Object.keys(Occupation);
  maritalStatusValues = Object.keys(MaritalStatus);

  memberAssetsSharedCollection: IMemberAssets[] = [];
  employementDetailsSharedCollection: IEmployementDetails[] = [];
  membersSharedCollection: IMember[] = [];

  editForm: GuarantorFormGroup = this.guarantorFormService.createGuarantorFormGroup();

  constructor(
    protected guarantorService: GuarantorService,
    protected guarantorFormService: GuarantorFormService,
    protected memberAssetsService: MemberAssetsService,
    protected employementDetailsService: EmployementDetailsService,
    protected memberService: MemberService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareMemberAssets = (o1: IMemberAssets | null, o2: IMemberAssets | null): boolean =>
    this.memberAssetsService.compareMemberAssets(o1, o2);

  compareEmployementDetails = (o1: IEmployementDetails | null, o2: IEmployementDetails | null): boolean =>
    this.employementDetailsService.compareEmployementDetails(o1, o2);

  compareMember = (o1: IMember | null, o2: IMember | null): boolean => this.memberService.compareMember(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ guarantor }) => {
      this.guarantor = guarantor;
      if (guarantor) {
        this.updateForm(guarantor);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const guarantor = this.guarantorFormService.getGuarantor(this.editForm);
    if (guarantor.id !== null) {
      this.subscribeToSaveResponse(this.guarantorService.update(guarantor));
    } else {
      this.subscribeToSaveResponse(this.guarantorService.create(guarantor));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGuarantor>>): void {
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

  protected updateForm(guarantor: IGuarantor): void {
    this.guarantor = guarantor;
    this.guarantorFormService.resetForm(this.editForm, guarantor);

    this.memberAssetsSharedCollection = this.memberAssetsService.addMemberAssetsToCollectionIfMissing<IMemberAssets>(
      this.memberAssetsSharedCollection,
      guarantor.memberAssets
    );
    this.employementDetailsSharedCollection =
      this.employementDetailsService.addEmployementDetailsToCollectionIfMissing<IEmployementDetails>(
        this.employementDetailsSharedCollection,
        guarantor.employementDetails
      );
    this.membersSharedCollection = this.memberService.addMemberToCollectionIfMissing<IMember>(
      this.membersSharedCollection,
      guarantor.member
    );
  }

  protected loadRelationshipsOptions(): void {
    this.memberAssetsService
      .query()
      .pipe(map((res: HttpResponse<IMemberAssets[]>) => res.body ?? []))
      .pipe(
        map((memberAssets: IMemberAssets[]) =>
          this.memberAssetsService.addMemberAssetsToCollectionIfMissing<IMemberAssets>(memberAssets, this.guarantor?.memberAssets)
        )
      )
      .subscribe((memberAssets: IMemberAssets[]) => (this.memberAssetsSharedCollection = memberAssets));

    this.employementDetailsService
      .query()
      .pipe(map((res: HttpResponse<IEmployementDetails[]>) => res.body ?? []))
      .pipe(
        map((employementDetails: IEmployementDetails[]) =>
          this.employementDetailsService.addEmployementDetailsToCollectionIfMissing<IEmployementDetails>(
            employementDetails,
            this.guarantor?.employementDetails
          )
        )
      )
      .subscribe((employementDetails: IEmployementDetails[]) => (this.employementDetailsSharedCollection = employementDetails));

    this.memberService
      .query()
      .pipe(map((res: HttpResponse<IMember[]>) => res.body ?? []))
      .pipe(map((members: IMember[]) => this.memberService.addMemberToCollectionIfMissing<IMember>(members, this.guarantor?.member)))
      .subscribe((members: IMember[]) => (this.membersSharedCollection = members));
  }
}
