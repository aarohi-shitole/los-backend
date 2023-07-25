import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { GuarantorFormService } from './guarantor-form.service';
import { GuarantorService } from '../service/guarantor.service';
import { IGuarantor } from '../guarantor.model';
import { IMemberAssets } from 'app/entities/member-assets/member-assets.model';
import { MemberAssetsService } from 'app/entities/member-assets/service/member-assets.service';
import { IEmployementDetails } from 'app/entities/employement-details/employement-details.model';
import { EmployementDetailsService } from 'app/entities/employement-details/service/employement-details.service';
import { IMember } from 'app/entities/member/member.model';
import { MemberService } from 'app/entities/member/service/member.service';

import { GuarantorUpdateComponent } from './guarantor-update.component';

describe('Guarantor Management Update Component', () => {
  let comp: GuarantorUpdateComponent;
  let fixture: ComponentFixture<GuarantorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let guarantorFormService: GuarantorFormService;
  let guarantorService: GuarantorService;
  let memberAssetsService: MemberAssetsService;
  let employementDetailsService: EmployementDetailsService;
  let memberService: MemberService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [GuarantorUpdateComponent],
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
      .overrideTemplate(GuarantorUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(GuarantorUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    guarantorFormService = TestBed.inject(GuarantorFormService);
    guarantorService = TestBed.inject(GuarantorService);
    memberAssetsService = TestBed.inject(MemberAssetsService);
    employementDetailsService = TestBed.inject(EmployementDetailsService);
    memberService = TestBed.inject(MemberService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call MemberAssets query and add missing value', () => {
      const guarantor: IGuarantor = { id: 456 };
      const memberAssets: IMemberAssets = { id: 8465 };
      guarantor.memberAssets = memberAssets;

      const memberAssetsCollection: IMemberAssets[] = [{ id: 44860 }];
      jest.spyOn(memberAssetsService, 'query').mockReturnValue(of(new HttpResponse({ body: memberAssetsCollection })));
      const additionalMemberAssets = [memberAssets];
      const expectedCollection: IMemberAssets[] = [...additionalMemberAssets, ...memberAssetsCollection];
      jest.spyOn(memberAssetsService, 'addMemberAssetsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ guarantor });
      comp.ngOnInit();

      expect(memberAssetsService.query).toHaveBeenCalled();
      expect(memberAssetsService.addMemberAssetsToCollectionIfMissing).toHaveBeenCalledWith(
        memberAssetsCollection,
        ...additionalMemberAssets.map(expect.objectContaining)
      );
      expect(comp.memberAssetsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call EmployementDetails query and add missing value', () => {
      const guarantor: IGuarantor = { id: 456 };
      const employementDetails: IEmployementDetails = { id: 67191 };
      guarantor.employementDetails = employementDetails;

      const employementDetailsCollection: IEmployementDetails[] = [{ id: 83158 }];
      jest.spyOn(employementDetailsService, 'query').mockReturnValue(of(new HttpResponse({ body: employementDetailsCollection })));
      const additionalEmployementDetails = [employementDetails];
      const expectedCollection: IEmployementDetails[] = [...additionalEmployementDetails, ...employementDetailsCollection];
      jest.spyOn(employementDetailsService, 'addEmployementDetailsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ guarantor });
      comp.ngOnInit();

      expect(employementDetailsService.query).toHaveBeenCalled();
      expect(employementDetailsService.addEmployementDetailsToCollectionIfMissing).toHaveBeenCalledWith(
        employementDetailsCollection,
        ...additionalEmployementDetails.map(expect.objectContaining)
      );
      expect(comp.employementDetailsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Member query and add missing value', () => {
      const guarantor: IGuarantor = { id: 456 };
      const member: IMember = { id: 71453 };
      guarantor.member = member;

      const memberCollection: IMember[] = [{ id: 50448 }];
      jest.spyOn(memberService, 'query').mockReturnValue(of(new HttpResponse({ body: memberCollection })));
      const additionalMembers = [member];
      const expectedCollection: IMember[] = [...additionalMembers, ...memberCollection];
      jest.spyOn(memberService, 'addMemberToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ guarantor });
      comp.ngOnInit();

      expect(memberService.query).toHaveBeenCalled();
      expect(memberService.addMemberToCollectionIfMissing).toHaveBeenCalledWith(
        memberCollection,
        ...additionalMembers.map(expect.objectContaining)
      );
      expect(comp.membersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const guarantor: IGuarantor = { id: 456 };
      const memberAssets: IMemberAssets = { id: 35650 };
      guarantor.memberAssets = memberAssets;
      const employementDetails: IEmployementDetails = { id: 83804 };
      guarantor.employementDetails = employementDetails;
      const member: IMember = { id: 95167 };
      guarantor.member = member;

      activatedRoute.data = of({ guarantor });
      comp.ngOnInit();

      expect(comp.memberAssetsSharedCollection).toContain(memberAssets);
      expect(comp.employementDetailsSharedCollection).toContain(employementDetails);
      expect(comp.membersSharedCollection).toContain(member);
      expect(comp.guarantor).toEqual(guarantor);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGuarantor>>();
      const guarantor = { id: 123 };
      jest.spyOn(guarantorFormService, 'getGuarantor').mockReturnValue(guarantor);
      jest.spyOn(guarantorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ guarantor });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: guarantor }));
      saveSubject.complete();

      // THEN
      expect(guarantorFormService.getGuarantor).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(guarantorService.update).toHaveBeenCalledWith(expect.objectContaining(guarantor));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGuarantor>>();
      const guarantor = { id: 123 };
      jest.spyOn(guarantorFormService, 'getGuarantor').mockReturnValue({ id: null });
      jest.spyOn(guarantorService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ guarantor: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: guarantor }));
      saveSubject.complete();

      // THEN
      expect(guarantorFormService.getGuarantor).toHaveBeenCalled();
      expect(guarantorService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGuarantor>>();
      const guarantor = { id: 123 };
      jest.spyOn(guarantorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ guarantor });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(guarantorService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareMemberAssets', () => {
      it('Should forward to memberAssetsService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(memberAssetsService, 'compareMemberAssets');
        comp.compareMemberAssets(entity, entity2);
        expect(memberAssetsService.compareMemberAssets).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareEmployementDetails', () => {
      it('Should forward to employementDetailsService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(employementDetailsService, 'compareEmployementDetails');
        comp.compareEmployementDetails(entity, entity2);
        expect(employementDetailsService.compareEmployementDetails).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareMember', () => {
      it('Should forward to memberService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(memberService, 'compareMember');
        comp.compareMember(entity, entity2);
        expect(memberService.compareMember).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
