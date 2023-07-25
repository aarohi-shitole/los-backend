import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProductFormService } from './product-form.service';
import { ProductService } from '../service/product.service';
import { IProduct } from '../product.model';
import { ILoanCatagory } from 'app/entities/loan-catagory/loan-catagory.model';
import { LoanCatagoryService } from 'app/entities/loan-catagory/service/loan-catagory.service';
import { IOrganisation } from 'app/entities/organisation/organisation.model';
import { OrganisationService } from 'app/entities/organisation/service/organisation.service';
import { ILedgerAccounts } from 'app/entities/ledger-accounts/ledger-accounts.model';
import { LedgerAccountsService } from 'app/entities/ledger-accounts/service/ledger-accounts.service';

import { ProductUpdateComponent } from './product-update.component';

describe('Product Management Update Component', () => {
  let comp: ProductUpdateComponent;
  let fixture: ComponentFixture<ProductUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let productFormService: ProductFormService;
  let productService: ProductService;
  let loanCatagoryService: LoanCatagoryService;
  let organisationService: OrganisationService;
  let ledgerAccountsService: LedgerAccountsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProductUpdateComponent],
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
      .overrideTemplate(ProductUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProductUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    productFormService = TestBed.inject(ProductFormService);
    productService = TestBed.inject(ProductService);
    loanCatagoryService = TestBed.inject(LoanCatagoryService);
    organisationService = TestBed.inject(OrganisationService);
    ledgerAccountsService = TestBed.inject(LedgerAccountsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call LoanCatagory query and add missing value', () => {
      const product: IProduct = { id: 456 };
      const loanCatagory: ILoanCatagory = { id: 81088 };
      product.loanCatagory = loanCatagory;

      const loanCatagoryCollection: ILoanCatagory[] = [{ id: 7809 }];
      jest.spyOn(loanCatagoryService, 'query').mockReturnValue(of(new HttpResponse({ body: loanCatagoryCollection })));
      const additionalLoanCatagories = [loanCatagory];
      const expectedCollection: ILoanCatagory[] = [...additionalLoanCatagories, ...loanCatagoryCollection];
      jest.spyOn(loanCatagoryService, 'addLoanCatagoryToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ product });
      comp.ngOnInit();

      expect(loanCatagoryService.query).toHaveBeenCalled();
      expect(loanCatagoryService.addLoanCatagoryToCollectionIfMissing).toHaveBeenCalledWith(
        loanCatagoryCollection,
        ...additionalLoanCatagories.map(expect.objectContaining)
      );
      expect(comp.loanCatagoriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Organisation query and add missing value', () => {
      const product: IProduct = { id: 456 };
      const organisation: IOrganisation = { id: 92180 };
      product.organisation = organisation;

      const organisationCollection: IOrganisation[] = [{ id: 49009 }];
      jest.spyOn(organisationService, 'query').mockReturnValue(of(new HttpResponse({ body: organisationCollection })));
      const additionalOrganisations = [organisation];
      const expectedCollection: IOrganisation[] = [...additionalOrganisations, ...organisationCollection];
      jest.spyOn(organisationService, 'addOrganisationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ product });
      comp.ngOnInit();

      expect(organisationService.query).toHaveBeenCalled();
      expect(organisationService.addOrganisationToCollectionIfMissing).toHaveBeenCalledWith(
        organisationCollection,
        ...additionalOrganisations.map(expect.objectContaining)
      );
      expect(comp.organisationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call LedgerAccounts query and add missing value', () => {
      const product: IProduct = { id: 456 };
      const ledgerAccounts: ILedgerAccounts = { id: 23191 };
      product.ledgerAccounts = ledgerAccounts;

      const ledgerAccountsCollection: ILedgerAccounts[] = [{ id: 6832 }];
      jest.spyOn(ledgerAccountsService, 'query').mockReturnValue(of(new HttpResponse({ body: ledgerAccountsCollection })));
      const additionalLedgerAccounts = [ledgerAccounts];
      const expectedCollection: ILedgerAccounts[] = [...additionalLedgerAccounts, ...ledgerAccountsCollection];
      jest.spyOn(ledgerAccountsService, 'addLedgerAccountsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ product });
      comp.ngOnInit();

      expect(ledgerAccountsService.query).toHaveBeenCalled();
      expect(ledgerAccountsService.addLedgerAccountsToCollectionIfMissing).toHaveBeenCalledWith(
        ledgerAccountsCollection,
        ...additionalLedgerAccounts.map(expect.objectContaining)
      );
      expect(comp.ledgerAccountsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const product: IProduct = { id: 456 };
      const loanCatagory: ILoanCatagory = { id: 34660 };
      product.loanCatagory = loanCatagory;
      const organisation: IOrganisation = { id: 73716 };
      product.organisation = organisation;
      const ledgerAccounts: ILedgerAccounts = { id: 53418 };
      product.ledgerAccounts = ledgerAccounts;

      activatedRoute.data = of({ product });
      comp.ngOnInit();

      expect(comp.loanCatagoriesSharedCollection).toContain(loanCatagory);
      expect(comp.organisationsSharedCollection).toContain(organisation);
      expect(comp.ledgerAccountsSharedCollection).toContain(ledgerAccounts);
      expect(comp.product).toEqual(product);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProduct>>();
      const product = { id: 123 };
      jest.spyOn(productFormService, 'getProduct').mockReturnValue(product);
      jest.spyOn(productService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ product });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: product }));
      saveSubject.complete();

      // THEN
      expect(productFormService.getProduct).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(productService.update).toHaveBeenCalledWith(expect.objectContaining(product));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProduct>>();
      const product = { id: 123 };
      jest.spyOn(productFormService, 'getProduct').mockReturnValue({ id: null });
      jest.spyOn(productService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ product: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: product }));
      saveSubject.complete();

      // THEN
      expect(productFormService.getProduct).toHaveBeenCalled();
      expect(productService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProduct>>();
      const product = { id: 123 };
      jest.spyOn(productService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ product });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(productService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareLoanCatagory', () => {
      it('Should forward to loanCatagoryService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(loanCatagoryService, 'compareLoanCatagory');
        comp.compareLoanCatagory(entity, entity2);
        expect(loanCatagoryService.compareLoanCatagory).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareOrganisation', () => {
      it('Should forward to organisationService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(organisationService, 'compareOrganisation');
        comp.compareOrganisation(entity, entity2);
        expect(organisationService.compareOrganisation).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareLedgerAccounts', () => {
      it('Should forward to ledgerAccountsService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(ledgerAccountsService, 'compareLedgerAccounts');
        comp.compareLedgerAccounts(entity, entity2);
        expect(ledgerAccountsService.compareLedgerAccounts).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
