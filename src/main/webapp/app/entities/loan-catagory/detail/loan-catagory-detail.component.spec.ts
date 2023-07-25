import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LoanCatagoryDetailComponent } from './loan-catagory-detail.component';

describe('LoanCatagory Management Detail Component', () => {
  let comp: LoanCatagoryDetailComponent;
  let fixture: ComponentFixture<LoanCatagoryDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LoanCatagoryDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ loanCatagory: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(LoanCatagoryDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(LoanCatagoryDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load loanCatagory on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.loanCatagory).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
