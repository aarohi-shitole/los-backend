import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DocumentsFormService } from './documents-form.service';
import { DocumentsService } from '../service/documents.service';
import { IDocuments } from '../documents.model';
import { IMember } from 'app/entities/member/member.model';
import { MemberService } from 'app/entities/member/service/member.service';
import { IGuarantor } from 'app/entities/guarantor/guarantor.model';
import { GuarantorService } from 'app/entities/guarantor/service/guarantor.service';

import { DocumentsUpdateComponent } from './documents-update.component';

describe('Documents Management Update Component', () => {
  let comp: DocumentsUpdateComponent;
  let fixture: ComponentFixture<DocumentsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let documentsFormService: DocumentsFormService;
  let documentsService: DocumentsService;
  let memberService: MemberService;
  let guarantorService: GuarantorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DocumentsUpdateComponent],
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
      .overrideTemplate(DocumentsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DocumentsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    documentsFormService = TestBed.inject(DocumentsFormService);
    documentsService = TestBed.inject(DocumentsService);
    memberService = TestBed.inject(MemberService);
    guarantorService = TestBed.inject(GuarantorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Member query and add missing value', () => {
      const documents: IDocuments = { id: 456 };
      const member: IMember = { id: 67202 };
      documents.member = member;

      const memberCollection: IMember[] = [{ id: 64467 }];
      jest.spyOn(memberService, 'query').mockReturnValue(of(new HttpResponse({ body: memberCollection })));
      const additionalMembers = [member];
      const expectedCollection: IMember[] = [...additionalMembers, ...memberCollection];
      jest.spyOn(memberService, 'addMemberToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ documents });
      comp.ngOnInit();

      expect(memberService.query).toHaveBeenCalled();
      expect(memberService.addMemberToCollectionIfMissing).toHaveBeenCalledWith(
        memberCollection,
        ...additionalMembers.map(expect.objectContaining)
      );
      expect(comp.membersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Guarantor query and add missing value', () => {
      const documents: IDocuments = { id: 456 };
      const guarantor: IGuarantor = { id: 29910 };
      documents.guarantor = guarantor;

      const guarantorCollection: IGuarantor[] = [{ id: 89323 }];
      jest.spyOn(guarantorService, 'query').mockReturnValue(of(new HttpResponse({ body: guarantorCollection })));
      const additionalGuarantors = [guarantor];
      const expectedCollection: IGuarantor[] = [...additionalGuarantors, ...guarantorCollection];
      jest.spyOn(guarantorService, 'addGuarantorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ documents });
      comp.ngOnInit();

      expect(guarantorService.query).toHaveBeenCalled();
      expect(guarantorService.addGuarantorToCollectionIfMissing).toHaveBeenCalledWith(
        guarantorCollection,
        ...additionalGuarantors.map(expect.objectContaining)
      );
      expect(comp.guarantorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const documents: IDocuments = { id: 456 };
      const member: IMember = { id: 84523 };
      documents.member = member;
      const guarantor: IGuarantor = { id: 51077 };
      documents.guarantor = guarantor;

      activatedRoute.data = of({ documents });
      comp.ngOnInit();

      expect(comp.membersSharedCollection).toContain(member);
      expect(comp.guarantorsSharedCollection).toContain(guarantor);
      expect(comp.documents).toEqual(documents);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDocuments>>();
      const documents = { id: 123 };
      jest.spyOn(documentsFormService, 'getDocuments').mockReturnValue(documents);
      jest.spyOn(documentsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ documents });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: documents }));
      saveSubject.complete();

      // THEN
      expect(documentsFormService.getDocuments).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(documentsService.update).toHaveBeenCalledWith(expect.objectContaining(documents));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDocuments>>();
      const documents = { id: 123 };
      jest.spyOn(documentsFormService, 'getDocuments').mockReturnValue({ id: null });
      jest.spyOn(documentsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ documents: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: documents }));
      saveSubject.complete();

      // THEN
      expect(documentsFormService.getDocuments).toHaveBeenCalled();
      expect(documentsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDocuments>>();
      const documents = { id: 123 };
      jest.spyOn(documentsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ documents });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(documentsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareMember', () => {
      it('Should forward to memberService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(memberService, 'compareMember');
        comp.compareMember(entity, entity2);
        expect(memberService.compareMember).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareGuarantor', () => {
      it('Should forward to guarantorService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(guarantorService, 'compareGuarantor');
        comp.compareGuarantor(entity, entity2);
        expect(guarantorService.compareGuarantor).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
