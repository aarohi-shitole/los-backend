import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { RemarkHistoryFormService, RemarkHistoryFormGroup } from './remark-history-form.service';
import { IRemarkHistory } from '../remark-history.model';
import { RemarkHistoryService } from '../service/remark-history.service';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { SecurityUserService } from 'app/entities/security-user/service/security-user.service';
import { ILoanApplications } from 'app/entities/loan-applications/loan-applications.model';
import { LoanApplicationsService } from 'app/entities/loan-applications/service/loan-applications.service';

@Component({
  selector: 'jhi-remark-history-update',
  templateUrl: './remark-history-update.component.html',
})
export class RemarkHistoryUpdateComponent implements OnInit {
  isSaving = false;
  remarkHistory: IRemarkHistory | null = null;

  securityUsersSharedCollection: ISecurityUser[] = [];
  loanApplicationsSharedCollection: ILoanApplications[] = [];

  editForm: RemarkHistoryFormGroup = this.remarkHistoryFormService.createRemarkHistoryFormGroup();

  constructor(
    protected remarkHistoryService: RemarkHistoryService,
    protected remarkHistoryFormService: RemarkHistoryFormService,
    protected securityUserService: SecurityUserService,
    protected loanApplicationsService: LoanApplicationsService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareSecurityUser = (o1: ISecurityUser | null, o2: ISecurityUser | null): boolean =>
    this.securityUserService.compareSecurityUser(o1, o2);

  compareLoanApplications = (o1: ILoanApplications | null, o2: ILoanApplications | null): boolean =>
    this.loanApplicationsService.compareLoanApplications(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ remarkHistory }) => {
      this.remarkHistory = remarkHistory;
      if (remarkHistory) {
        this.updateForm(remarkHistory);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const remarkHistory = this.remarkHistoryFormService.getRemarkHistory(this.editForm);
    if (remarkHistory.id !== null) {
      this.subscribeToSaveResponse(this.remarkHistoryService.update(remarkHistory));
    } else {
      this.subscribeToSaveResponse(this.remarkHistoryService.create(remarkHistory));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRemarkHistory>>): void {
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

  protected updateForm(remarkHistory: IRemarkHistory): void {
    this.remarkHistory = remarkHistory;
    this.remarkHistoryFormService.resetForm(this.editForm, remarkHistory);

    this.securityUsersSharedCollection = this.securityUserService.addSecurityUserToCollectionIfMissing<ISecurityUser>(
      this.securityUsersSharedCollection,
      remarkHistory.securityUser
    );
    this.loanApplicationsSharedCollection = this.loanApplicationsService.addLoanApplicationsToCollectionIfMissing<ILoanApplications>(
      this.loanApplicationsSharedCollection,
      remarkHistory.loanApplications
    );
  }

  protected loadRelationshipsOptions(): void {
    this.securityUserService
      .query()
      .pipe(map((res: HttpResponse<ISecurityUser[]>) => res.body ?? []))
      .pipe(
        map((securityUsers: ISecurityUser[]) =>
          this.securityUserService.addSecurityUserToCollectionIfMissing<ISecurityUser>(securityUsers, this.remarkHistory?.securityUser)
        )
      )
      .subscribe((securityUsers: ISecurityUser[]) => (this.securityUsersSharedCollection = securityUsers));

    this.loanApplicationsService
      .query()
      .pipe(map((res: HttpResponse<ILoanApplications[]>) => res.body ?? []))
      .pipe(
        map((loanApplications: ILoanApplications[]) =>
          this.loanApplicationsService.addLoanApplicationsToCollectionIfMissing<ILoanApplications>(
            loanApplications,
            this.remarkHistory?.loanApplications
          )
        )
      )
      .subscribe((loanApplications: ILoanApplications[]) => (this.loanApplicationsSharedCollection = loanApplications));
  }
}
