import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MasterAgreementFormService } from './master-agreement-form.service';
import { MasterAgreementService } from '../service/master-agreement.service';
import { IMasterAgreement } from '../master-agreement.model';

import { MasterAgreementUpdateComponent } from './master-agreement-update.component';

describe('MasterAgreement Management Update Component', () => {
  let comp: MasterAgreementUpdateComponent;
  let fixture: ComponentFixture<MasterAgreementUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let masterAgreementFormService: MasterAgreementFormService;
  let masterAgreementService: MasterAgreementService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MasterAgreementUpdateComponent],
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
      .overrideTemplate(MasterAgreementUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MasterAgreementUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    masterAgreementFormService = TestBed.inject(MasterAgreementFormService);
    masterAgreementService = TestBed.inject(MasterAgreementService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const masterAgreement: IMasterAgreement = { id: 456 };

      activatedRoute.data = of({ masterAgreement });
      comp.ngOnInit();

      expect(comp.masterAgreement).toEqual(masterAgreement);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMasterAgreement>>();
      const masterAgreement = { id: 123 };
      jest.spyOn(masterAgreementFormService, 'getMasterAgreement').mockReturnValue(masterAgreement);
      jest.spyOn(masterAgreementService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ masterAgreement });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: masterAgreement }));
      saveSubject.complete();

      // THEN
      expect(masterAgreementFormService.getMasterAgreement).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(masterAgreementService.update).toHaveBeenCalledWith(expect.objectContaining(masterAgreement));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMasterAgreement>>();
      const masterAgreement = { id: 123 };
      jest.spyOn(masterAgreementFormService, 'getMasterAgreement').mockReturnValue({ id: null });
      jest.spyOn(masterAgreementService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ masterAgreement: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: masterAgreement }));
      saveSubject.complete();

      // THEN
      expect(masterAgreementFormService.getMasterAgreement).toHaveBeenCalled();
      expect(masterAgreementService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMasterAgreement>>();
      const masterAgreement = { id: 123 };
      jest.spyOn(masterAgreementService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ masterAgreement });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(masterAgreementService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
