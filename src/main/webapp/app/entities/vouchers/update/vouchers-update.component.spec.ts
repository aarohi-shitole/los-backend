import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { VouchersFormService } from './vouchers-form.service';
import { VouchersService } from '../service/vouchers.service';
import { IVouchers } from '../vouchers.model';

import { VouchersUpdateComponent } from './vouchers-update.component';

describe('Vouchers Management Update Component', () => {
  let comp: VouchersUpdateComponent;
  let fixture: ComponentFixture<VouchersUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let vouchersFormService: VouchersFormService;
  let vouchersService: VouchersService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [VouchersUpdateComponent],
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
      .overrideTemplate(VouchersUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(VouchersUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    vouchersFormService = TestBed.inject(VouchersFormService);
    vouchersService = TestBed.inject(VouchersService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const vouchers: IVouchers = { id: 456 };

      activatedRoute.data = of({ vouchers });
      comp.ngOnInit();

      expect(comp.vouchers).toEqual(vouchers);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVouchers>>();
      const vouchers = { id: 123 };
      jest.spyOn(vouchersFormService, 'getVouchers').mockReturnValue(vouchers);
      jest.spyOn(vouchersService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ vouchers });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: vouchers }));
      saveSubject.complete();

      // THEN
      expect(vouchersFormService.getVouchers).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(vouchersService.update).toHaveBeenCalledWith(expect.objectContaining(vouchers));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVouchers>>();
      const vouchers = { id: 123 };
      jest.spyOn(vouchersFormService, 'getVouchers').mockReturnValue({ id: null });
      jest.spyOn(vouchersService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ vouchers: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: vouchers }));
      saveSubject.complete();

      // THEN
      expect(vouchersFormService.getVouchers).toHaveBeenCalled();
      expect(vouchersService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVouchers>>();
      const vouchers = { id: 123 };
      jest.spyOn(vouchersService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ vouchers });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(vouchersService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
