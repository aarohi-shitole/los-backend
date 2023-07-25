import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MemberExistingfacilityFormService } from './member-existingfacility-form.service';
import { MemberExistingfacilityService } from '../service/member-existingfacility.service';
import { IMemberExistingfacility } from '../member-existingfacility.model';
import { IMember } from 'app/entities/member/member.model';
import { MemberService } from 'app/entities/member/service/member.service';

import { MemberExistingfacilityUpdateComponent } from './member-existingfacility-update.component';

describe('MemberExistingfacility Management Update Component', () => {
  let comp: MemberExistingfacilityUpdateComponent;
  let fixture: ComponentFixture<MemberExistingfacilityUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let memberExistingfacilityFormService: MemberExistingfacilityFormService;
  let memberExistingfacilityService: MemberExistingfacilityService;
  let memberService: MemberService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MemberExistingfacilityUpdateComponent],
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
      .overrideTemplate(MemberExistingfacilityUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MemberExistingfacilityUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    memberExistingfacilityFormService = TestBed.inject(MemberExistingfacilityFormService);
    memberExistingfacilityService = TestBed.inject(MemberExistingfacilityService);
    memberService = TestBed.inject(MemberService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Member query and add missing value', () => {
      const memberExistingfacility: IMemberExistingfacility = { id: 456 };
      const member: IMember = { id: 1666 };
      memberExistingfacility.member = member;

      const memberCollection: IMember[] = [{ id: 19157 }];
      jest.spyOn(memberService, 'query').mockReturnValue(of(new HttpResponse({ body: memberCollection })));
      const additionalMembers = [member];
      const expectedCollection: IMember[] = [...additionalMembers, ...memberCollection];
      jest.spyOn(memberService, 'addMemberToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ memberExistingfacility });
      comp.ngOnInit();

      expect(memberService.query).toHaveBeenCalled();
      expect(memberService.addMemberToCollectionIfMissing).toHaveBeenCalledWith(
        memberCollection,
        ...additionalMembers.map(expect.objectContaining)
      );
      expect(comp.membersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const memberExistingfacility: IMemberExistingfacility = { id: 456 };
      const member: IMember = { id: 29023 };
      memberExistingfacility.member = member;

      activatedRoute.data = of({ memberExistingfacility });
      comp.ngOnInit();

      expect(comp.membersSharedCollection).toContain(member);
      expect(comp.memberExistingfacility).toEqual(memberExistingfacility);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMemberExistingfacility>>();
      const memberExistingfacility = { id: 123 };
      jest.spyOn(memberExistingfacilityFormService, 'getMemberExistingfacility').mockReturnValue(memberExistingfacility);
      jest.spyOn(memberExistingfacilityService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ memberExistingfacility });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: memberExistingfacility }));
      saveSubject.complete();

      // THEN
      expect(memberExistingfacilityFormService.getMemberExistingfacility).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(memberExistingfacilityService.update).toHaveBeenCalledWith(expect.objectContaining(memberExistingfacility));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMemberExistingfacility>>();
      const memberExistingfacility = { id: 123 };
      jest.spyOn(memberExistingfacilityFormService, 'getMemberExistingfacility').mockReturnValue({ id: null });
      jest.spyOn(memberExistingfacilityService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ memberExistingfacility: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: memberExistingfacility }));
      saveSubject.complete();

      // THEN
      expect(memberExistingfacilityFormService.getMemberExistingfacility).toHaveBeenCalled();
      expect(memberExistingfacilityService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMemberExistingfacility>>();
      const memberExistingfacility = { id: 123 };
      jest.spyOn(memberExistingfacilityService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ memberExistingfacility });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(memberExistingfacilityService.update).toHaveBeenCalled();
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
