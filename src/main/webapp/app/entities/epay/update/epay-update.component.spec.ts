import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EpayFormService } from './epay-form.service';
import { EpayService } from '../service/epay.service';
import { IEpay } from '../epay.model';

import { EpayUpdateComponent } from './epay-update.component';

describe('Epay Management Update Component', () => {
  let comp: EpayUpdateComponent;
  let fixture: ComponentFixture<EpayUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let epayFormService: EpayFormService;
  let epayService: EpayService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EpayUpdateComponent],
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
      .overrideTemplate(EpayUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EpayUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    epayFormService = TestBed.inject(EpayFormService);
    epayService = TestBed.inject(EpayService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const epay: IEpay = { id: 456 };

      activatedRoute.data = of({ epay });
      comp.ngOnInit();

      expect(comp.epay).toEqual(epay);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEpay>>();
      const epay = { id: 123 };
      jest.spyOn(epayFormService, 'getEpay').mockReturnValue(epay);
      jest.spyOn(epayService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ epay });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: epay }));
      saveSubject.complete();

      // THEN
      expect(epayFormService.getEpay).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(epayService.update).toHaveBeenCalledWith(expect.objectContaining(epay));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEpay>>();
      const epay = { id: 123 };
      jest.spyOn(epayFormService, 'getEpay').mockReturnValue({ id: null });
      jest.spyOn(epayService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ epay: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: epay }));
      saveSubject.complete();

      // THEN
      expect(epayFormService.getEpay).toHaveBeenCalled();
      expect(epayService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEpay>>();
      const epay = { id: 123 };
      jest.spyOn(epayService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ epay });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(epayService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
