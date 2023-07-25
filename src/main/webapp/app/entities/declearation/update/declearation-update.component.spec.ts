import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DeclearationFormService } from './declearation-form.service';
import { DeclearationService } from '../service/declearation.service';
import { IDeclearation } from '../declearation.model';
import { IOrganisation } from 'app/entities/organisation/organisation.model';
import { OrganisationService } from 'app/entities/organisation/service/organisation.service';

import { DeclearationUpdateComponent } from './declearation-update.component';

describe('Declearation Management Update Component', () => {
  let comp: DeclearationUpdateComponent;
  let fixture: ComponentFixture<DeclearationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let declearationFormService: DeclearationFormService;
  let declearationService: DeclearationService;
  let organisationService: OrganisationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DeclearationUpdateComponent],
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
      .overrideTemplate(DeclearationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DeclearationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    declearationFormService = TestBed.inject(DeclearationFormService);
    declearationService = TestBed.inject(DeclearationService);
    organisationService = TestBed.inject(OrganisationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Organisation query and add missing value', () => {
      const declearation: IDeclearation = { id: 456 };
      const organisation: IOrganisation = { id: 46882 };
      declearation.organisation = organisation;

      const organisationCollection: IOrganisation[] = [{ id: 88269 }];
      jest.spyOn(organisationService, 'query').mockReturnValue(of(new HttpResponse({ body: organisationCollection })));
      const additionalOrganisations = [organisation];
      const expectedCollection: IOrganisation[] = [...additionalOrganisations, ...organisationCollection];
      jest.spyOn(organisationService, 'addOrganisationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ declearation });
      comp.ngOnInit();

      expect(organisationService.query).toHaveBeenCalled();
      expect(organisationService.addOrganisationToCollectionIfMissing).toHaveBeenCalledWith(
        organisationCollection,
        ...additionalOrganisations.map(expect.objectContaining)
      );
      expect(comp.organisationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const declearation: IDeclearation = { id: 456 };
      const organisation: IOrganisation = { id: 37591 };
      declearation.organisation = organisation;

      activatedRoute.data = of({ declearation });
      comp.ngOnInit();

      expect(comp.organisationsSharedCollection).toContain(organisation);
      expect(comp.declearation).toEqual(declearation);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDeclearation>>();
      const declearation = { id: 123 };
      jest.spyOn(declearationFormService, 'getDeclearation').mockReturnValue(declearation);
      jest.spyOn(declearationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ declearation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: declearation }));
      saveSubject.complete();

      // THEN
      expect(declearationFormService.getDeclearation).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(declearationService.update).toHaveBeenCalledWith(expect.objectContaining(declearation));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDeclearation>>();
      const declearation = { id: 123 };
      jest.spyOn(declearationFormService, 'getDeclearation').mockReturnValue({ id: null });
      jest.spyOn(declearationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ declearation: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: declearation }));
      saveSubject.complete();

      // THEN
      expect(declearationFormService.getDeclearation).toHaveBeenCalled();
      expect(declearationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDeclearation>>();
      const declearation = { id: 123 };
      jest.spyOn(declearationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ declearation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(declearationService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
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
