import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GuarantorDetailComponent } from './guarantor-detail.component';

describe('Guarantor Management Detail Component', () => {
  let comp: GuarantorDetailComponent;
  let fixture: ComponentFixture<GuarantorDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [GuarantorDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ guarantor: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(GuarantorDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(GuarantorDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load guarantor on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.guarantor).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
