import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RemarkHistoryDetailComponent } from './remark-history-detail.component';

describe('RemarkHistory Management Detail Component', () => {
  let comp: RemarkHistoryDetailComponent;
  let fixture: ComponentFixture<RemarkHistoryDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RemarkHistoryDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ remarkHistory: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(RemarkHistoryDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(RemarkHistoryDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load remarkHistory on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.remarkHistory).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
