import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { VoucherDetailsFormService } from './voucher-details-form.service';
import { VoucherDetailsService } from '../service/voucher-details.service';
import { IVoucherDetails } from '../voucher-details.model';

import { VoucherDetailsUpdateComponent } from './voucher-details-update.component';

describe('VoucherDetails Management Update Component', () => {
  let comp: VoucherDetailsUpdateComponent;
  let fixture: ComponentFixture<VoucherDetailsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let voucherDetailsFormService: VoucherDetailsFormService;
  let voucherDetailsService: VoucherDetailsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [VoucherDetailsUpdateComponent],
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
      .overrideTemplate(VoucherDetailsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(VoucherDetailsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    voucherDetailsFormService = TestBed.inject(VoucherDetailsFormService);
    voucherDetailsService = TestBed.inject(VoucherDetailsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const voucherDetails: IVoucherDetails = { id: 456 };

      activatedRoute.data = of({ voucherDetails });
      comp.ngOnInit();

      expect(comp.voucherDetails).toEqual(voucherDetails);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVoucherDetails>>();
      const voucherDetails = { id: 123 };
      jest.spyOn(voucherDetailsFormService, 'getVoucherDetails').mockReturnValue(voucherDetails);
      jest.spyOn(voucherDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ voucherDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: voucherDetails }));
      saveSubject.complete();

      // THEN
      expect(voucherDetailsFormService.getVoucherDetails).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(voucherDetailsService.update).toHaveBeenCalledWith(expect.objectContaining(voucherDetails));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVoucherDetails>>();
      const voucherDetails = { id: 123 };
      jest.spyOn(voucherDetailsFormService, 'getVoucherDetails').mockReturnValue({ id: null });
      jest.spyOn(voucherDetailsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ voucherDetails: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: voucherDetails }));
      saveSubject.complete();

      // THEN
      expect(voucherDetailsFormService.getVoucherDetails).toHaveBeenCalled();
      expect(voucherDetailsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVoucherDetails>>();
      const voucherDetails = { id: 123 };
      jest.spyOn(voucherDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ voucherDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(voucherDetailsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
