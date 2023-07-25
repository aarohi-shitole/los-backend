import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EnquiryDetailsDetailComponent } from './enquiry-details-detail.component';

describe('EnquiryDetails Management Detail Component', () => {
  let comp: EnquiryDetailsDetailComponent;
  let fixture: ComponentFixture<EnquiryDetailsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EnquiryDetailsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ enquiryDetails: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(EnquiryDetailsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EnquiryDetailsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load enquiryDetails on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.enquiryDetails).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
