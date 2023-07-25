import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VouchersDetailComponent } from './vouchers-detail.component';

describe('Vouchers Management Detail Component', () => {
  let comp: VouchersDetailComponent;
  let fixture: ComponentFixture<VouchersDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VouchersDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ vouchers: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(VouchersDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(VouchersDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load vouchers on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.vouchers).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
