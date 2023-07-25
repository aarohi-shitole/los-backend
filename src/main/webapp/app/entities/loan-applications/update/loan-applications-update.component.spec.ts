import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LoanApplicationsFormService } from './loan-applications-form.service';
import { LoanApplicationsService } from '../service/loan-applications.service';
import { ILoanApplications } from '../loan-applications.model';
import { IMember } from 'app/entities/member/member.model';
import { MemberService } from 'app/entities/member/service/member.service';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { SecurityUserService } from 'app/entities/security-user/service/security-user.service';
import { IProduct } from 'app/entities/product/product.model';
import { ProductService } from 'app/entities/product/service/product.service';

import { LoanApplicationsUpdateComponent } from './loan-applications-update.component';

describe('LoanApplications Management Update Component', () => {
  let comp: LoanApplicationsUpdateComponent;
  let fixture: ComponentFixture<LoanApplicationsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let loanApplicationsFormService: LoanApplicationsFormService;
  let loanApplicationsService: LoanApplicationsService;
  let memberService: MemberService;
  let securityUserService: SecurityUserService;
  let productService: ProductService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LoanApplicationsUpdateComponent],
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
      .overrideTemplate(LoanApplicationsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LoanApplicationsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    loanApplicationsFormService = TestBed.inject(LoanApplicationsFormService);
    loanApplicationsService = TestBed.inject(LoanApplicationsService);
    memberService = TestBed.inject(MemberService);
    securityUserService = TestBed.inject(SecurityUserService);
    productService = TestBed.inject(ProductService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Member query and add missing value', () => {
      const loanApplications: ILoanApplications = { id: 456 };
      const member: IMember = { id: 79181 };
      loanApplications.member = member;

      const memberCollection: IMember[] = [{ id: 9443 }];
      jest.spyOn(memberService, 'query').mockReturnValue(of(new HttpResponse({ body: memberCollection })));
      const additionalMembers = [member];
      const expectedCollection: IMember[] = [...additionalMembers, ...memberCollection];
      jest.spyOn(memberService, 'addMemberToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ loanApplications });
      comp.ngOnInit();

      expect(memberService.query).toHaveBeenCalled();
      expect(memberService.addMemberToCollectionIfMissing).toHaveBeenCalledWith(
        memberCollection,
        ...additionalMembers.map(expect.objectContaining)
      );
      expect(comp.membersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call SecurityUser query and add missing value', () => {
      const loanApplications: ILoanApplications = { id: 456 };
      const securityUser: ISecurityUser = { id: 54435 };
      loanApplications.securityUser = securityUser;

      const securityUserCollection: ISecurityUser[] = [{ id: 10328 }];
      jest.spyOn(securityUserService, 'query').mockReturnValue(of(new HttpResponse({ body: securityUserCollection })));
      const additionalSecurityUsers = [securityUser];
      const expectedCollection: ISecurityUser[] = [...additionalSecurityUsers, ...securityUserCollection];
      jest.spyOn(securityUserService, 'addSecurityUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ loanApplications });
      comp.ngOnInit();

      expect(securityUserService.query).toHaveBeenCalled();
      expect(securityUserService.addSecurityUserToCollectionIfMissing).toHaveBeenCalledWith(
        securityUserCollection,
        ...additionalSecurityUsers.map(expect.objectContaining)
      );
      expect(comp.securityUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Product query and add missing value', () => {
      const loanApplications: ILoanApplications = { id: 456 };
      const product: IProduct = { id: 81530 };
      loanApplications.product = product;

      const productCollection: IProduct[] = [{ id: 81021 }];
      jest.spyOn(productService, 'query').mockReturnValue(of(new HttpResponse({ body: productCollection })));
      const additionalProducts = [product];
      const expectedCollection: IProduct[] = [...additionalProducts, ...productCollection];
      jest.spyOn(productService, 'addProductToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ loanApplications });
      comp.ngOnInit();

      expect(productService.query).toHaveBeenCalled();
      expect(productService.addProductToCollectionIfMissing).toHaveBeenCalledWith(
        productCollection,
        ...additionalProducts.map(expect.objectContaining)
      );
      expect(comp.productsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const loanApplications: ILoanApplications = { id: 456 };
      const member: IMember = { id: 18666 };
      loanApplications.member = member;
      const securityUser: ISecurityUser = { id: 38173 };
      loanApplications.securityUser = securityUser;
      const product: IProduct = { id: 88833 };
      loanApplications.product = product;

      activatedRoute.data = of({ loanApplications });
      comp.ngOnInit();

      expect(comp.membersSharedCollection).toContain(member);
      expect(comp.securityUsersSharedCollection).toContain(securityUser);
      expect(comp.productsSharedCollection).toContain(product);
      expect(comp.loanApplications).toEqual(loanApplications);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILoanApplications>>();
      const loanApplications = { id: 123 };
      jest.spyOn(loanApplicationsFormService, 'getLoanApplications').mockReturnValue(loanApplications);
      jest.spyOn(loanApplicationsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ loanApplications });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: loanApplications }));
      saveSubject.complete();

      // THEN
      expect(loanApplicationsFormService.getLoanApplications).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(loanApplicationsService.update).toHaveBeenCalledWith(expect.objectContaining(loanApplications));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILoanApplications>>();
      const loanApplications = { id: 123 };
      jest.spyOn(loanApplicationsFormService, 'getLoanApplications').mockReturnValue({ id: null });
      jest.spyOn(loanApplicationsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ loanApplications: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: loanApplications }));
      saveSubject.complete();

      // THEN
      expect(loanApplicationsFormService.getLoanApplications).toHaveBeenCalled();
      expect(loanApplicationsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILoanApplications>>();
      const loanApplications = { id: 123 };
      jest.spyOn(loanApplicationsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ loanApplications });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(loanApplicationsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareMember', () => {
      it('Should forward to memberService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(memberService, 'compareMember');
        comp.compareMember(entity, entity2);
        expect(memberService.compareMember).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareSecurityUser', () => {
      it('Should forward to securityUserService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(securityUserService, 'compareSecurityUser');
        comp.compareSecurityUser(entity, entity2);
        expect(securityUserService.compareSecurityUser).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareProduct', () => {
      it('Should forward to productService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(productService, 'compareProduct');
        comp.compareProduct(entity, entity2);
        expect(productService.compareProduct).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
