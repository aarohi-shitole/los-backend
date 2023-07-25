import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { SequenceGeneratorFormService, SequenceGeneratorFormGroup } from './sequence-generator-form.service';
import { ISequenceGenerator } from '../sequence-generator.model';
import { SequenceGeneratorService } from '../service/sequence-generator.service';
import { IBranch } from 'app/entities/branch/branch.model';
import { BranchService } from 'app/entities/branch/service/branch.service';

@Component({
  selector: 'jhi-sequence-generator-update',
  templateUrl: './sequence-generator-update.component.html',
})
export class SequenceGeneratorUpdateComponent implements OnInit {
  isSaving = false;
  sequenceGenerator: ISequenceGenerator | null = null;

  branchesSharedCollection: IBranch[] = [];

  editForm: SequenceGeneratorFormGroup = this.sequenceGeneratorFormService.createSequenceGeneratorFormGroup();

  constructor(
    protected sequenceGeneratorService: SequenceGeneratorService,
    protected sequenceGeneratorFormService: SequenceGeneratorFormService,
    protected branchService: BranchService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareBranch = (o1: IBranch | null, o2: IBranch | null): boolean => this.branchService.compareBranch(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sequenceGenerator }) => {
      this.sequenceGenerator = sequenceGenerator;
      if (sequenceGenerator) {
        this.updateForm(sequenceGenerator);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sequenceGenerator = this.sequenceGeneratorFormService.getSequenceGenerator(this.editForm);
    if (sequenceGenerator.id !== null) {
      this.subscribeToSaveResponse(this.sequenceGeneratorService.update(sequenceGenerator));
    } else {
      this.subscribeToSaveResponse(this.sequenceGeneratorService.create(sequenceGenerator));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISequenceGenerator>>): void {
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

  protected updateForm(sequenceGenerator: ISequenceGenerator): void {
    this.sequenceGenerator = sequenceGenerator;
    this.sequenceGeneratorFormService.resetForm(this.editForm, sequenceGenerator);

    this.branchesSharedCollection = this.branchService.addBranchToCollectionIfMissing<IBranch>(
      this.branchesSharedCollection,
      sequenceGenerator.branch
    );
  }

  protected loadRelationshipsOptions(): void {
    this.branchService
      .query()
      .pipe(map((res: HttpResponse<IBranch[]>) => res.body ?? []))
      .pipe(
        map((branches: IBranch[]) => this.branchService.addBranchToCollectionIfMissing<IBranch>(branches, this.sequenceGenerator?.branch))
      )
      .subscribe((branches: IBranch[]) => (this.branchesSharedCollection = branches));
  }
}
