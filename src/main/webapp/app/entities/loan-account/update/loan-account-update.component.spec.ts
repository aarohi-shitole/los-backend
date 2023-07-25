import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LoanAccountFormService } from './loan-account-form.service';
import { LoanAccountService } from '../service/loan-account.service';
import { ILoanAccount } from '../loan-account.model';
import { ILoanApplications } from 'app/entities/loan-applications/loan-applications.model';
import { LoanApplicationsService } from 'app/entities/loan-applications/service/loan-applications.service';
import { IMember } from 'app/entities/member/member.model';
import { MemberService } from 'app/entities/member/service/member.service';
import { IProduct } from 'app/entities/product/product.model';
import { ProductService } from 'app/entities/product/service/product.service';

import { LoanAccountUpdateComponent } from './loan-account-update.component';

describe('LoanAccount Management Update Component', () => {
  let comp: LoanAccountUpdateComponent;
  let fixture: ComponentFixture<LoanAccountUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let loanAccountFormService: LoanAccountFormService;
  let loanAccountService: LoanAccountService;
  let loanApplicationsService: LoanApplicationsService;
  let memberService: MemberService;
  let productService: ProductService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LoanAccountUpdateComponent],
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
      .overrideTemplate(LoanAccountUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LoanAccountUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    loanAccountFormService = TestBed.inject(LoanAccountFormService);
    loanAccountService = TestBed.inject(LoanAccountService);
    loanApplicationsService = TestBed.inject(LoanApplicationsService);
    memberService = TestBed.inject(MemberService);
    productService = TestBed.inject(ProductService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call LoanApplications query and add missing value', () => {
      const loanAccount: ILoanAccount = { id: 456 };
      const loanApplications: ILoanApplications = { id: 55898 };
      loanAccount.loanApplications = loanApplications;

      const loanApplicationsCollection: ILoanApplications[] = [{ id: 37315 }];
      jest.spyOn(loanApplicationsService, 'query').mockReturnValue(of(new HttpResponse({ body: loanApplicationsCollection })));
      const additionalLoanApplications = [loanApplications];
      const expectedCollection: ILoanApplications[] = [...additionalLoanApplications, ...loanApplicationsCollection];
      jest.spyOn(loanApplicationsService, 'addLoanApplicationsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ loanAccount });
      comp.ngOnInit();

      expect(loanApplicationsService.query).toHaveBeenCalled();
      expect(loanApplicationsService.addLoanApplicationsToCollectionIfMissing).toHaveBeenCalledWith(
        loanApplicationsCollection,
        ...additionalLoanApplications.map(expect.objectContaining)
      );
      expect(comp.loanApplicationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Member query and add missing value', () => {
      const loanAccount: ILoanAccount = { id: 456 };
      const member: IMember = { id: 68986 };
      loanAccount.member = member;

      const memberCollection: IMember[] = [{ id: 29423 }];
      jest.spyOn(memberService, 'query').mockReturnValue(of(new HttpResponse({ body: memberCollection })));
      const additionalMembers = [member];
      const expectedCollection: IMember[] = [...additionalMembers, ...memberCollection];
      jest.spyOn(memberService, 'addMemberToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ loanAccount });
      comp.ngOnInit();

      expect(memberService.query).toHaveBeenCalled();
      expect(memberService.addMemberToCollectionIfMissing).toHaveBeenCalledWith(
        memberCollection,
        ...additionalMembers.map(expect.objectContaining)
      );
      expect(comp.membersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Product query and add missing value', () => {
      const loanAccount: ILoanAccount = { id: 456 };
      const product: IProduct = { id: 21920 };
      loanAccount.product = product;

      const productCollection: IProduct[] = [{ id: 21549 }];
      jest.spyOn(productService, 'query').mockReturnValue(of(new HttpResponse({ body: productCollection })));
      const additionalProducts = [product];
      const expectedCollection: IProduct[] = [...additionalProducts, ...productCollection];
      jest.spyOn(productService, 'addProductToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ loanAccount });
      comp.ngOnInit();

      expect(productService.query).toHaveBeenCalled();
      expect(productService.addProductToCollectionIfMissing).toHaveBeenCalledWith(
        productCollection,
        ...additionalProducts.map(expect.objectContaining)
      );
      expect(comp.productsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const loanAccount: ILoanAccount = { id: 456 };
      const loanApplications: ILoanApplications = { id: 7828 };
      loanAccount.loanApplications = loanApplications;
      const member: IMember = { id: 75781 };
      loanAccount.member = member;
      const product: IProduct = { id: 69987 };
      loanAccount.product = product;

      activatedRoute.data = of({ loanAccount });
      comp.ngOnInit();

      expect(comp.loanApplicationsSharedCollection).toContain(loanApplications);
      expect(comp.membersSharedCollection).toContain(member);
      expect(comp.productsSharedCollection).toContain(product);
      expect(comp.loanAccount).toEqual(loanAccount);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILoanAccount>>();
      const loanAccount = { id: 123 };
      jest.spyOn(loanAccountFormService, 'getLoanAccount').mockReturnValue(loanAccount);
      jest.spyOn(loanAccountService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ loanAccount });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: loanAccount }));
      saveSubject.complete();

      // THEN
      expect(loanAccountFormService.getLoanAccount).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(loanAccountService.update).toHaveBeenCalledWith(expect.objectContaining(loanAccount));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILoanAccount>>();
      const loanAccount = { id: 123 };
      jest.spyOn(loanAccountFormService, 'getLoanAccount').mockReturnValue({ id: null });
      jest.spyOn(loanAccountService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ loanAccount: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: loanAccount }));
      saveSubject.complete();

      // THEN
      expect(loanAccountFormService.getLoanAccount).toHaveBeenCalled();
      expect(loanAccountService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILoanAccount>>();
      const loanAccount = { id: 123 };
      jest.spyOn(loanAccountService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ loanAccount });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(loanAccountService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareLoanApplications', () => {
      it('Should forward to loanApplicationsService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(loanApplicationsService, 'compareLoanApplications');
        comp.compareLoanApplications(entity, entity2);
        expect(loanApplicationsService.compareLoanApplications).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareMember', () => {
      it('Should forward to memberService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(memberService, 'compareMember');
        comp.compareMember(entity, entity2);
        expect(memberService.compareMember).toHaveBeenCalledWith(entity, entity2);
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
