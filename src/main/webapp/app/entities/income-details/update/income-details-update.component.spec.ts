import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IncomeDetailsFormService } from './income-details-form.service';
import { IncomeDetailsService } from '../service/income-details.service';
import { IIncomeDetails } from '../income-details.model';
import { IMember } from 'app/entities/member/member.model';
import { MemberService } from 'app/entities/member/service/member.service';

import { IncomeDetailsUpdateComponent } from './income-details-update.component';

describe('IncomeDetails Management Update Component', () => {
  let comp: IncomeDetailsUpdateComponent;
  let fixture: ComponentFixture<IncomeDetailsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let incomeDetailsFormService: IncomeDetailsFormService;
  let incomeDetailsService: IncomeDetailsService;
  let memberService: MemberService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [IncomeDetailsUpdateComponent],
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
      .overrideTemplate(IncomeDetailsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(IncomeDetailsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    incomeDetailsFormService = TestBed.inject(IncomeDetailsFormService);
    incomeDetailsService = TestBed.inject(IncomeDetailsService);
    memberService = TestBed.inject(MemberService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Member query and add missing value', () => {
      const incomeDetails: IIncomeDetails = { id: 456 };
      const member: IMember = { id: 41419 };
      incomeDetails.member = member;

      const memberCollection: IMember[] = [{ id: 23149 }];
      jest.spyOn(memberService, 'query').mockReturnValue(of(new HttpResponse({ body: memberCollection })));
      const additionalMembers = [member];
      const expectedCollection: IMember[] = [...additionalMembers, ...memberCollection];
      jest.spyOn(memberService, 'addMemberToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ incomeDetails });
      comp.ngOnInit();

      expect(memberService.query).toHaveBeenCalled();
      expect(memberService.addMemberToCollectionIfMissing).toHaveBeenCalledWith(
        memberCollection,
        ...additionalMembers.map(expect.objectContaining)
      );
      expect(comp.membersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const incomeDetails: IIncomeDetails = { id: 456 };
      const member: IMember = { id: 98837 };
      incomeDetails.member = member;

      activatedRoute.data = of({ incomeDetails });
      comp.ngOnInit();

      expect(comp.membersSharedCollection).toContain(member);
      expect(comp.incomeDetails).toEqual(incomeDetails);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIncomeDetails>>();
      const incomeDetails = { id: 123 };
      jest.spyOn(incomeDetailsFormService, 'getIncomeDetails').mockReturnValue(incomeDetails);
      jest.spyOn(incomeDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ incomeDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: incomeDetails }));
      saveSubject.complete();

      // THEN
      expect(incomeDetailsFormService.getIncomeDetails).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(incomeDetailsService.update).toHaveBeenCalledWith(expect.objectContaining(incomeDetails));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIncomeDetails>>();
      const incomeDetails = { id: 123 };
      jest.spyOn(incomeDetailsFormService, 'getIncomeDetails').mockReturnValue({ id: null });
      jest.spyOn(incomeDetailsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ incomeDetails: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: incomeDetails }));
      saveSubject.complete();

      // THEN
      expect(incomeDetailsFormService.getIncomeDetails).toHaveBeenCalled();
      expect(incomeDetailsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIncomeDetails>>();
      const incomeDetails = { id: 123 };
      jest.spyOn(incomeDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ incomeDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(incomeDetailsService.update).toHaveBeenCalled();
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
  });
});
