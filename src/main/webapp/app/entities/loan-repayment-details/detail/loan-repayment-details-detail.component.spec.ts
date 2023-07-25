import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LoanRepaymentDetailsDetailComponent } from './loan-repayment-details-detail.component';

describe('LoanRepaymentDetails Management Detail Component', () => {
  let comp: LoanRepaymentDetailsDetailComponent;
  let fixture: ComponentFixture<LoanRepaymentDetailsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LoanRepaymentDetailsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ loanRepaymentDetails: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(LoanRepaymentDetailsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(LoanRepaymentDetailsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load loanRepaymentDetails on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.loanRepaymentDetails).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
