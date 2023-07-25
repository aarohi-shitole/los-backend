import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LedgerAccountsDetailComponent } from './ledger-accounts-detail.component';

describe('LedgerAccounts Management Detail Component', () => {
  let comp: LedgerAccountsDetailComponent;
  let fixture: ComponentFixture<LedgerAccountsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LedgerAccountsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ ledgerAccounts: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(LedgerAccountsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(LedgerAccountsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load ledgerAccounts on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.ledgerAccounts).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
