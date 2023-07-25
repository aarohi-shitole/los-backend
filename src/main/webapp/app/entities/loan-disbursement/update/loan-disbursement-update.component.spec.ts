import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LoanDisbursementFormService } from './loan-disbursement-form.service';
import { LoanDisbursementService } from '../service/loan-disbursement.service';
import { ILoanDisbursement } from '../loan-disbursement.model';
import { IProduct } from 'app/entities/product/product.model';
import { ProductService } from 'app/entities/product/service/product.service';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { SecurityUserService } from 'app/entities/security-user/service/security-user.service';
import { ILoanAccount } from 'app/entities/loan-account/loan-account.model';
import { LoanAccountService } from 'app/entities/loan-account/service/loan-account.service';

import { LoanDisbursementUpdateComponent } from './loan-disbursement-update.component';

describe('LoanDisbursement Management Update Component', () => {
  let comp: LoanDisbursementUpdateComponent;
  let fixture: ComponentFixture<LoanDisbursementUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let loanDisbursementFormService: LoanDisbursementFormService;
  let loanDisbursementService: LoanDisbursementService;
  let productService: ProductService;
  let securityUserService: SecurityUserService;
  let loanAccountService: LoanAccountService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LoanDisbursementUpdateComponent],
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
      .overrideTemplate(LoanDisbursementUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LoanDisbursementUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    loanDisbursementFormService = TestBed.inject(LoanDisbursementFormService);
    loanDisbursementService = TestBed.inject(LoanDisbursementService);
    productService = TestBed.inject(ProductService);
    securityUserService = TestBed.inject(SecurityUserService);
    loanAccountService = TestBed.inject(LoanAccountService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Product query and add missing value', () => {
      const loanDisbursement: ILoanDisbursement = { id: 456 };
      const product: IProduct = { id: 59520 };
      loanDisbursement.product = product;

      const productCollection: IProduct[] = [{ id: 30401 }];
      jest.spyOn(productService, 'query').mockReturnValue(of(new HttpResponse({ body: productCollection })));
      const additionalProducts = [product];
      const expectedCollection: IProduct[] = [...additionalProducts, ...productCollection];
      jest.spyOn(productService, 'addProductToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ loanDisbursement });
      comp.ngOnInit();

      expect(productService.query).toHaveBeenCalled();
      expect(productService.addProductToCollectionIfMissing).toHaveBeenCalledWith(
        productCollection,
        ...additionalProducts.map(expect.objectContaining)
      );
      expect(comp.productsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call SecurityUser query and add missing value', () => {
      const loanDisbursement: ILoanDisbursement = { id: 456 };
      const securityUser: ISecurityUser = { id: 56461 };
      loanDisbursement.securityUser = securityUser;

      const securityUserCollection: ISecurityUser[] = [{ id: 3650 }];
      jest.spyOn(securityUserService, 'query').mockReturnValue(of(new HttpResponse({ body: securityUserCollection })));
      const additionalSecurityUsers = [securityUser];
      const expectedCollection: ISecurityUser[] = [...additionalSecurityUsers, ...securityUserCollection];
      jest.spyOn(securityUserService, 'addSecurityUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ loanDisbursement });
      comp.ngOnInit();

      expect(securityUserService.query).toHaveBeenCalled();
      expect(securityUserService.addSecurityUserToCollectionIfMissing).toHaveBeenCalledWith(
        securityUserCollection,
        ...additionalSecurityUsers.map(expect.objectContaining)
      );
      expect(comp.securityUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call LoanAccount query and add missing value', () => {
      const loanDisbursement: ILoanDisbursement = { id: 456 };
      const loanAccount: ILoanAccount = { id: 50904 };
      loanDisbursement.loanAccount = loanAccount;

      const loanAccountCollection: ILoanAccount[] = [{ id: 17521 }];
      jest.spyOn(loanAccountService, 'query').mockReturnValue(of(new HttpResponse({ body: loanAccountCollection })));
      const additionalLoanAccounts = [loanAccount];
      const expectedCollection: ILoanAccount[] = [...additionalLoanAccounts, ...loanAccountCollection];
      jest.spyOn(loanAccountService, 'addLoanAccountToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ loanDisbursement });
      comp.ngOnInit();

      expect(loanAccountService.query).toHaveBeenCalled();
      expect(loanAccountService.addLoanAccountToCollectionIfMissing).toHaveBeenCalledWith(
        loanAccountCollection,
        ...additionalLoanAccounts.map(expect.objectContaining)
      );
      expect(comp.loanAccountsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const loanDisbursement: ILoanDisbursement = { id: 456 };
      const product: IProduct = { id: 42034 };
      loanDisbursement.product = product;
      const securityUser: ISecurityUser = { id: 43577 };
      loanDisbursement.securityUser = securityUser;
      const loanAccount: ILoanAccount = { id: 53498 };
      loanDisbursement.loanAccount = loanAccount;

      activatedRoute.data = of({ loanDisbursement });
      comp.ngOnInit();

      expect(comp.productsSharedCollection).toContain(product);
      expect(comp.securityUsersSharedCollection).toContain(securityUser);
      expect(comp.loanAccountsSharedCollection).toContain(loanAccount);
      expect(comp.loanDisbursement).toEqual(loanDisbursement);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILoanDisbursement>>();
      const loanDisbursement = { id: 123 };
      jest.spyOn(loanDisbursementFormService, 'getLoanDisbursement').mockReturnValue(loanDisbursement);
      jest.spyOn(loanDisbursementService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ loanDisbursement });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: loanDisbursement }));
      saveSubject.complete();

      // THEN
      expect(loanDisbursementFormService.getLoanDisbursement).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(loanDisbursementService.update).toHaveBeenCalledWith(expect.objectContaining(loanDisbursement));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILoanDisbursement>>();
      const loanDisbursement = { id: 123 };
      jest.spyOn(loanDisbursementFormService, 'getLoanDisbursement').mockReturnValue({ id: null });
      jest.spyOn(loanDisbursementService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ loanDisbursement: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: loanDisbursement }));
      saveSubject.complete();

      // THEN
      expect(loanDisbursementFormService.getLoanDisbursement).toHaveBeenCalled();
      expect(loanDisbursementService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILoanDisbursement>>();
      const loanDisbursement = { id: 123 };
      jest.spyOn(loanDisbursementService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ loanDisbursement });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(loanDisbursementService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareProduct', () => {
      it('Should forward to productService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(productService, 'compareProduct');
        comp.compareProduct(entity, entity2);
        expect(productService.compareProduct).toHaveBeenCalledWith(entity, entity2);
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

    describe('compareLoanAccount', () => {
      it('Should forward to loanAccountService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(loanAccountService, 'compareLoanAccount');
        comp.compareLoanAccount(entity, entity2);
        expect(loanAccountService.compareLoanAccount).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
