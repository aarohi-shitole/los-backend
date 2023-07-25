import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RemarkHistoryFormService } from './remark-history-form.service';
import { RemarkHistoryService } from '../service/remark-history.service';
import { IRemarkHistory } from '../remark-history.model';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { SecurityUserService } from 'app/entities/security-user/service/security-user.service';
import { ILoanApplications } from 'app/entities/loan-applications/loan-applications.model';
import { LoanApplicationsService } from 'app/entities/loan-applications/service/loan-applications.service';

import { RemarkHistoryUpdateComponent } from './remark-history-update.component';

describe('RemarkHistory Management Update Component', () => {
  let comp: RemarkHistoryUpdateComponent;
  let fixture: ComponentFixture<RemarkHistoryUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let remarkHistoryFormService: RemarkHistoryFormService;
  let remarkHistoryService: RemarkHistoryService;
  let securityUserService: SecurityUserService;
  let loanApplicationsService: LoanApplicationsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [RemarkHistoryUpdateComponent],
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
      .overrideTemplate(RemarkHistoryUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RemarkHistoryUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    remarkHistoryFormService = TestBed.inject(RemarkHistoryFormService);
    remarkHistoryService = TestBed.inject(RemarkHistoryService);
    securityUserService = TestBed.inject(SecurityUserService);
    loanApplicationsService = TestBed.inject(LoanApplicationsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call SecurityUser query and add missing value', () => {
      const remarkHistory: IRemarkHistory = { id: 456 };
      const securityUser: ISecurityUser = { id: 67477 };
      remarkHistory.securityUser = securityUser;

      const securityUserCollection: ISecurityUser[] = [{ id: 31039 }];
      jest.spyOn(securityUserService, 'query').mockReturnValue(of(new HttpResponse({ body: securityUserCollection })));
      const additionalSecurityUsers = [securityUser];
      const expectedCollection: ISecurityUser[] = [...additionalSecurityUsers, ...securityUserCollection];
      jest.spyOn(securityUserService, 'addSecurityUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ remarkHistory });
      comp.ngOnInit();

      expect(securityUserService.query).toHaveBeenCalled();
      expect(securityUserService.addSecurityUserToCollectionIfMissing).toHaveBeenCalledWith(
        securityUserCollection,
        ...additionalSecurityUsers.map(expect.objectContaining)
      );
      expect(comp.securityUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call LoanApplications query and add missing value', () => {
      const remarkHistory: IRemarkHistory = { id: 456 };
      const loanApplications: ILoanApplications = { id: 31046 };
      remarkHistory.loanApplications = loanApplications;

      const loanApplicationsCollection: ILoanApplications[] = [{ id: 34905 }];
      jest.spyOn(loanApplicationsService, 'query').mockReturnValue(of(new HttpResponse({ body: loanApplicationsCollection })));
      const additionalLoanApplications = [loanApplications];
      const expectedCollection: ILoanApplications[] = [...additionalLoanApplications, ...loanApplicationsCollection];
      jest.spyOn(loanApplicationsService, 'addLoanApplicationsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ remarkHistory });
      comp.ngOnInit();

      expect(loanApplicationsService.query).toHaveBeenCalled();
      expect(loanApplicationsService.addLoanApplicationsToCollectionIfMissing).toHaveBeenCalledWith(
        loanApplicationsCollection,
        ...additionalLoanApplications.map(expect.objectContaining)
      );
      expect(comp.loanApplicationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const remarkHistory: IRemarkHistory = { id: 456 };
      const securityUser: ISecurityUser = { id: 10208 };
      remarkHistory.securityUser = securityUser;
      const loanApplications: ILoanApplications = { id: 89993 };
      remarkHistory.loanApplications = loanApplications;

      activatedRoute.data = of({ remarkHistory });
      comp.ngOnInit();

      expect(comp.securityUsersSharedCollection).toContain(securityUser);
      expect(comp.loanApplicationsSharedCollection).toContain(loanApplications);
      expect(comp.remarkHistory).toEqual(remarkHistory);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRemarkHistory>>();
      const remarkHistory = { id: 123 };
      jest.spyOn(remarkHistoryFormService, 'getRemarkHistory').mockReturnValue(remarkHistory);
      jest.spyOn(remarkHistoryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ remarkHistory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: remarkHistory }));
      saveSubject.complete();

      // THEN
      expect(remarkHistoryFormService.getRemarkHistory).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(remarkHistoryService.update).toHaveBeenCalledWith(expect.objectContaining(remarkHistory));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRemarkHistory>>();
      const remarkHistory = { id: 123 };
      jest.spyOn(remarkHistoryFormService, 'getRemarkHistory').mockReturnValue({ id: null });
      jest.spyOn(remarkHistoryService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ remarkHistory: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: remarkHistory }));
      saveSubject.complete();

      // THEN
      expect(remarkHistoryFormService.getRemarkHistory).toHaveBeenCalled();
      expect(remarkHistoryService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRemarkHistory>>();
      const remarkHistory = { id: 123 };
      jest.spyOn(remarkHistoryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ remarkHistory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(remarkHistoryService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareSecurityUser', () => {
      it('Should forward to securityUserService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(securityUserService, 'compareSecurityUser');
        comp.compareSecurityUser(entity, entity2);
        expect(securityUserService.compareSecurityUser).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareLoanApplications', () => {
      it('Should forward to loanApplicationsService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(loanApplicationsService, 'compareLoanApplications');
        comp.compareLoanApplications(entity, entity2);
        expect(loanApplicationsService.compareLoanApplications).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
