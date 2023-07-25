import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEnquiryDetails } from '../enquiry-details.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../enquiry-details.test-samples';

import { EnquiryDetailsService, RestEnquiryDetails } from './enquiry-details.service';

const requireRestSample: RestEnquiryDetails = {
  ...sampleWithRequiredData,
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
};

describe('EnquiryDetails Service', () => {
  let service: EnquiryDetailsService;
  let httpMock: HttpTestingController;
  let expectedResult: IEnquiryDetails | IEnquiryDetails[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EnquiryDetailsService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a EnquiryDetails', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const enquiryDetails = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(enquiryDetails).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EnquiryDetails', () => {
      const enquiryDetails = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(enquiryDetails).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a EnquiryDetails', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of EnquiryDetails', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a EnquiryDetails', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEnquiryDetailsToCollectionIfMissing', () => {
      it('should add a EnquiryDetails to an empty array', () => {
        const enquiryDetails: IEnquiryDetails = sampleWithRequiredData;
        expectedResult = service.addEnquiryDetailsToCollectionIfMissing([], enquiryDetails);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(enquiryDetails);
      });

      it('should not add a EnquiryDetails to an array that contains it', () => {
        const enquiryDetails: IEnquiryDetails = sampleWithRequiredData;
        const enquiryDetailsCollection: IEnquiryDetails[] = [
          {
            ...enquiryDetails,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEnquiryDetailsToCollectionIfMissing(enquiryDetailsCollection, enquiryDetails);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EnquiryDetails to an array that doesn't contain it", () => {
        const enquiryDetails: IEnquiryDetails = sampleWithRequiredData;
        const enquiryDetailsCollection: IEnquiryDetails[] = [sampleWithPartialData];
        expectedResult = service.addEnquiryDetailsToCollectionIfMissing(enquiryDetailsCollection, enquiryDetails);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(enquiryDetails);
      });

      it('should add only unique EnquiryDetails to an array', () => {
        const enquiryDetailsArray: IEnquiryDetails[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const enquiryDetailsCollection: IEnquiryDetails[] = [sampleWithRequiredData];
        expectedResult = service.addEnquiryDetailsToCollectionIfMissing(enquiryDetailsCollection, ...enquiryDetailsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const enquiryDetails: IEnquiryDetails = sampleWithRequiredData;
        const enquiryDetails2: IEnquiryDetails = sampleWithPartialData;
        expectedResult = service.addEnquiryDetailsToCollectionIfMissing([], enquiryDetails, enquiryDetails2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(enquiryDetails);
        expect(expectedResult).toContain(enquiryDetails2);
      });

      it('should accept null and undefined values', () => {
        const enquiryDetails: IEnquiryDetails = sampleWithRequiredData;
        expectedResult = service.addEnquiryDetailsToCollectionIfMissing([], null, enquiryDetails, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(enquiryDetails);
      });

      it('should return initial array if no EnquiryDetails is added', () => {
        const enquiryDetailsCollection: IEnquiryDetails[] = [sampleWithRequiredData];
        expectedResult = service.addEnquiryDetailsToCollectionIfMissing(enquiryDetailsCollection, undefined, null);
        expect(expectedResult).toEqual(enquiryDetailsCollection);
      });
    });

    describe('compareEnquiryDetails', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEnquiryDetails(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareEnquiryDetails(entity1, entity2);
        const compareResult2 = service.compareEnquiryDetails(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareEnquiryDetails(entity1, entity2);
        const compareResult2 = service.compareEnquiryDetails(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareEnquiryDetails(entity1, entity2);
        const compareResult2 = service.compareEnquiryDetails(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
