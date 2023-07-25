import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IIncomeDetails } from '../income-details.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../income-details.test-samples';

import { IncomeDetailsService, RestIncomeDetails } from './income-details.service';

const requireRestSample: RestIncomeDetails = {
  ...sampleWithRequiredData,
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
  createdOn: sampleWithRequiredData.createdOn?.toJSON(),
};

describe('IncomeDetails Service', () => {
  let service: IncomeDetailsService;
  let httpMock: HttpTestingController;
  let expectedResult: IIncomeDetails | IIncomeDetails[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(IncomeDetailsService);
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

    it('should create a IncomeDetails', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const incomeDetails = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(incomeDetails).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a IncomeDetails', () => {
      const incomeDetails = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(incomeDetails).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a IncomeDetails', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of IncomeDetails', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a IncomeDetails', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addIncomeDetailsToCollectionIfMissing', () => {
      it('should add a IncomeDetails to an empty array', () => {
        const incomeDetails: IIncomeDetails = sampleWithRequiredData;
        expectedResult = service.addIncomeDetailsToCollectionIfMissing([], incomeDetails);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(incomeDetails);
      });

      it('should not add a IncomeDetails to an array that contains it', () => {
        const incomeDetails: IIncomeDetails = sampleWithRequiredData;
        const incomeDetailsCollection: IIncomeDetails[] = [
          {
            ...incomeDetails,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addIncomeDetailsToCollectionIfMissing(incomeDetailsCollection, incomeDetails);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a IncomeDetails to an array that doesn't contain it", () => {
        const incomeDetails: IIncomeDetails = sampleWithRequiredData;
        const incomeDetailsCollection: IIncomeDetails[] = [sampleWithPartialData];
        expectedResult = service.addIncomeDetailsToCollectionIfMissing(incomeDetailsCollection, incomeDetails);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(incomeDetails);
      });

      it('should add only unique IncomeDetails to an array', () => {
        const incomeDetailsArray: IIncomeDetails[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const incomeDetailsCollection: IIncomeDetails[] = [sampleWithRequiredData];
        expectedResult = service.addIncomeDetailsToCollectionIfMissing(incomeDetailsCollection, ...incomeDetailsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const incomeDetails: IIncomeDetails = sampleWithRequiredData;
        const incomeDetails2: IIncomeDetails = sampleWithPartialData;
        expectedResult = service.addIncomeDetailsToCollectionIfMissing([], incomeDetails, incomeDetails2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(incomeDetails);
        expect(expectedResult).toContain(incomeDetails2);
      });

      it('should accept null and undefined values', () => {
        const incomeDetails: IIncomeDetails = sampleWithRequiredData;
        expectedResult = service.addIncomeDetailsToCollectionIfMissing([], null, incomeDetails, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(incomeDetails);
      });

      it('should return initial array if no IncomeDetails is added', () => {
        const incomeDetailsCollection: IIncomeDetails[] = [sampleWithRequiredData];
        expectedResult = service.addIncomeDetailsToCollectionIfMissing(incomeDetailsCollection, undefined, null);
        expect(expectedResult).toEqual(incomeDetailsCollection);
      });
    });

    describe('compareIncomeDetails', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareIncomeDetails(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareIncomeDetails(entity1, entity2);
        const compareResult2 = service.compareIncomeDetails(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareIncomeDetails(entity1, entity2);
        const compareResult2 = service.compareIncomeDetails(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareIncomeDetails(entity1, entity2);
        const compareResult2 = service.compareIncomeDetails(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
