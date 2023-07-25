import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MasterAgreementDetailComponent } from './master-agreement-detail.component';

describe('MasterAgreement Management Detail Component', () => {
  let comp: MasterAgreementDetailComponent;
  let fixture: ComponentFixture<MasterAgreementDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MasterAgreementDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ masterAgreement: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(MasterAgreementDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(MasterAgreementDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load masterAgreement on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.masterAgreement).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
