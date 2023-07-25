import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AccountHeadCodeFormService } from './account-head-code-form.service';
import { AccountHeadCodeService } from '../service/account-head-code.service';
import { IAccountHeadCode } from '../account-head-code.model';
import { ILedgerAccounts } from 'app/entities/ledger-accounts/ledger-accounts.model';
import { LedgerAccountsService } from 'app/entities/ledger-accounts/service/ledger-accounts.service';

import { AccountHeadCodeUpdateComponent } from './account-head-code-update.component';

describe('AccountHeadCode Management Update Component', () => {
  let comp: AccountHeadCodeUpdateComponent;
  let fixture: ComponentFixture<AccountHeadCodeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let accountHeadCodeFormService: AccountHeadCodeFormService;
  let accountHeadCodeService: AccountHeadCodeService;
  let ledgerAccountsService: LedgerAccountsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AccountHeadCodeUpdateComponent],
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
      .overrideTemplate(AccountHeadCodeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AccountHeadCodeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    accountHeadCodeFormService = TestBed.inject(AccountHeadCodeFormService);
    accountHeadCodeService = TestBed.inject(AccountHeadCodeService);
    ledgerAccountsService = TestBed.inject(LedgerAccountsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call LedgerAccounts query and add missing value', () => {
      const accountHeadCode: IAccountHeadCode = { id: 456 };
      const ledgerAccounts: ILedgerAccounts = { id: 46515 };
      accountHeadCode.ledgerAccounts = ledgerAccounts;

      const ledgerAccountsCollection: ILedgerAccounts[] = [{ id: 29095 }];
      jest.spyOn(ledgerAccountsService, 'query').mockReturnValue(of(new HttpResponse({ body: ledgerAccountsCollection })));
      const additionalLedgerAccounts = [ledgerAccounts];
      const expectedCollection: ILedgerAccounts[] = [...additionalLedgerAccounts, ...ledgerAccountsCollection];
      jest.spyOn(ledgerAccountsService, 'addLedgerAccountsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ accountHeadCode });
      comp.ngOnInit();

      expect(ledgerAccountsService.query).toHaveBeenCalled();
      expect(ledgerAccountsService.addLedgerAccountsToCollectionIfMissing).toHaveBeenCalledWith(
        ledgerAccountsCollection,
        ...additionalLedgerAccounts.map(expect.objectContaining)
      );
      expect(comp.ledgerAccountsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const accountHeadCode: IAccountHeadCode = { id: 456 };
      const ledgerAccounts: ILedgerAccounts = { id: 39572 };
      accountHeadCode.ledgerAccounts = ledgerAccounts;

      activatedRoute.data = of({ accountHeadCode });
      comp.ngOnInit();

      expect(comp.ledgerAccountsSharedCollection).toContain(ledgerAccounts);
      expect(comp.accountHeadCode).toEqual(accountHeadCode);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAccountHeadCode>>();
      const accountHeadCode = { id: 123 };
      jest.spyOn(accountHeadCodeFormService, 'getAccountHeadCode').mockReturnValue(accountHeadCode);
      jest.spyOn(accountHeadCodeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ accountHeadCode });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: accountHeadCode }));
      saveSubject.complete();

      // THEN
      expect(accountHeadCodeFormService.getAccountHeadCode).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(accountHeadCodeService.update).toHaveBeenCalledWith(expect.objectContaining(accountHeadCode));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAccountHeadCode>>();
      const accountHeadCode = { id: 123 };
      jest.spyOn(accountHeadCodeFormService, 'getAccountHeadCode').mockReturnValue({ id: null });
      jest.spyOn(accountHeadCodeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ accountHeadCode: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: accountHeadCode }));
      saveSubject.complete();

      // THEN
      expect(accountHeadCodeFormService.getAccountHeadCode).toHaveBeenCalled();
      expect(accountHeadCodeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAccountHeadCode>>();
      const accountHeadCode = { id: 123 };
      jest.spyOn(accountHeadCodeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ accountHeadCode });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(accountHeadCodeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
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
