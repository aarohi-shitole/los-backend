import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MemberBankFormService } from './member-bank-form.service';
import { MemberBankService } from '../service/member-bank.service';
import { IMemberBank } from '../member-bank.model';
import { IMember } from 'app/entities/member/member.model';
import { MemberService } from 'app/entities/member/service/member.service';

import { MemberBankUpdateComponent } from './member-bank-update.component';

describe('MemberBank Management Update Component', () => {
  let comp: MemberBankUpdateComponent;
  let fixture: ComponentFixture<MemberBankUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let memberBankFormService: MemberBankFormService;
  let memberBankService: MemberBankService;
  let memberService: MemberService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MemberBankUpdateComponent],
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
      .overrideTemplate(MemberBankUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MemberBankUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    memberBankFormService = TestBed.inject(MemberBankFormService);
    memberBankService = TestBed.inject(MemberBankService);
    memberService = TestBed.inject(MemberService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Member query and add missing value', () => {
      const memberBank: IMemberBank = { id: 456 };
      const member: IMember = { id: 15424 };
      memberBank.member = member;

      const memberCollection: IMember[] = [{ id: 13764 }];
      jest.spyOn(memberService, 'query').mockReturnValue(of(new HttpResponse({ body: memberCollection })));
      const additionalMembers = [member];
      const expectedCollection: IMember[] = [...additionalMembers, ...memberCollection];
      jest.spyOn(memberService, 'addMemberToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ memberBank });
      comp.ngOnInit();

      expect(memberService.query).toHaveBeenCalled();
      expect(memberService.addMemberToCollectionIfMissing).toHaveBeenCalledWith(
        memberCollection,
        ...additionalMembers.map(expect.objectContaining)
      );
      expect(comp.membersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const memberBank: IMemberBank = { id: 456 };
      const member: IMember = { id: 27906 };
      memberBank.member = member;

      activatedRoute.data = of({ memberBank });
      comp.ngOnInit();

      expect(comp.membersSharedCollection).toContain(member);
      expect(comp.memberBank).toEqual(memberBank);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMemberBank>>();
      const memberBank = { id: 123 };
      jest.spyOn(memberBankFormService, 'getMemberBank').mockReturnValue(memberBank);
      jest.spyOn(memberBankService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ memberBank });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: memberBank }));
      saveSubject.complete();

      // THEN
      expect(memberBankFormService.getMemberBank).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(memberBankService.update).toHaveBeenCalledWith(expect.objectContaining(memberBank));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMemberBank>>();
      const memberBank = { id: 123 };
      jest.spyOn(memberBankFormService, 'getMemberBank').mockReturnValue({ id: null });
      jest.spyOn(memberBankService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ memberBank: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: memberBank }));
      saveSubject.complete();

      // THEN
      expect(memberBankFormService.getMemberBank).toHaveBeenCalled();
      expect(memberBankService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMemberBank>>();
      const memberBank = { id: 123 };
      jest.spyOn(memberBankService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ memberBank });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(memberBankService.update).toHaveBeenCalled();
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
