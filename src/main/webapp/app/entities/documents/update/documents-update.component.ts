import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { DocumentsFormService, DocumentsFormGroup } from './documents-form.service';
import { IDocuments } from '../documents.model';
import { DocumentsService } from '../service/documents.service';
import { IMember } from 'app/entities/member/member.model';
import { MemberService } from 'app/entities/member/service/member.service';
import { IGuarantor } from 'app/entities/guarantor/guarantor.model';
import { GuarantorService } from 'app/entities/guarantor/service/guarantor.service';

@Component({
  selector: 'jhi-documents-update',
  templateUrl: './documents-update.component.html',
})
export class DocumentsUpdateComponent implements OnInit {
  isSaving = false;
  documents: IDocuments | null = null;

  membersSharedCollection: IMember[] = [];
  guarantorsSharedCollection: IGuarantor[] = [];

  editForm: DocumentsFormGroup = this.documentsFormService.createDocumentsFormGroup();

  constructor(
    protected documentsService: DocumentsService,
    protected documentsFormService: DocumentsFormService,
    protected memberService: MemberService,
    protected guarantorService: GuarantorService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareMember = (o1: IMember | null, o2: IMember | null): boolean => this.memberService.compareMember(o1, o2);

  compareGuarantor = (o1: IGuarantor | null, o2: IGuarantor | null): boolean => this.guarantorService.compareGuarantor(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ documents }) => {
      this.documents = documents;
      if (documents) {
        this.updateForm(documents);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const documents = this.documentsFormService.getDocuments(this.editForm);
    if (documents.id !== null) {
      this.subscribeToSaveResponse(this.documentsService.update(documents));
    } else {
      this.subscribeToSaveResponse(this.documentsService.create(documents));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocuments>>): void {
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

  protected updateForm(documents: IDocuments): void {
    this.documents = documents;
    this.documentsFormService.resetForm(this.editForm, documents);

    this.membersSharedCollection = this.memberService.addMemberToCollectionIfMissing<IMember>(
      this.membersSharedCollection,
      documents.member
    );
    this.guarantorsSharedCollection = this.guarantorService.addGuarantorToCollectionIfMissing<IGuarantor>(
      this.guarantorsSharedCollection,
      documents.guarantor
    );
  }

  protected loadRelationshipsOptions(): void {
    this.memberService
      .query()
      .pipe(map((res: HttpResponse<IMember[]>) => res.body ?? []))
      .pipe(map((members: IMember[]) => this.memberService.addMemberToCollectionIfMissing<IMember>(members, this.documents?.member)))
      .subscribe((members: IMember[]) => (this.membersSharedCollection = members));

    this.guarantorService
      .query()
      .pipe(map((res: HttpResponse<IGuarantor[]>) => res.body ?? []))
      .pipe(
        map((guarantors: IGuarantor[]) =>
          this.guarantorService.addGuarantorToCollectionIfMissing<IGuarantor>(guarantors, this.documents?.guarantor)
        )
      )
      .subscribe((guarantors: IGuarantor[]) => (this.guarantorsSharedCollection = guarantors));
  }
}
