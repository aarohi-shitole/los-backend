import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EmployementDetailsDetailComponent } from './employement-details-detail.component';

describe('EmployementDetails Management Detail Component', () => {
  let comp: EmployementDetailsDetailComponent;
  let fixture: ComponentFixture<EmployementDetailsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EmployementDetailsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ employementDetails: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(EmployementDetailsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EmployementDetailsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load employementDetails on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.employementDetails).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
