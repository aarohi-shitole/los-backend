import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { NpaSettingFormService } from './npa-setting-form.service';
import { NpaSettingService } from '../service/npa-setting.service';
import { INpaSetting } from '../npa-setting.model';
import { IOrganisation } from 'app/entities/organisation/organisation.model';
import { OrganisationService } from 'app/entities/organisation/service/organisation.service';

import { NpaSettingUpdateComponent } from './npa-setting-update.component';

describe('NpaSetting Management Update Component', () => {
  let comp: NpaSettingUpdateComponent;
  let fixture: ComponentFixture<NpaSettingUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let npaSettingFormService: NpaSettingFormService;
  let npaSettingService: NpaSettingService;
  let organisationService: OrganisationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [NpaSettingUpdateComponent],
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
      .overrideTemplate(NpaSettingUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NpaSettingUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    npaSettingFormService = TestBed.inject(NpaSettingFormService);
    npaSettingService = TestBed.inject(NpaSettingService);
    organisationService = TestBed.inject(OrganisationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Organisation query and add missing value', () => {
      const npaSetting: INpaSetting = { id: 456 };
      const organisation: IOrganisation = { id: 19680 };
      npaSetting.organisation = organisation;

      const organisationCollection: IOrganisation[] = [{ id: 48010 }];
      jest.spyOn(organisationService, 'query').mockReturnValue(of(new HttpResponse({ body: organisationCollection })));
      const additionalOrganisations = [organisation];
      const expectedCollection: IOrganisation[] = [...additionalOrganisations, ...organisationCollection];
      jest.spyOn(organisationService, 'addOrganisationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ npaSetting });
      comp.ngOnInit();

      expect(organisationService.query).toHaveBeenCalled();
      expect(organisationService.addOrganisationToCollectionIfMissing).toHaveBeenCalledWith(
        organisationCollection,
        ...additionalOrganisations.map(expect.objectContaining)
      );
      expect(comp.organisationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const npaSetting: INpaSetting = { id: 456 };
      const organisation: IOrganisation = { id: 57326 };
      npaSetting.organisation = organisation;

      activatedRoute.data = of({ npaSetting });
      comp.ngOnInit();

      expect(comp.organisationsSharedCollection).toContain(organisation);
      expect(comp.npaSetting).toEqual(npaSetting);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INpaSetting>>();
      const npaSetting = { id: 123 };
      jest.spyOn(npaSettingFormService, 'getNpaSetting').mockReturnValue(npaSetting);
      jest.spyOn(npaSettingService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ npaSetting });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: npaSetting }));
      saveSubject.complete();

      // THEN
      expect(npaSettingFormService.getNpaSetting).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(npaSettingService.update).toHaveBeenCalledWith(expect.objectContaining(npaSetting));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INpaSetting>>();
      const npaSetting = { id: 123 };
      jest.spyOn(npaSettingFormService, 'getNpaSetting').mockReturnValue({ id: null });
      jest.spyOn(npaSettingService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ npaSetting: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: npaSetting }));
      saveSubject.complete();

      // THEN
      expect(npaSettingFormService.getNpaSetting).toHaveBeenCalled();
      expect(npaSettingService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INpaSetting>>();
      const npaSetting = { id: 123 };
      jest.spyOn(npaSettingService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ npaSetting });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(npaSettingService.update).toHaveBeenCalled();
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
