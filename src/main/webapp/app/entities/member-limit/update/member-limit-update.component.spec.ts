import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MemberLimitFormService } from './member-limit-form.service';
import { MemberLimitService } from '../service/member-limit.service';
import { IMemberLimit } from '../member-limit.model';

import { MemberLimitUpdateComponent } from './member-limit-update.component';

describe('MemberLimit Management Update Component', () => {
  let comp: MemberLimitUpdateComponent;
  let fixture: ComponentFixture<MemberLimitUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let memberLimitFormService: MemberLimitFormService;
  let memberLimitService: MemberLimitService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MemberLimitUpdateComponent],
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
      .overrideTemplate(MemberLimitUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MemberLimitUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    memberLimitFormService = TestBed.inject(MemberLimitFormService);
    memberLimitService = TestBed.inject(MemberLimitService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const memberLimit: IMemberLimit = { id: 456 };

      activatedRoute.data = of({ memberLimit });
      comp.ngOnInit();

      expect(comp.memberLimit).toEqual(memberLimit);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMemberLimit>>();
      const memberLimit = { id: 123 };
      jest.spyOn(memberLimitFormService, 'getMemberLimit').mockReturnValue(memberLimit);
      jest.spyOn(memberLimitService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ memberLimit });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: memberLimit }));
      saveSubject.complete();

      // THEN
      expect(memberLimitFormService.getMemberLimit).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(memberLimitService.update).toHaveBeenCalledWith(expect.objectContaining(memberLimit));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMemberLimit>>();
      const memberLimit = { id: 123 };
      jest.spyOn(memberLimitFormService, 'getMemberLimit').mockReturnValue({ id: null });
      jest.spyOn(memberLimitService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ memberLimit: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: memberLimit }));
      saveSubject.complete();

      // THEN
      expect(memberLimitFormService.getMemberLimit).toHaveBeenCalled();
      expect(memberLimitService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMemberLimit>>();
      const memberLimit = { id: 123 };
      jest.spyOn(memberLimitService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ memberLimit });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(memberLimitService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
