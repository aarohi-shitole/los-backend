import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SchemesDetailsDetailComponent } from './schemes-details-detail.component';

describe('SchemesDetails Management Detail Component', () => {
  let comp: SchemesDetailsDetailComponent;
  let fixture: ComponentFixture<SchemesDetailsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SchemesDetailsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ schemesDetails: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SchemesDetailsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SchemesDetailsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load schemesDetails on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.schemesDetails).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
