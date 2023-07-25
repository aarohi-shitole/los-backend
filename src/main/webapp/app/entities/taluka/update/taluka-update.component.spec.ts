import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TalukaFormService } from './taluka-form.service';
import { TalukaService } from '../service/taluka.service';
import { ITaluka } from '../taluka.model';
import { IDistrict } from 'app/entities/district/district.model';
import { DistrictService } from 'app/entities/district/service/district.service';

import { TalukaUpdateComponent } from './taluka-update.component';

describe('Taluka Management Update Component', () => {
  let comp: TalukaUpdateComponent;
  let fixture: ComponentFixture<TalukaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let talukaFormService: TalukaFormService;
  let talukaService: TalukaService;
  let districtService: DistrictService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TalukaUpdateComponent],
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
      .overrideTemplate(TalukaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TalukaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    talukaFormService = TestBed.inject(TalukaFormService);
    talukaService = TestBed.inject(TalukaService);
    districtService = TestBed.inject(DistrictService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call District query and add missing value', () => {
      const taluka: ITaluka = { id: 456 };
      const district: IDistrict = { id: 75008 };
      taluka.district = district;

      const districtCollection: IDistrict[] = [{ id: 89581 }];
      jest.spyOn(districtService, 'query').mockReturnValue(of(new HttpResponse({ body: districtCollection })));
      const additionalDistricts = [district];
      const expectedCollection: IDistrict[] = [...additionalDistricts, ...districtCollection];
      jest.spyOn(districtService, 'addDistrictToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ taluka });
      comp.ngOnInit();

      expect(districtService.query).toHaveBeenCalled();
      expect(districtService.addDistrictToCollectionIfMissing).toHaveBeenCalledWith(
        districtCollection,
        ...additionalDistricts.map(expect.objectContaining)
      );
      expect(comp.districtsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const taluka: ITaluka = { id: 456 };
      const district: IDistrict = { id: 10771 };
      taluka.district = district;

      activatedRoute.data = of({ taluka });
      comp.ngOnInit();

      expect(comp.districtsSharedCollection).toContain(district);
      expect(comp.taluka).toEqual(taluka);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITaluka>>();
      const taluka = { id: 123 };
      jest.spyOn(talukaFormService, 'getTaluka').mockReturnValue(taluka);
      jest.spyOn(talukaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ taluka });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: taluka }));
      saveSubject.complete();

      // THEN
      expect(talukaFormService.getTaluka).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(talukaService.update).toHaveBeenCalledWith(expect.objectContaining(taluka));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITaluka>>();
      const taluka = { id: 123 };
      jest.spyOn(talukaFormService, 'getTaluka').mockReturnValue({ id: null });
      jest.spyOn(talukaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ taluka: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: taluka }));
      saveSubject.complete();

      // THEN
      expect(talukaFormService.getTaluka).toHaveBeenCalled();
      expect(talukaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITaluka>>();
      const taluka = { id: 123 };
      jest.spyOn(talukaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ taluka });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(talukaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareDistrict', () => {
      it('Should forward to districtService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(districtService, 'compareDistrict');
        comp.compareDistrict(entity, entity2);
        expect(districtService.compareDistrict).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
