import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EpayDetailComponent } from './epay-detail.component';

describe('Epay Management Detail Component', () => {
  let comp: EpayDetailComponent;
  let fixture: ComponentFixture<EpayDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EpayDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ epay: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(EpayDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EpayDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load epay on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.epay).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
