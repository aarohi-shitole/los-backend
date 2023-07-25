import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { InterestConfigDetailComponent } from './interest-config-detail.component';

describe('InterestConfig Management Detail Component', () => {
  let comp: InterestConfigDetailComponent;
  let fixture: ComponentFixture<InterestConfigDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InterestConfigDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ interestConfig: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(InterestConfigDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(InterestConfigDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load interestConfig on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.interestConfig).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
