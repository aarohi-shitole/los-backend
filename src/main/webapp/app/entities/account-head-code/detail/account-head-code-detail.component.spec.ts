import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AccountHeadCodeDetailComponent } from './account-head-code-detail.component';

describe('AccountHeadCode Management Detail Component', () => {
  let comp: AccountHeadCodeDetailComponent;
  let fixture: ComponentFixture<AccountHeadCodeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AccountHeadCodeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ accountHeadCode: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AccountHeadCodeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AccountHeadCodeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load accountHeadCode on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.accountHeadCode).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
