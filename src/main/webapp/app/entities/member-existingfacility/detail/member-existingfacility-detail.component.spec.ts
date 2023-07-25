import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MemberExistingfacilityDetailComponent } from './member-existingfacility-detail.component';

describe('MemberExistingfacility Management Detail Component', () => {
  let comp: MemberExistingfacilityDetailComponent;
  let fixture: ComponentFixture<MemberExistingfacilityDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MemberExistingfacilityDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ memberExistingfacility: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(MemberExistingfacilityDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(MemberExistingfacilityDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load memberExistingfacility on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.memberExistingfacility).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
