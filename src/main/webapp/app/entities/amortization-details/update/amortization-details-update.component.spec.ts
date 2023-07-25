import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AmortizationDetailsFormService } from './amortization-details-form.service';
import { AmortizationDetailsService } from '../service/amortization-details.service';
import { IAmortizationDetails } from '../amortization-details.model';
import { ILoanAccount } from 'app/entities/loan-account/loan-account.model';
import { LoanAccountService } from 'app/entities/loan-account/service/loan-account.service';

import { AmortizationDetailsUpdateComponent } from './amortization-details-update.component';

describe('AmortizationDetails Management Update Component', () => {
  let comp: AmortizationDetailsUpdateComponent;
  let fixture: ComponentFixture<AmortizationDetailsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let amortizationDetailsFormService: AmortizationDetailsFormService;
  let amortizationDetailsService: AmortizationDetailsService;
  let loanAccountService: LoanAccountService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AmortizationDetailsUpdateComponent],
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
      .overrideTemplate(AmortizationDetailsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AmortizationDetailsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    amortizationDetailsFormService = TestBed.inject(AmortizationDetailsFormService);
    amortizationDetailsService = TestBed.inject(AmortizationDetailsService);
    loanAccountService = TestBed.inject(LoanAccountService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call LoanAccount query and add missing value', () => {
      const amortizationDetails: IAmortizationDetails = { id: 456 };
      const loanAccount: ILoanAccount = { id: 24336 };
      amortizationDetails.loanAccount = loanAccount;

      const loanAccountCollection: ILoanAccount[] = [{ id: 98197 }];
      jest.spyOn(loanAccountService, 'query').mockReturnValue(of(new HttpResponse({ body: loanAccountCollection })));
      const additionalLoanAccounts = [loanAccount];
      const expectedCollection: ILoanAccount[] = [...additionalLoanAccounts, ...loanAccountCollection];
      jest.spyOn(loanAccountService, 'addLoanAccountToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ amortizationDetails });
      comp.ngOnInit();

      expect(loanAccountService.query).toHaveBeenCalled();
      expect(loanAccountService.addLoanAccountToCollectionIfMissing).toHaveBeenCalledWith(
        loanAccountCollection,
        ...additionalLoanAccounts.map(expect.objectContaining)
      );
      expect(comp.loanAccountsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const amortizationDetails: IAmortizationDetails = { id: 456 };
      const loanAccount: ILoanAccount = { id: 80388 };
      amortizationDetails.loanAccount = loanAccount;

      activatedRoute.data = of({ amortizationDetails });
      comp.ngOnInit();

      expect(comp.loanAccountsSharedCollection).toContain(loanAccount);
      expect(comp.amortizationDetails).toEqual(amortizationDetails);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAmortizationDetails>>();
      const amortizationDetails = { id: 123 };
      jest.spyOn(amortizationDetailsFormService, 'getAmortizationDetails').mockReturnValue(amortizationDetails);
      jest.spyOn(amortizationDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ amortizationDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: amortizationDetails }));
      saveSubject.complete();

      // THEN
      expect(amortizationDetailsFormService.getAmortizationDetails).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(amortizationDetailsService.update).toHaveBeenCalledWith(expect.objectContaining(amortizationDetails));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAmortizationDetails>>();
      const amortizationDetails = { id: 123 };
      jest.spyOn(amortizationDetailsFormService, 'getAmortizationDetails').mockReturnValue({ id: null });
      jest.spyOn(amortizationDetailsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ amortizationDetails: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: amortizationDetails }));
      saveSubject.complete();

      // THEN
      expect(amortizationDetailsFormService.getAmortizationDetails).toHaveBeenCalled();
      expect(amortizationDetailsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAmortizationDetails>>();
      const amortizationDetails = { id: 123 };
      jest.spyOn(amortizationDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ amortizationDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(amortizationDetailsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
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
