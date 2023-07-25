import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MemberLimitDetailComponent } from './member-limit-detail.component';

describe('MemberLimit Management Detail Component', () => {
  let comp: MemberLimitDetailComponent;
  let fixture: ComponentFixture<MemberLimitDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MemberLimitDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ memberLimit: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(MemberLimitDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(MemberLimitDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load memberLimit on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.memberLimit).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
