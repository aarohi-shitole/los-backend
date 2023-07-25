import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MemberAssetsDetailComponent } from './member-assets-detail.component';

describe('MemberAssets Management Detail Component', () => {
  let comp: MemberAssetsDetailComponent;
  let fixture: ComponentFixture<MemberAssetsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MemberAssetsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ memberAssets: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(MemberAssetsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(MemberAssetsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load memberAssets on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.memberAssets).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
