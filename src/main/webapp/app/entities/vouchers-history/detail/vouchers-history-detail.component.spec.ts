import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VouchersHistoryDetailComponent } from './vouchers-history-detail.component';

describe('VouchersHistory Management Detail Component', () => {
  let comp: VouchersHistoryDetailComponent;
  let fixture: ComponentFixture<VouchersHistoryDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VouchersHistoryDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ vouchersHistory: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(VouchersHistoryDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(VouchersHistoryDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load vouchersHistory on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.vouchersHistory).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
