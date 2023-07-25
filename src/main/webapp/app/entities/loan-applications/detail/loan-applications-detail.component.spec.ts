import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LoanApplicationsDetailComponent } from './loan-applications-detail.component';

describe('LoanApplications Management Detail Component', () => {
  let comp: LoanApplicationsDetailComponent;
  let fixture: ComponentFixture<LoanApplicationsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LoanApplicationsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ loanApplications: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(LoanApplicationsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(LoanApplicationsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load loanApplications on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.loanApplications).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
