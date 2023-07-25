import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NpaSettingDetailComponent } from './npa-setting-detail.component';

describe('NpaSetting Management Detail Component', () => {
  let comp: NpaSettingDetailComponent;
  let fixture: ComponentFixture<NpaSettingDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NpaSettingDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ npaSetting: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(NpaSettingDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(NpaSettingDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load npaSetting on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.npaSetting).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
