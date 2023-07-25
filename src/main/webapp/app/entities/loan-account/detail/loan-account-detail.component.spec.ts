import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LoanAccountDetailComponent } from './loan-account-detail.component';

describe('LoanAccount Management Detail Component', () => {
  let comp: LoanAccountDetailComponent;
  let fixture: ComponentFixture<LoanAccountDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LoanAccountDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ loanAccount: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(LoanAccountDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(LoanAccountDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load loanAccount on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.loanAccount).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
