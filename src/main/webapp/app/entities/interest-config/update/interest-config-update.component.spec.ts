import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { InterestConfigFormService } from './interest-config-form.service';
import { InterestConfigService } from '../service/interest-config.service';
import { IInterestConfig } from '../interest-config.model';
import { IProduct } from 'app/entities/product/product.model';
import { ProductService } from 'app/entities/product/service/product.service';

import { InterestConfigUpdateComponent } from './interest-config-update.component';

describe('InterestConfig Management Update Component', () => {
  let comp: InterestConfigUpdateComponent;
  let fixture: ComponentFixture<InterestConfigUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let interestConfigFormService: InterestConfigFormService;
  let interestConfigService: InterestConfigService;
  let productService: ProductService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [InterestConfigUpdateComponent],
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
      .overrideTemplate(InterestConfigUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(InterestConfigUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    interestConfigFormService = TestBed.inject(InterestConfigFormService);
    interestConfigService = TestBed.inject(InterestConfigService);
    productService = TestBed.inject(ProductService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Product query and add missing value', () => {
      const interestConfig: IInterestConfig = { id: 456 };
      const product: IProduct = { id: 51779 };
      interestConfig.product = product;

      const productCollection: IProduct[] = [{ id: 44289 }];
      jest.spyOn(productService, 'query').mockReturnValue(of(new HttpResponse({ body: productCollection })));
      const additionalProducts = [product];
      const expectedCollection: IProduct[] = [...additionalProducts, ...productCollection];
      jest.spyOn(productService, 'addProductToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ interestConfig });
      comp.ngOnInit();

      expect(productService.query).toHaveBeenCalled();
      expect(productService.addProductToCollectionIfMissing).toHaveBeenCalledWith(
        productCollection,
        ...additionalProducts.map(expect.objectContaining)
      );
      expect(comp.productsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const interestConfig: IInterestConfig = { id: 456 };
      const product: IProduct = { id: 79819 };
      interestConfig.product = product;

      activatedRoute.data = of({ interestConfig });
      comp.ngOnInit();

      expect(comp.productsSharedCollection).toContain(product);
      expect(comp.interestConfig).toEqual(interestConfig);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInterestConfig>>();
      const interestConfig = { id: 123 };
      jest.spyOn(interestConfigFormService, 'getInterestConfig').mockReturnValue(interestConfig);
      jest.spyOn(interestConfigService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ interestConfig });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: interestConfig }));
      saveSubject.complete();

      // THEN
      expect(interestConfigFormService.getInterestConfig).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(interestConfigService.update).toHaveBeenCalledWith(expect.objectContaining(interestConfig));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInterestConfig>>();
      const interestConfig = { id: 123 };
      jest.spyOn(interestConfigFormService, 'getInterestConfig').mockReturnValue({ id: null });
      jest.spyOn(interestConfigService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ interestConfig: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: interestConfig }));
      saveSubject.complete();

      // THEN
      expect(interestConfigFormService.getInterestConfig).toHaveBeenCalled();
      expect(interestConfigService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInterestConfig>>();
      const interestConfig = { id: 123 };
      jest.spyOn(interestConfigService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ interestConfig });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(interestConfigService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
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
