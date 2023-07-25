import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { VouchersHistoryFormService } from './vouchers-history-form.service';
import { VouchersHistoryService } from '../service/vouchers-history.service';
import { IVouchersHistory } from '../vouchers-history.model';

import { VouchersHistoryUpdateComponent } from './vouchers-history-update.component';

describe('VouchersHistory Management Update Component', () => {
  let comp: VouchersHistoryUpdateComponent;
  let fixture: ComponentFixture<VouchersHistoryUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let vouchersHistoryFormService: VouchersHistoryFormService;
  let vouchersHistoryService: VouchersHistoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [VouchersHistoryUpdateComponent],
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
      .overrideTemplate(VouchersHistoryUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(VouchersHistoryUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    vouchersHistoryFormService = TestBed.inject(VouchersHistoryFormService);
    vouchersHistoryService = TestBed.inject(VouchersHistoryService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const vouchersHistory: IVouchersHistory = { id: 456 };

      activatedRoute.data = of({ vouchersHistory });
      comp.ngOnInit();

      expect(comp.vouchersHistory).toEqual(vouchersHistory);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVouchersHistory>>();
      const vouchersHistory = { id: 123 };
      jest.spyOn(vouchersHistoryFormService, 'getVouchersHistory').mockReturnValue(vouchersHistory);
      jest.spyOn(vouchersHistoryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ vouchersHistory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: vouchersHistory }));
      saveSubject.complete();

      // THEN
      expect(vouchersHistoryFormService.getVouchersHistory).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(vouchersHistoryService.update).toHaveBeenCalledWith(expect.objectContaining(vouchersHistory));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVouchersHistory>>();
      const vouchersHistory = { id: 123 };
      jest.spyOn(vouchersHistoryFormService, 'getVouchersHistory').mockReturnValue({ id: null });
      jest.spyOn(vouchersHistoryService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ vouchersHistory: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: vouchersHistory }));
      saveSubject.complete();

      // THEN
      expect(vouchersHistoryFormService.getVouchersHistory).toHaveBeenCalled();
      expect(vouchersHistoryService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVouchersHistory>>();
      const vouchersHistory = { id: 123 };
      jest.spyOn(vouchersHistoryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ vouchersHistory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(vouchersHistoryService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
