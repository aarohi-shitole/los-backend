import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAmortizationDetails } from '../amortization-details.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../amortization-details.test-samples';

import { AmortizationDetailsService, RestAmortizationDetails } from './amortization-details.service';

const requireRestSample: RestAmortizationDetails = {
  ...sampleWithRequiredData,
  installmentDate: sampleWithRequiredData.installmentDate?.toJSON(),
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
};

describe('AmortizationDetails Service', () => {
  let service: AmortizationDetailsService;
  let httpMock: HttpTestingController;
  let expectedResult: IAmortizationDetails | IAmortizationDetails[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AmortizationDetailsService);
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

    it('should create a AmortizationDetails', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const amortizationDetails = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(amortizationDetails).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AmortizationDetails', () => {
      const amortizationDetails = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(amortizationDetails).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AmortizationDetails', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AmortizationDetails', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AmortizationDetails', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAmortizationDetailsToCollectionIfMissing', () => {
      it('should add a AmortizationDetails to an empty array', () => {
        const amortizationDetails: IAmortizationDetails = sampleWithRequiredData;
        expectedResult = service.addAmortizationDetailsToCollectionIfMissing([], amortizationDetails);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(amortizationDetails);
      });

      it('should not add a AmortizationDetails to an array that contains it', () => {
        const amortizationDetails: IAmortizationDetails = sampleWithRequiredData;
        const amortizationDetailsCollection: IAmortizationDetails[] = [
          {
            ...amortizationDetails,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAmortizationDetailsToCollectionIfMissing(amortizationDetailsCollection, amortizationDetails);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AmortizationDetails to an array that doesn't contain it", () => {
        const amortizationDetails: IAmortizationDetails = sampleWithRequiredData;
        const amortizationDetailsCollection: IAmortizationDetails[] = [sampleWithPartialData];
        expectedResult = service.addAmortizationDetailsToCollectionIfMissing(amortizationDetailsCollection, amortizationDetails);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(amortizationDetails);
      });

      it('should add only unique AmortizationDetails to an array', () => {
        const amortizationDetailsArray: IAmortizationDetails[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const amortizationDetailsCollection: IAmortizationDetails[] = [sampleWithRequiredData];
        expectedResult = service.addAmortizationDetailsToCollectionIfMissing(amortizationDetailsCollection, ...amortizationDetailsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const amortizationDetails: IAmortizationDetails = sampleWithRequiredData;
        const amortizationDetails2: IAmortizationDetails = sampleWithPartialData;
        expectedResult = service.addAmortizationDetailsToCollectionIfMissing([], amortizationDetails, amortizationDetails2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(amortizationDetails);
        expect(expectedResult).toContain(amortizationDetails2);
      });

      it('should accept null and undefined values', () => {
        const amortizationDetails: IAmortizationDetails = sampleWithRequiredData;
        expectedResult = service.addAmortizationDetailsToCollectionIfMissing([], null, amortizationDetails, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(amortizationDetails);
      });

      it('should return initial array if no AmortizationDetails is added', () => {
        const amortizationDetailsCollection: IAmortizationDetails[] = [sampleWithRequiredData];
        expectedResult = service.addAmortizationDetailsToCollectionIfMissing(amortizationDetailsCollection, undefined, null);
        expect(expectedResult).toEqual(amortizationDetailsCollection);
      });
    });

    describe('compareAmortizationDetails', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAmortizationDetails(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAmortizationDetails(entity1, entity2);
        const compareResult2 = service.compareAmortizationDetails(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAmortizationDetails(entity1, entity2);
        const compareResult2 = service.compareAmortizationDetails(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAmortizationDetails(entity1, entity2);
        const compareResult2 = service.compareAmortizationDetails(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
