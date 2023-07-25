import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AmortizationDetailsDetailComponent } from './amortization-details-detail.component';

describe('AmortizationDetails Management Detail Component', () => {
  let comp: AmortizationDetailsDetailComponent;
  let fixture: ComponentFixture<AmortizationDetailsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AmortizationDetailsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ amortizationDetails: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AmortizationDetailsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AmortizationDetailsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load amortizationDetails on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.amortizationDetails).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
