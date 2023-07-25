import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IncomeDetailsDetailComponent } from './income-details-detail.component';

describe('IncomeDetails Management Detail Component', () => {
  let comp: IncomeDetailsDetailComponent;
  let fixture: ComponentFixture<IncomeDetailsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [IncomeDetailsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ incomeDetails: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(IncomeDetailsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(IncomeDetailsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load incomeDetails on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.incomeDetails).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
