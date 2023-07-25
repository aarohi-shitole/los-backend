import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VoucherDetailsDetailComponent } from './voucher-details-detail.component';

describe('VoucherDetails Management Detail Component', () => {
  let comp: VoucherDetailsDetailComponent;
  let fixture: ComponentFixture<VoucherDetailsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VoucherDetailsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ voucherDetails: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(VoucherDetailsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(VoucherDetailsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load voucherDetails on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.voucherDetails).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
