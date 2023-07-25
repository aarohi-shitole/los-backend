import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEmployementDetails } from '../employement-details.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../employement-details.test-samples';

import { EmployementDetailsService, RestEmployementDetails } from './employement-details.service';

const requireRestSample: RestEmployementDetails = {
  ...sampleWithRequiredData,
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
  createdOn: sampleWithRequiredData.createdOn?.toJSON(),
};

describe('EmployementDetails Service', () => {
  let service: EmployementDetailsService;
  let httpMock: HttpTestingController;
  let expectedResult: IEmployementDetails | IEmployementDetails[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EmployementDetailsService);
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

    it('should create a EmployementDetails', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const employementDetails = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(employementDetails).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EmployementDetails', () => {
      const employementDetails = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(employementDetails).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a EmployementDetails', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of EmployementDetails', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a EmployementDetails', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEmployementDetailsToCollectionIfMissing', () => {
      it('should add a EmployementDetails to an empty array', () => {
        const employementDetails: IEmployementDetails = sampleWithRequiredData;
        expectedResult = service.addEmployementDetailsToCollectionIfMissing([], employementDetails);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(employementDetails);
      });

      it('should not add a EmployementDetails to an array that contains it', () => {
        const employementDetails: IEmployementDetails = sampleWithRequiredData;
        const employementDetailsCollection: IEmployementDetails[] = [
          {
            ...employementDetails,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEmployementDetailsToCollectionIfMissing(employementDetailsCollection, employementDetails);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EmployementDetails to an array that doesn't contain it", () => {
        const employementDetails: IEmployementDetails = sampleWithRequiredData;
        const employementDetailsCollection: IEmployementDetails[] = [sampleWithPartialData];
        expectedResult = service.addEmployementDetailsToCollectionIfMissing(employementDetailsCollection, employementDetails);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(employementDetails);
      });

      it('should add only unique EmployementDetails to an array', () => {
        const employementDetailsArray: IEmployementDetails[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const employementDetailsCollection: IEmployementDetails[] = [sampleWithRequiredData];
        expectedResult = service.addEmployementDetailsToCollectionIfMissing(employementDetailsCollection, ...employementDetailsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const employementDetails: IEmployementDetails = sampleWithRequiredData;
        const employementDetails2: IEmployementDetails = sampleWithPartialData;
        expectedResult = service.addEmployementDetailsToCollectionIfMissing([], employementDetails, employementDetails2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(employementDetails);
        expect(expectedResult).toContain(employementDetails2);
      });

      it('should accept null and undefined values', () => {
        const employementDetails: IEmployementDetails = sampleWithRequiredData;
        expectedResult = service.addEmployementDetailsToCollectionIfMissing([], null, employementDetails, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(employementDetails);
      });

      it('should return initial array if no EmployementDetails is added', () => {
        const employementDetailsCollection: IEmployementDetails[] = [sampleWithRequiredData];
        expectedResult = service.addEmployementDetailsToCollectionIfMissing(employementDetailsCollection, undefined, null);
        expect(expectedResult).toEqual(employementDetailsCollection);
      });
    });

    describe('compareEmployementDetails', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEmployementDetails(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareEmployementDetails(entity1, entity2);
        const compareResult2 = service.compareEmployementDetails(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareEmployementDetails(entity1, entity2);
        const compareResult2 = service.compareEmployementDetails(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareEmployementDetails(entity1, entity2);
        const compareResult2 = service.compareEmployementDetails(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
