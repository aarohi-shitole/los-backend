import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { MemberBankFormService, MemberBankFormGroup } from './member-bank-form.service';
import { IMemberBank } from '../member-bank.model';
import { MemberBankService } from '../service/member-bank.service';
import { IMember } from 'app/entities/member/member.model';
import { MemberService } from 'app/entities/member/service/member.service';

@Component({
  selector: 'jhi-member-bank-update',
  templateUrl: './member-bank-update.component.html',
})
export class MemberBankUpdateComponent implements OnInit {
  isSaving = false;
  memberBank: IMemberBank | null = null;

  membersSharedCollection: IMember[] = [];

  editForm: MemberBankFormGroup = this.memberBankFormService.createMemberBankFormGroup();

  constructor(
    protected memberBankService: MemberBankService,
    protected memberBankFormService: MemberBankFormService,
    protected memberService: MemberService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareMember = (o1: IMember | null, o2: IMember | null): boolean => this.memberService.compareMember(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ memberBank }) => {
      this.memberBank = memberBank;
      if (memberBank) {
        this.updateForm(memberBank);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const memberBank = this.memberBankFormService.getMemberBank(this.editForm);
    if (memberBank.id !== null) {
      this.subscribeToSaveResponse(this.memberBankService.update(memberBank));
    } else {
      this.subscribeToSaveResponse(this.memberBankService.create(memberBank));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMemberBank>>): void {
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

  protected updateForm(memberBank: IMemberBank): void {
    this.memberBank = memberBank;
    this.memberBankFormService.resetForm(this.editForm, memberBank);

    this.membersSharedCollection = this.memberService.addMemberToCollectionIfMissing<IMember>(
      this.membersSharedCollection,
      memberBank.member
    );
  }

  protected loadRelationshipsOptions(): void {
    this.memberService
      .query()
      .pipe(map((res: HttpResponse<IMember[]>) => res.body ?? []))
      .pipe(map((members: IMember[]) => this.memberService.addMemberToCollectionIfMissing<IMember>(members, this.memberBank?.member)))
      .subscribe((members: IMember[]) => (this.membersSharedCollection = members));
  }
}
