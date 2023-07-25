import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EmployementDetailsFormService } from './employement-details-form.service';
import { EmployementDetailsService } from '../service/employement-details.service';
import { IEmployementDetails } from '../employement-details.model';
import { IMember } from 'app/entities/member/member.model';
import { MemberService } from 'app/entities/member/service/member.service';

import { EmployementDetailsUpdateComponent } from './employement-details-update.component';

describe('EmployementDetails Management Update Component', () => {
  let comp: EmployementDetailsUpdateComponent;
  let fixture: ComponentFixture<EmployementDetailsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let employementDetailsFormService: EmployementDetailsFormService;
  let employementDetailsService: EmployementDetailsService;
  let memberService: MemberService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EmployementDetailsUpdateComponent],
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
      .overrideTemplate(EmployementDetailsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EmployementDetailsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    employementDetailsFormService = TestBed.inject(EmployementDetailsFormService);
    employementDetailsService = TestBed.inject(EmployementDetailsService);
    memberService = TestBed.inject(MemberService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Member query and add missing value', () => {
      const employementDetails: IEmployementDetails = { id: 456 };
      const member: IMember = { id: 28638 };
      employementDetails.member = member;

      const memberCollection: IMember[] = [{ id: 41156 }];
      jest.spyOn(memberService, 'query').mockReturnValue(of(new HttpResponse({ body: memberCollection })));
      const additionalMembers = [member];
      const expectedCollection: IMember[] = [...additionalMembers, ...memberCollection];
      jest.spyOn(memberService, 'addMemberToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ employementDetails });
      comp.ngOnInit();

      expect(memberService.query).toHaveBeenCalled();
      expect(memberService.addMemberToCollectionIfMissing).toHaveBeenCalledWith(
        memberCollection,
        ...additionalMembers.map(expect.objectContaining)
      );
      expect(comp.membersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const employementDetails: IEmployementDetails = { id: 456 };
      const member: IMember = { id: 96781 };
      employementDetails.member = member;

      activatedRoute.data = of({ employementDetails });
      comp.ngOnInit();

      expect(comp.membersSharedCollection).toContain(member);
      expect(comp.employementDetails).toEqual(employementDetails);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmployementDetails>>();
      const employementDetails = { id: 123 };
      jest.spyOn(employementDetailsFormService, 'getEmployementDetails').mockReturnValue(employementDetails);
      jest.spyOn(employementDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ employementDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: employementDetails }));
      saveSubject.complete();

      // THEN
      expect(employementDetailsFormService.getEmployementDetails).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(employementDetailsService.update).toHaveBeenCalledWith(expect.objectContaining(employementDetails));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmployementDetails>>();
      const employementDetails = { id: 123 };
      jest.spyOn(employementDetailsFormService, 'getEmployementDetails').mockReturnValue({ id: null });
      jest.spyOn(employementDetailsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ employementDetails: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: employementDetails }));
      saveSubject.complete();

      // THEN
      expect(employementDetailsFormService.getEmployementDetails).toHaveBeenCalled();
      expect(employementDetailsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmployementDetails>>();
      const employementDetails = { id: 123 };
      jest.spyOn(employementDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ employementDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(employementDetailsService.update).toHaveBeenCalled();
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
