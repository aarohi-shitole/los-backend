import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ILoanCatagory } from '../loan-catagory.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../loan-catagory.test-samples';

import { LoanCatagoryService, RestLoanCatagory } from './loan-catagory.service';

const requireRestSample: RestLoanCatagory = {
  ...sampleWithRequiredData,
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
  createdOn: sampleWithRequiredData.createdOn?.toJSON(),
};

describe('LoanCatagory Service', () => {
  let service: LoanCatagoryService;
  let httpMock: HttpTestingController;
  let expectedResult: ILoanCatagory | ILoanCatagory[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LoanCatagoryService);
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

    it('should create a LoanCatagory', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const loanCatagory = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(loanCatagory).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a LoanCatagory', () => {
      const loanCatagory = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(loanCatagory).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a LoanCatagory', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of LoanCatagory', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a LoanCatagory', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addLoanCatagoryToCollectionIfMissing', () => {
      it('should add a LoanCatagory to an empty array', () => {
        const loanCatagory: ILoanCatagory = sampleWithRequiredData;
        expectedResult = service.addLoanCatagoryToCollectionIfMissing([], loanCatagory);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(loanCatagory);
      });

      it('should not add a LoanCatagory to an array that contains it', () => {
        const loanCatagory: ILoanCatagory = sampleWithRequiredData;
        const loanCatagoryCollection: ILoanCatagory[] = [
          {
            ...loanCatagory,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addLoanCatagoryToCollectionIfMissing(loanCatagoryCollection, loanCatagory);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a LoanCatagory to an array that doesn't contain it", () => {
        const loanCatagory: ILoanCatagory = sampleWithRequiredData;
        const loanCatagoryCollection: ILoanCatagory[] = [sampleWithPartialData];
        expectedResult = service.addLoanCatagoryToCollectionIfMissing(loanCatagoryCollection, loanCatagory);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(loanCatagory);
      });

      it('should add only unique LoanCatagory to an array', () => {
        const loanCatagoryArray: ILoanCatagory[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const loanCatagoryCollection: ILoanCatagory[] = [sampleWithRequiredData];
        expectedResult = service.addLoanCatagoryToCollectionIfMissing(loanCatagoryCollection, ...loanCatagoryArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const loanCatagory: ILoanCatagory = sampleWithRequiredData;
        const loanCatagory2: ILoanCatagory = sampleWithPartialData;
        expectedResult = service.addLoanCatagoryToCollectionIfMissing([], loanCatagory, loanCatagory2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(loanCatagory);
        expect(expectedResult).toContain(loanCatagory2);
      });

      it('should accept null and undefined values', () => {
        const loanCatagory: ILoanCatagory = sampleWithRequiredData;
        expectedResult = service.addLoanCatagoryToCollectionIfMissing([], null, loanCatagory, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(loanCatagory);
      });

      it('should return initial array if no LoanCatagory is added', () => {
        const loanCatagoryCollection: ILoanCatagory[] = [sampleWithRequiredData];
        expectedResult = service.addLoanCatagoryToCollectionIfMissing(loanCatagoryCollection, undefined, null);
        expect(expectedResult).toEqual(loanCatagoryCollection);
      });
    });

    describe('compareLoanCatagory', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareLoanCatagory(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareLoanCatagory(entity1, entity2);
        const compareResult2 = service.compareLoanCatagory(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareLoanCatagory(entity1, entity2);
        const compareResult2 = service.compareLoanCatagory(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareLoanCatagory(entity1, entity2);
        const compareResult2 = service.compareLoanCatagory(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
