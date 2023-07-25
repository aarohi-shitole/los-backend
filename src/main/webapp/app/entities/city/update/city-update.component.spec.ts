import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CityFormService } from './city-form.service';
import { CityService } from '../service/city.service';
import { ICity } from '../city.model';
import { ITaluka } from 'app/entities/taluka/taluka.model';
import { TalukaService } from 'app/entities/taluka/service/taluka.service';

import { CityUpdateComponent } from './city-update.component';

describe('City Management Update Component', () => {
  let comp: CityUpdateComponent;
  let fixture: ComponentFixture<CityUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cityFormService: CityFormService;
  let cityService: CityService;
  let talukaService: TalukaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CityUpdateComponent],
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
      .overrideTemplate(CityUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CityUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cityFormService = TestBed.inject(CityFormService);
    cityService = TestBed.inject(CityService);
    talukaService = TestBed.inject(TalukaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Taluka query and add missing value', () => {
      const city: ICity = { id: 456 };
      const taluka: ITaluka = { id: 91554 };
      city.taluka = taluka;

      const talukaCollection: ITaluka[] = [{ id: 47373 }];
      jest.spyOn(talukaService, 'query').mockReturnValue(of(new HttpResponse({ body: talukaCollection })));
      const additionalTalukas = [taluka];
      const expectedCollection: ITaluka[] = [...additionalTalukas, ...talukaCollection];
      jest.spyOn(talukaService, 'addTalukaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ city });
      comp.ngOnInit();

      expect(talukaService.query).toHaveBeenCalled();
      expect(talukaService.addTalukaToCollectionIfMissing).toHaveBeenCalledWith(
        talukaCollection,
        ...additionalTalukas.map(expect.objectContaining)
      );
      expect(comp.talukasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const city: ICity = { id: 456 };
      const taluka: ITaluka = { id: 85187 };
      city.taluka = taluka;

      activatedRoute.data = of({ city });
      comp.ngOnInit();

      expect(comp.talukasSharedCollection).toContain(taluka);
      expect(comp.city).toEqual(city);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICity>>();
      const city = { id: 123 };
      jest.spyOn(cityFormService, 'getCity').mockReturnValue(city);
      jest.spyOn(cityService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ city });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: city }));
      saveSubject.complete();

      // THEN
      expect(cityFormService.getCity).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(cityService.update).toHaveBeenCalledWith(expect.objectContaining(city));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICity>>();
      const city = { id: 123 };
      jest.spyOn(cityFormService, 'getCity').mockReturnValue({ id: null });
      jest.spyOn(cityService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ city: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: city }));
      saveSubject.complete();

      // THEN
      expect(cityFormService.getCity).toHaveBeenCalled();
      expect(cityService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICity>>();
      const city = { id: 123 };
      jest.spyOn(cityService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ city });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cityService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareTaluka', () => {
      it('Should forward to talukaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(talukaService, 'compareTaluka');
        comp.compareTaluka(entity, entity2);
        expect(talukaService.compareTaluka).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
