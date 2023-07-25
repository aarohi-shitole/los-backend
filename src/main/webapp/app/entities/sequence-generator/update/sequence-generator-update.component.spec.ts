import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SequenceGeneratorFormService } from './sequence-generator-form.service';
import { SequenceGeneratorService } from '../service/sequence-generator.service';
import { ISequenceGenerator } from '../sequence-generator.model';
import { IBranch } from 'app/entities/branch/branch.model';
import { BranchService } from 'app/entities/branch/service/branch.service';

import { SequenceGeneratorUpdateComponent } from './sequence-generator-update.component';

describe('SequenceGenerator Management Update Component', () => {
  let comp: SequenceGeneratorUpdateComponent;
  let fixture: ComponentFixture<SequenceGeneratorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let sequenceGeneratorFormService: SequenceGeneratorFormService;
  let sequenceGeneratorService: SequenceGeneratorService;
  let branchService: BranchService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SequenceGeneratorUpdateComponent],
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
      .overrideTemplate(SequenceGeneratorUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SequenceGeneratorUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    sequenceGeneratorFormService = TestBed.inject(SequenceGeneratorFormService);
    sequenceGeneratorService = TestBed.inject(SequenceGeneratorService);
    branchService = TestBed.inject(BranchService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Branch query and add missing value', () => {
      const sequenceGenerator: ISequenceGenerator = { id: 456 };
      const branch: IBranch = { id: 28927 };
      sequenceGenerator.branch = branch;

      const branchCollection: IBranch[] = [{ id: 53063 }];
      jest.spyOn(branchService, 'query').mockReturnValue(of(new HttpResponse({ body: branchCollection })));
      const additionalBranches = [branch];
      const expectedCollection: IBranch[] = [...additionalBranches, ...branchCollection];
      jest.spyOn(branchService, 'addBranchToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ sequenceGenerator });
      comp.ngOnInit();

      expect(branchService.query).toHaveBeenCalled();
      expect(branchService.addBranchToCollectionIfMissing).toHaveBeenCalledWith(
        branchCollection,
        ...additionalBranches.map(expect.objectContaining)
      );
      expect(comp.branchesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const sequenceGenerator: ISequenceGenerator = { id: 456 };
      const branch: IBranch = { id: 50478 };
      sequenceGenerator.branch = branch;

      activatedRoute.data = of({ sequenceGenerator });
      comp.ngOnInit();

      expect(comp.branchesSharedCollection).toContain(branch);
      expect(comp.sequenceGenerator).toEqual(sequenceGenerator);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISequenceGenerator>>();
      const sequenceGenerator = { id: 123 };
      jest.spyOn(sequenceGeneratorFormService, 'getSequenceGenerator').mockReturnValue(sequenceGenerator);
      jest.spyOn(sequenceGeneratorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sequenceGenerator });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sequenceGenerator }));
      saveSubject.complete();

      // THEN
      expect(sequenceGeneratorFormService.getSequenceGenerator).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(sequenceGeneratorService.update).toHaveBeenCalledWith(expect.objectContaining(sequenceGenerator));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISequenceGenerator>>();
      const sequenceGenerator = { id: 123 };
      jest.spyOn(sequenceGeneratorFormService, 'getSequenceGenerator').mockReturnValue({ id: null });
      jest.spyOn(sequenceGeneratorService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sequenceGenerator: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sequenceGenerator }));
      saveSubject.complete();

      // THEN
      expect(sequenceGeneratorFormService.getSequenceGenerator).toHaveBeenCalled();
      expect(sequenceGeneratorService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISequenceGenerator>>();
      const sequenceGenerator = { id: 123 };
      jest.spyOn(sequenceGeneratorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sequenceGenerator });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(sequenceGeneratorService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareBranch', () => {
      it('Should forward to branchService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(branchService, 'compareBranch');
        comp.compareBranch(entity, entity2);
        expect(branchService.compareBranch).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
