import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SecurityUserFormService } from './security-user-form.service';
import { SecurityUserService } from '../service/security-user.service';
import { ISecurityUser } from '../security-user.model';
import { IBranch } from 'app/entities/branch/branch.model';
import { BranchService } from 'app/entities/branch/service/branch.service';
import { ISecurityPermission } from 'app/entities/security-permission/security-permission.model';
import { SecurityPermissionService } from 'app/entities/security-permission/service/security-permission.service';
import { ISecurityRole } from 'app/entities/security-role/security-role.model';
import { SecurityRoleService } from 'app/entities/security-role/service/security-role.service';

import { SecurityUserUpdateComponent } from './security-user-update.component';

describe('SecurityUser Management Update Component', () => {
  let comp: SecurityUserUpdateComponent;
  let fixture: ComponentFixture<SecurityUserUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let securityUserFormService: SecurityUserFormService;
  let securityUserService: SecurityUserService;
  let branchService: BranchService;
  let securityPermissionService: SecurityPermissionService;
  let securityRoleService: SecurityRoleService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SecurityUserUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(SecurityUserUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SecurityUserUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    securityUserFormService = TestBed.inject(SecurityUserFormService);
    securityUserService = TestBed.inject(SecurityUserService);
    branchService = TestBed.inject(BranchService);
    securityPermissionService = TestBed.inject(SecurityPermissionService);
    securityRoleService = TestBed.inject(SecurityRoleService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Branch query and add missing value', () => {
      const securityUser: ISecurityUser = { id: 456 };
      const branch: IBranch = { id: 32712 };
      securityUser.branch = branch;

      const branchCollection: IBranch[] = [{ id: 4664 }];
      jest.spyOn(branchService, 'query').mockReturnValue(of(new HttpResponse({ body: branchCollection })));
      const additionalBranches = [branch];
      const expectedCollection: IBranch[] = [...additionalBranches, ...branchCollection];
      jest.spyOn(branchService, 'addBranchToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ securityUser });
      comp.ngOnInit();

      expect(branchService.query).toHaveBeenCalled();
      expect(branchService.addBranchToCollectionIfMissing).toHaveBeenCalledWith(
        branchCollection,
        ...additionalBranches.map(expect.objectContaining)
      );
      expect(comp.branchesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call SecurityPermission query and add missing value', () => {
      const securityUser: ISecurityUser = { id: 456 };
      const securityPermissions: ISecurityPermission[] = [{ id: 39938 }];
      securityUser.securityPermissions = securityPermissions;

      const securityPermissionCollection: ISecurityPermission[] = [{ id: 31986 }];
      jest.spyOn(securityPermissionService, 'query').mockReturnValue(of(new HttpResponse({ body: securityPermissionCollection })));
      const additionalSecurityPermissions = [...securityPermissions];
      const expectedCollection: ISecurityPermission[] = [...additionalSecurityPermissions, ...securityPermissionCollection];
      jest.spyOn(securityPermissionService, 'addSecurityPermissionToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ securityUser });
      comp.ngOnInit();

      expect(securityPermissionService.query).toHaveBeenCalled();
      expect(securityPermissionService.addSecurityPermissionToCollectionIfMissing).toHaveBeenCalledWith(
        securityPermissionCollection,
        ...additionalSecurityPermissions.map(expect.objectContaining)
      );
      expect(comp.securityPermissionsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call SecurityRole query and add missing value', () => {
      const securityUser: ISecurityUser = { id: 456 };
      const securityRoles: ISecurityRole[] = [{ id: 78752 }];
      securityUser.securityRoles = securityRoles;

      const securityRoleCollection: ISecurityRole[] = [{ id: 72788 }];
      jest.spyOn(securityRoleService, 'query').mockReturnValue(of(new HttpResponse({ body: securityRoleCollection })));
      const additionalSecurityRoles = [...securityRoles];
      const expectedCollection: ISecurityRole[] = [...additionalSecurityRoles, ...securityRoleCollection];
      jest.spyOn(securityRoleService, 'addSecurityRoleToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ securityUser });
      comp.ngOnInit();

      expect(securityRoleService.query).toHaveBeenCalled();
      expect(securityRoleService.addSecurityRoleToCollectionIfMissing).toHaveBeenCalledWith(
        securityRoleCollection,
        ...additionalSecurityRoles.map(expect.objectContaining)
      );
      expect(comp.securityRolesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const securityUser: ISecurityUser = { id: 456 };
      const branch: IBranch = { id: 36069 };
      securityUser.branch = branch;
      const securityPermission: ISecurityPermission = { id: 79826 };
      securityUser.securityPermissions = [securityPermission];
      const securityRole: ISecurityRole = { id: 91779 };
      securityUser.securityRoles = [securityRole];

      activatedRoute.data = of({ securityUser });
      comp.ngOnInit();

      expect(comp.branchesSharedCollection).toContain(branch);
      expect(comp.securityPermissionsSharedCollection).toContain(securityPermission);
      expect(comp.securityRolesSharedCollection).toContain(securityRole);
      expect(comp.securityUser).toEqual(securityUser);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISecurityUser>>();
      const securityUser = { id: 123 };
      jest.spyOn(securityUserFormService, 'getSecurityUser').mockReturnValue(securityUser);
      jest.spyOn(securityUserService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ securityUser });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: securityUser }));
      saveSubject.complete();

      // THEN
      expect(securityUserFormService.getSecurityUser).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(securityUserService.update).toHaveBeenCalledWith(expect.objectContaining(securityUser));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISecurityUser>>();
      const securityUser = { id: 123 };
      jest.spyOn(securityUserFormService, 'getSecurityUser').mockReturnValue({ id: null });
      jest.spyOn(securityUserService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ securityUser: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: securityUser }));
      saveSubject.complete();

      // THEN
      expect(securityUserFormService.getSecurityUser).toHaveBeenCalled();
      expect(securityUserService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISecurityUser>>();
      const securityUser = { id: 123 };
      jest.spyOn(securityUserService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ securityUser });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(securityUserService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareBranch', () => {
      it('Should forward to branchService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(branchService, 'compareBranch');
        comp.compareBranch(entity, entity2);
        expect(branchService.compareBranch).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareSecurityPermission', () => {
      it('Should forward to securityPermissionService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(securityPermissionService, 'compareSecurityPermission');
        comp.compareSecurityPermission(entity, entity2);
        expect(securityPermissionService.compareSecurityPermission).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareSecurityRole', () => {
      it('Should forward to securityRoleService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(securityRoleService, 'compareSecurityRole');
        comp.compareSecurityRole(entity, entity2);
        expect(securityRoleService.compareSecurityRole).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
