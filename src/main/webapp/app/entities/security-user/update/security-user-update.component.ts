import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { SecurityUserFormService, SecurityUserFormGroup } from './security-user-form.service';
import { ISecurityUser } from '../security-user.model';
import { SecurityUserService } from '../service/security-user.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IBranch } from 'app/entities/branch/branch.model';
import { BranchService } from 'app/entities/branch/service/branch.service';
import { ISecurityPermission } from 'app/entities/security-permission/security-permission.model';
import { SecurityPermissionService } from 'app/entities/security-permission/service/security-permission.service';
import { ISecurityRole } from 'app/entities/security-role/security-role.model';
import { SecurityRoleService } from 'app/entities/security-role/service/security-role.service';

@Component({
  selector: 'jhi-security-user-update',
  templateUrl: './security-user-update.component.html',
})
export class SecurityUserUpdateComponent implements OnInit {
  isSaving = false;
  securityUser: ISecurityUser | null = null;

  branchesSharedCollection: IBranch[] = [];
  securityPermissionsSharedCollection: ISecurityPermission[] = [];
  securityRolesSharedCollection: ISecurityRole[] = [];

  editForm: SecurityUserFormGroup = this.securityUserFormService.createSecurityUserFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected securityUserService: SecurityUserService,
    protected securityUserFormService: SecurityUserFormService,
    protected branchService: BranchService,
    protected securityPermissionService: SecurityPermissionService,
    protected securityRoleService: SecurityRoleService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareBranch = (o1: IBranch | null, o2: IBranch | null): boolean => this.branchService.compareBranch(o1, o2);

  compareSecurityPermission = (o1: ISecurityPermission | null, o2: ISecurityPermission | null): boolean =>
    this.securityPermissionService.compareSecurityPermission(o1, o2);

  compareSecurityRole = (o1: ISecurityRole | null, o2: ISecurityRole | null): boolean =>
    this.securityRoleService.compareSecurityRole(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ securityUser }) => {
      this.securityUser = securityUser;
      if (securityUser) {
        this.updateForm(securityUser);
      }

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(
          new EventWithContent<AlertError>('loanOrignationSystemApp.error', { ...err, key: 'error.file.' + err.key })
        ),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const securityUser = this.securityUserFormService.getSecurityUser(this.editForm);
    if (securityUser.id !== null) {
      this.subscribeToSaveResponse(this.securityUserService.update(securityUser));
    } else {
      this.subscribeToSaveResponse(this.securityUserService.create(securityUser));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISecurityUser>>): void {
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

  protected updateForm(securityUser: ISecurityUser): void {
    this.securityUser = securityUser;
    this.securityUserFormService.resetForm(this.editForm, securityUser);

    this.branchesSharedCollection = this.branchService.addBranchToCollectionIfMissing<IBranch>(
      this.branchesSharedCollection,
      securityUser.branch
    );
    this.securityPermissionsSharedCollection =
      this.securityPermissionService.addSecurityPermissionToCollectionIfMissing<ISecurityPermission>(
        this.securityPermissionsSharedCollection,
        ...(securityUser.securityPermissions ?? [])
      );
    this.securityRolesSharedCollection = this.securityRoleService.addSecurityRoleToCollectionIfMissing<ISecurityRole>(
      this.securityRolesSharedCollection,
      ...(securityUser.securityRoles ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.branchService
      .query()
      .pipe(map((res: HttpResponse<IBranch[]>) => res.body ?? []))
      .pipe(map((branches: IBranch[]) => this.branchService.addBranchToCollectionIfMissing<IBranch>(branches, this.securityUser?.branch)))
      .subscribe((branches: IBranch[]) => (this.branchesSharedCollection = branches));

    this.securityPermissionService
      .query()
      .pipe(map((res: HttpResponse<ISecurityPermission[]>) => res.body ?? []))
      .pipe(
        map((securityPermissions: ISecurityPermission[]) =>
          this.securityPermissionService.addSecurityPermissionToCollectionIfMissing<ISecurityPermission>(
            securityPermissions,
            ...(this.securityUser?.securityPermissions ?? [])
          )
        )
      )
      .subscribe((securityPermissions: ISecurityPermission[]) => (this.securityPermissionsSharedCollection = securityPermissions));

    this.securityRoleService
      .query()
      .pipe(map((res: HttpResponse<ISecurityRole[]>) => res.body ?? []))
      .pipe(
        map((securityRoles: ISecurityRole[]) =>
          this.securityRoleService.addSecurityRoleToCollectionIfMissing<ISecurityRole>(
            securityRoles,
            ...(this.securityUser?.securityRoles ?? [])
          )
        )
      )
      .subscribe((securityRoles: ISecurityRole[]) => (this.securityRolesSharedCollection = securityRoles));
  }
}
