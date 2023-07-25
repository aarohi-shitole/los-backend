import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DeclearationDetailComponent } from './declearation-detail.component';

describe('Declearation Management Detail Component', () => {
  let comp: DeclearationDetailComponent;
  let fixture: ComponentFixture<DeclearationDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DeclearationDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ declearation: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DeclearationDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DeclearationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load declearation on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.declearation).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
