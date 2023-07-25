import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LoanDisbursementDetailComponent } from './loan-disbursement-detail.component';

describe('LoanDisbursement Management Detail Component', () => {
  let comp: LoanDisbursementDetailComponent;
  let fixture: ComponentFixture<LoanDisbursementDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LoanDisbursementDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ loanDisbursement: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(LoanDisbursementDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(LoanDisbursementDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load loanDisbursement on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.loanDisbursement).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
