import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { OrgPrerequisiteDocFormService } from './org-prerequisite-doc-form.service';
import { OrgPrerequisiteDocService } from '../service/org-prerequisite-doc.service';
import { IOrgPrerequisiteDoc } from '../org-prerequisite-doc.model';
import { IProduct } from 'app/entities/product/product.model';
import { ProductService } from 'app/entities/product/service/product.service';
import { IOrganisation } from 'app/entities/organisation/organisation.model';
import { OrganisationService } from 'app/entities/organisation/service/organisation.service';

import { OrgPrerequisiteDocUpdateComponent } from './org-prerequisite-doc-update.component';

describe('OrgPrerequisiteDoc Management Update Component', () => {
  let comp: OrgPrerequisiteDocUpdateComponent;
  let fixture: ComponentFixture<OrgPrerequisiteDocUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let orgPrerequisiteDocFormService: OrgPrerequisiteDocFormService;
  let orgPrerequisiteDocService: OrgPrerequisiteDocService;
  let productService: ProductService;
  let organisationService: OrganisationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [OrgPrerequisiteDocUpdateComponent],
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
      .overrideTemplate(OrgPrerequisiteDocUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OrgPrerequisiteDocUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    orgPrerequisiteDocFormService = TestBed.inject(OrgPrerequisiteDocFormService);
    orgPrerequisiteDocService = TestBed.inject(OrgPrerequisiteDocService);
    productService = TestBed.inject(ProductService);
    organisationService = TestBed.inject(OrganisationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Product query and add missing value', () => {
      const orgPrerequisiteDoc: IOrgPrerequisiteDoc = { id: 456 };
      const product: IProduct = { id: 75663 };
      orgPrerequisiteDoc.product = product;

      const productCollection: IProduct[] = [{ id: 95056 }];
      jest.spyOn(productService, 'query').mockReturnValue(of(new HttpResponse({ body: productCollection })));
      const additionalProducts = [product];
      const expectedCollection: IProduct[] = [...additionalProducts, ...productCollection];
      jest.spyOn(productService, 'addProductToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ orgPrerequisiteDoc });
      comp.ngOnInit();

      expect(productService.query).toHaveBeenCalled();
      expect(productService.addProductToCollectionIfMissing).toHaveBeenCalledWith(
        productCollection,
        ...additionalProducts.map(expect.objectContaining)
      );
      expect(comp.productsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Organisation query and add missing value', () => {
      const orgPrerequisiteDoc: IOrgPrerequisiteDoc = { id: 456 };
      const organisation: IOrganisation = { id: 11990 };
      orgPrerequisiteDoc.organisation = organisation;

      const organisationCollection: IOrganisation[] = [{ id: 32691 }];
      jest.spyOn(organisationService, 'query').mockReturnValue(of(new HttpResponse({ body: organisationCollection })));
      const additionalOrganisations = [organisation];
      const expectedCollection: IOrganisation[] = [...additionalOrganisations, ...organisationCollection];
      jest.spyOn(organisationService, 'addOrganisationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ orgPrerequisiteDoc });
      comp.ngOnInit();

      expect(organisationService.query).toHaveBeenCalled();
      expect(organisationService.addOrganisationToCollectionIfMissing).toHaveBeenCalledWith(
        organisationCollection,
        ...additionalOrganisations.map(expect.objectContaining)
      );
      expect(comp.organisationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const orgPrerequisiteDoc: IOrgPrerequisiteDoc = { id: 456 };
      const product: IProduct = { id: 5731 };
      orgPrerequisiteDoc.product = product;
      const organisation: IOrganisation = { id: 74268 };
      orgPrerequisiteDoc.organisation = organisation;

      activatedRoute.data = of({ orgPrerequisiteDoc });
      comp.ngOnInit();

      expect(comp.productsSharedCollection).toContain(product);
      expect(comp.organisationsSharedCollection).toContain(organisation);
      expect(comp.orgPrerequisiteDoc).toEqual(orgPrerequisiteDoc);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrgPrerequisiteDoc>>();
      const orgPrerequisiteDoc = { id: 123 };
      jest.spyOn(orgPrerequisiteDocFormService, 'getOrgPrerequisiteDoc').mockReturnValue(orgPrerequisiteDoc);
      jest.spyOn(orgPrerequisiteDocService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ orgPrerequisiteDoc });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: orgPrerequisiteDoc }));
      saveSubject.complete();

      // THEN
      expect(orgPrerequisiteDocFormService.getOrgPrerequisiteDoc).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(orgPrerequisiteDocService.update).toHaveBeenCalledWith(expect.objectContaining(orgPrerequisiteDoc));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrgPrerequisiteDoc>>();
      const orgPrerequisiteDoc = { id: 123 };
      jest.spyOn(orgPrerequisiteDocFormService, 'getOrgPrerequisiteDoc').mockReturnValue({ id: null });
      jest.spyOn(orgPrerequisiteDocService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ orgPrerequisiteDoc: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: orgPrerequisiteDoc }));
      saveSubject.complete();

      // THEN
      expect(orgPrerequisiteDocFormService.getOrgPrerequisiteDoc).toHaveBeenCalled();
      expect(orgPrerequisiteDocService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrgPrerequisiteDoc>>();
      const orgPrerequisiteDoc = { id: 123 };
      jest.spyOn(orgPrerequisiteDocService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ orgPrerequisiteDoc });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(orgPrerequisiteDocService.update).toHaveBeenCalled();
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

    describe('compareOrganisation', () => {
      it('Should forward to organisationService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(organisationService, 'compareOrganisation');
        comp.compareOrganisation(entity, entity2);
        expect(organisationService.compareOrganisation).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
