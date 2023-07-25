import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LoanCatagoryFormService } from './loan-catagory-form.service';
import { LoanCatagoryService } from '../service/loan-catagory.service';
import { ILoanCatagory } from '../loan-catagory.model';

import { LoanCatagoryUpdateComponent } from './loan-catagory-update.component';

describe('LoanCatagory Management Update Component', () => {
  let comp: LoanCatagoryUpdateComponent;
  let fixture: ComponentFixture<LoanCatagoryUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let loanCatagoryFormService: LoanCatagoryFormService;
  let loanCatagoryService: LoanCatagoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LoanCatagoryUpdateComponent],
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
      .overrideTemplate(LoanCatagoryUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LoanCatagoryUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    loanCatagoryFormService = TestBed.inject(LoanCatagoryFormService);
    loanCatagoryService = TestBed.inject(LoanCatagoryService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const loanCatagory: ILoanCatagory = { id: 456 };

      activatedRoute.data = of({ loanCatagory });
      comp.ngOnInit();

      expect(comp.loanCatagory).toEqual(loanCatagory);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILoanCatagory>>();
      const loanCatagory = { id: 123 };
      jest.spyOn(loanCatagoryFormService, 'getLoanCatagory').mockReturnValue(loanCatagory);
      jest.spyOn(loanCatagoryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ loanCatagory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: loanCatagory }));
      saveSubject.complete();

      // THEN
      expect(loanCatagoryFormService.getLoanCatagory).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(loanCatagoryService.update).toHaveBeenCalledWith(expect.objectContaining(loanCatagory));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILoanCatagory>>();
      const loanCatagory = { id: 123 };
      jest.spyOn(loanCatagoryFormService, 'getLoanCatagory').mockReturnValue({ id: null });
      jest.spyOn(loanCatagoryService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ loanCatagory: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: loanCatagory }));
      saveSubject.complete();

      // THEN
      expect(loanCatagoryFormService.getLoanCatagory).toHaveBeenCalled();
      expect(loanCatagoryService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILoanCatagory>>();
      const loanCatagory = { id: 123 };
      jest.spyOn(loanCatagoryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ loanCatagory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(loanCatagoryService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
