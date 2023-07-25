import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EnquiryDetailsFormService } from './enquiry-details-form.service';
import { EnquiryDetailsService } from '../service/enquiry-details.service';
import { IEnquiryDetails } from '../enquiry-details.model';
import { IState } from 'app/entities/state/state.model';
import { StateService } from 'app/entities/state/service/state.service';
import { IDistrict } from 'app/entities/district/district.model';
import { DistrictService } from 'app/entities/district/service/district.service';
import { ITaluka } from 'app/entities/taluka/taluka.model';
import { TalukaService } from 'app/entities/taluka/service/taluka.service';
import { ICity } from 'app/entities/city/city.model';
import { CityService } from 'app/entities/city/service/city.service';
import { IProduct } from 'app/entities/product/product.model';
import { ProductService } from 'app/entities/product/service/product.service';

import { EnquiryDetailsUpdateComponent } from './enquiry-details-update.component';

describe('EnquiryDetails Management Update Component', () => {
  let comp: EnquiryDetailsUpdateComponent;
  let fixture: ComponentFixture<EnquiryDetailsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let enquiryDetailsFormService: EnquiryDetailsFormService;
  let enquiryDetailsService: EnquiryDetailsService;
  let stateService: StateService;
  let districtService: DistrictService;
  let talukaService: TalukaService;
  let cityService: CityService;
  let productService: ProductService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EnquiryDetailsUpdateComponent],
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
      .overrideTemplate(EnquiryDetailsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EnquiryDetailsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    enquiryDetailsFormService = TestBed.inject(EnquiryDetailsFormService);
    enquiryDetailsService = TestBed.inject(EnquiryDetailsService);
    stateService = TestBed.inject(StateService);
    districtService = TestBed.inject(DistrictService);
    talukaService = TestBed.inject(TalukaService);
    cityService = TestBed.inject(CityService);
    productService = TestBed.inject(ProductService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call State query and add missing value', () => {
      const enquiryDetails: IEnquiryDetails = { id: 456 };
      const state: IState = { id: 83299 };
      enquiryDetails.state = state;

      const stateCollection: IState[] = [{ id: 97597 }];
      jest.spyOn(stateService, 'query').mockReturnValue(of(new HttpResponse({ body: stateCollection })));
      const additionalStates = [state];
      const expectedCollection: IState[] = [...additionalStates, ...stateCollection];
      jest.spyOn(stateService, 'addStateToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ enquiryDetails });
      comp.ngOnInit();

      expect(stateService.query).toHaveBeenCalled();
      expect(stateService.addStateToCollectionIfMissing).toHaveBeenCalledWith(
        stateCollection,
        ...additionalStates.map(expect.objectContaining)
      );
      expect(comp.statesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call District query and add missing value', () => {
      const enquiryDetails: IEnquiryDetails = { id: 456 };
      const district: IDistrict = { id: 44310 };
      enquiryDetails.district = district;

      const districtCollection: IDistrict[] = [{ id: 6341 }];
      jest.spyOn(districtService, 'query').mockReturnValue(of(new HttpResponse({ body: districtCollection })));
      const additionalDistricts = [district];
      const expectedCollection: IDistrict[] = [...additionalDistricts, ...districtCollection];
      jest.spyOn(districtService, 'addDistrictToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ enquiryDetails });
      comp.ngOnInit();

      expect(districtService.query).toHaveBeenCalled();
      expect(districtService.addDistrictToCollectionIfMissing).toHaveBeenCalledWith(
        districtCollection,
        ...additionalDistricts.map(expect.objectContaining)
      );
      expect(comp.districtsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Taluka query and add missing value', () => {
      const enquiryDetails: IEnquiryDetails = { id: 456 };
      const taluka: ITaluka = { id: 41132 };
      enquiryDetails.taluka = taluka;

      const talukaCollection: ITaluka[] = [{ id: 49455 }];
      jest.spyOn(talukaService, 'query').mockReturnValue(of(new HttpResponse({ body: talukaCollection })));
      const additionalTalukas = [taluka];
      const expectedCollection: ITaluka[] = [...additionalTalukas, ...talukaCollection];
      jest.spyOn(talukaService, 'addTalukaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ enquiryDetails });
      comp.ngOnInit();

      expect(talukaService.query).toHaveBeenCalled();
      expect(talukaService.addTalukaToCollectionIfMissing).toHaveBeenCalledWith(
        talukaCollection,
        ...additionalTalukas.map(expect.objectContaining)
      );
      expect(comp.talukasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call City query and add missing value', () => {
      const enquiryDetails: IEnquiryDetails = { id: 456 };
      const city: ICity = { id: 38823 };
      enquiryDetails.city = city;

      const cityCollection: ICity[] = [{ id: 35647 }];
      jest.spyOn(cityService, 'query').mockReturnValue(of(new HttpResponse({ body: cityCollection })));
      const additionalCities = [city];
      const expectedCollection: ICity[] = [...additionalCities, ...cityCollection];
      jest.spyOn(cityService, 'addCityToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ enquiryDetails });
      comp.ngOnInit();

      expect(cityService.query).toHaveBeenCalled();
      expect(cityService.addCityToCollectionIfMissing).toHaveBeenCalledWith(
        cityCollection,
        ...additionalCities.map(expect.objectContaining)
      );
      expect(comp.citiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Product query and add missing value', () => {
      const enquiryDetails: IEnquiryDetails = { id: 456 };
      const product: IProduct = { id: 42548 };
      enquiryDetails.product = product;

      const productCollection: IProduct[] = [{ id: 60701 }];
      jest.spyOn(productService, 'query').mockReturnValue(of(new HttpResponse({ body: productCollection })));
      const additionalProducts = [product];
      const expectedCollection: IProduct[] = [...additionalProducts, ...productCollection];
      jest.spyOn(productService, 'addProductToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ enquiryDetails });
      comp.ngOnInit();

      expect(productService.query).toHaveBeenCalled();
      expect(productService.addProductToCollectionIfMissing).toHaveBeenCalledWith(
        productCollection,
        ...additionalProducts.map(expect.objectContaining)
      );
      expect(comp.productsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const enquiryDetails: IEnquiryDetails = { id: 456 };
      const state: IState = { id: 35804 };
      enquiryDetails.state = state;
      const district: IDistrict = { id: 80429 };
      enquiryDetails.district = district;
      const taluka: ITaluka = { id: 66589 };
      enquiryDetails.taluka = taluka;
      const city: ICity = { id: 72319 };
      enquiryDetails.city = city;
      const product: IProduct = { id: 5906 };
      enquiryDetails.product = product;

      activatedRoute.data = of({ enquiryDetails });
      comp.ngOnInit();

      expect(comp.statesSharedCollection).toContain(state);
      expect(comp.districtsSharedCollection).toContain(district);
      expect(comp.talukasSharedCollection).toContain(taluka);
      expect(comp.citiesSharedCollection).toContain(city);
      expect(comp.productsSharedCollection).toContain(product);
      expect(comp.enquiryDetails).toEqual(enquiryDetails);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEnquiryDetails>>();
      const enquiryDetails = { id: 123 };
      jest.spyOn(enquiryDetailsFormService, 'getEnquiryDetails').mockReturnValue(enquiryDetails);
      jest.spyOn(enquiryDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ enquiryDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: enquiryDetails }));
      saveSubject.complete();

      // THEN
      expect(enquiryDetailsFormService.getEnquiryDetails).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(enquiryDetailsService.update).toHaveBeenCalledWith(expect.objectContaining(enquiryDetails));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEnquiryDetails>>();
      const enquiryDetails = { id: 123 };
      jest.spyOn(enquiryDetailsFormService, 'getEnquiryDetails').mockReturnValue({ id: null });
      jest.spyOn(enquiryDetailsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ enquiryDetails: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: enquiryDetails }));
      saveSubject.complete();

      // THEN
      expect(enquiryDetailsFormService.getEnquiryDetails).toHaveBeenCalled();
      expect(enquiryDetailsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEnquiryDetails>>();
      const enquiryDetails = { id: 123 };
      jest.spyOn(enquiryDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ enquiryDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(enquiryDetailsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareState', () => {
      it('Should forward to stateService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(stateService, 'compareState');
        comp.compareState(entity, entity2);
        expect(stateService.compareState).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareDistrict', () => {
      it('Should forward to districtService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(districtService, 'compareDistrict');
        comp.compareDistrict(entity, entity2);
        expect(districtService.compareDistrict).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareTaluka', () => {
      it('Should forward to talukaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(talukaService, 'compareTaluka');
        comp.compareTaluka(entity, entity2);
        expect(talukaService.compareTaluka).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCity', () => {
      it('Should forward to cityService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(cityService, 'compareCity');
        comp.compareCity(entity, entity2);
        expect(cityService.compareCity).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareProduct', () => {
      it('Should forward to productService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(productService, 'compareProduct');
        comp.compareProduct(entity, entity2);
        expect(productService.compareProduct).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
