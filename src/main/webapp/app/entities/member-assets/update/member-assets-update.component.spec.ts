import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MemberAssetsFormService } from './member-assets-form.service';
import { MemberAssetsService } from '../service/member-assets.service';
import { IMemberAssets } from '../member-assets.model';
import { IMember } from 'app/entities/member/member.model';
import { MemberService } from 'app/entities/member/service/member.service';
import { ILoanApplications } from 'app/entities/loan-applications/loan-applications.model';
import { LoanApplicationsService } from 'app/entities/loan-applications/service/loan-applications.service';

import { MemberAssetsUpdateComponent } from './member-assets-update.component';

describe('MemberAssets Management Update Component', () => {
  let comp: MemberAssetsUpdateComponent;
  let fixture: ComponentFixture<MemberAssetsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let memberAssetsFormService: MemberAssetsFormService;
  let memberAssetsService: MemberAssetsService;
  let memberService: MemberService;
  let loanApplicationsService: LoanApplicationsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MemberAssetsUpdateComponent],
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
      .overrideTemplate(MemberAssetsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MemberAssetsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    memberAssetsFormService = TestBed.inject(MemberAssetsFormService);
    memberAssetsService = TestBed.inject(MemberAssetsService);
    memberService = TestBed.inject(MemberService);
    loanApplicationsService = TestBed.inject(LoanApplicationsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Member query and add missing value', () => {
      const memberAssets: IMemberAssets = { id: 456 };
      const member: IMember = { id: 45531 };
      memberAssets.member = member;

      const memberCollection: IMember[] = [{ id: 95734 }];
      jest.spyOn(memberService, 'query').mockReturnValue(of(new HttpResponse({ body: memberCollection })));
      const additionalMembers = [member];
      const expectedCollection: IMember[] = [...additionalMembers, ...memberCollection];
      jest.spyOn(memberService, 'addMemberToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ memberAssets });
      comp.ngOnInit();

      expect(memberService.query).toHaveBeenCalled();
      expect(memberService.addMemberToCollectionIfMissing).toHaveBeenCalledWith(
        memberCollection,
        ...additionalMembers.map(expect.objectContaining)
      );
      expect(comp.membersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call LoanApplications query and add missing value', () => {
      const memberAssets: IMemberAssets = { id: 456 };
      const loanApplications: ILoanApplications = { id: 36513 };
      memberAssets.loanApplications = loanApplications;

      const loanApplicationsCollection: ILoanApplications[] = [{ id: 72746 }];
      jest.spyOn(loanApplicationsService, 'query').mockReturnValue(of(new HttpResponse({ body: loanApplicationsCollection })));
      const additionalLoanApplications = [loanApplications];
      const expectedCollection: ILoanApplications[] = [...additionalLoanApplications, ...loanApplicationsCollection];
      jest.spyOn(loanApplicationsService, 'addLoanApplicationsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ memberAssets });
      comp.ngOnInit();

      expect(loanApplicationsService.query).toHaveBeenCalled();
      expect(loanApplicationsService.addLoanApplicationsToCollectionIfMissing).toHaveBeenCalledWith(
        loanApplicationsCollection,
        ...additionalLoanApplications.map(expect.objectContaining)
      );
      expect(comp.loanApplicationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const memberAssets: IMemberAssets = { id: 456 };
      const member: IMember = { id: 37773 };
      memberAssets.member = member;
      const loanApplications: ILoanApplications = { id: 31940 };
      memberAssets.loanApplications = loanApplications;

      activatedRoute.data = of({ memberAssets });
      comp.ngOnInit();

      expect(comp.membersSharedCollection).toContain(member);
      expect(comp.loanApplicationsSharedCollection).toContain(loanApplications);
      expect(comp.memberAssets).toEqual(memberAssets);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMemberAssets>>();
      const memberAssets = { id: 123 };
      jest.spyOn(memberAssetsFormService, 'getMemberAssets').mockReturnValue(memberAssets);
      jest.spyOn(memberAssetsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ memberAssets });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: memberAssets }));
      saveSubject.complete();

      // THEN
      expect(memberAssetsFormService.getMemberAssets).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(memberAssetsService.update).toHaveBeenCalledWith(expect.objectContaining(memberAssets));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMemberAssets>>();
      const memberAssets = { id: 123 };
      jest.spyOn(memberAssetsFormService, 'getMemberAssets').mockReturnValue({ id: null });
      jest.spyOn(memberAssetsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ memberAssets: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: memberAssets }));
      saveSubject.complete();

      // THEN
      expect(memberAssetsFormService.getMemberAssets).toHaveBeenCalled();
      expect(memberAssetsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMemberAssets>>();
      const memberAssets = { id: 123 };
      jest.spyOn(memberAssetsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ memberAssets });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(memberAssetsService.update).toHaveBeenCalled();
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
