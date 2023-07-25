import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OrgPrerequisiteDocDetailComponent } from './org-prerequisite-doc-detail.component';

describe('OrgPrerequisiteDoc Management Detail Component', () => {
  let comp: OrgPrerequisiteDocDetailComponent;
  let fixture: ComponentFixture<OrgPrerequisiteDocDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OrgPrerequisiteDocDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ orgPrerequisiteDoc: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(OrgPrerequisiteDocDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(OrgPrerequisiteDocDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load orgPrerequisiteDoc on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.orgPrerequisiteDoc).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
