import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ILoanDisbursement } from '../loan-disbursement.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../loan-disbursement.test-samples';

import { LoanDisbursementService, RestLoanDisbursement } from './loan-disbursement.service';

const requireRestSample: RestLoanDisbursement = {
  ...sampleWithRequiredData,
  dtDisbDate: sampleWithRequiredData.dtDisbDate?.toJSON(),
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
};

describe('LoanDisbursement Service', () => {
  let service: LoanDisbursementService;
  let httpMock: HttpTestingController;
  let expectedResult: ILoanDisbursement | ILoanDisbursement[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LoanDisbursementService);
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

    it('should create a LoanDisbursement', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const loanDisbursement = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(loanDisbursement).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a LoanDisbursement', () => {
      const loanDisbursement = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(loanDisbursement).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a LoanDisbursement', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of LoanDisbursement', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a LoanDisbursement', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addLoanDisbursementToCollectionIfMissing', () => {
      it('should add a LoanDisbursement to an empty array', () => {
        const loanDisbursement: ILoanDisbursement = sampleWithRequiredData;
        expectedResult = service.addLoanDisbursementToCollectionIfMissing([], loanDisbursement);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(loanDisbursement);
      });

      it('should not add a LoanDisbursement to an array that contains it', () => {
        const loanDisbursement: ILoanDisbursement = sampleWithRequiredData;
        const loanDisbursementCollection: ILoanDisbursement[] = [
          {
            ...loanDisbursement,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addLoanDisbursementToCollectionIfMissing(loanDisbursementCollection, loanDisbursement);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a LoanDisbursement to an array that doesn't contain it", () => {
        const loanDisbursement: ILoanDisbursement = sampleWithRequiredData;
        const loanDisbursementCollection: ILoanDisbursement[] = [sampleWithPartialData];
        expectedResult = service.addLoanDisbursementToCollectionIfMissing(loanDisbursementCollection, loanDisbursement);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(loanDisbursement);
      });

      it('should add only unique LoanDisbursement to an array', () => {
        const loanDisbursementArray: ILoanDisbursement[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const loanDisbursementCollection: ILoanDisbursement[] = [sampleWithRequiredData];
        expectedResult = service.addLoanDisbursementToCollectionIfMissing(loanDisbursementCollection, ...loanDisbursementArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const loanDisbursement: ILoanDisbursement = sampleWithRequiredData;
        const loanDisbursement2: ILoanDisbursement = sampleWithPartialData;
        expectedResult = service.addLoanDisbursementToCollectionIfMissing([], loanDisbursement, loanDisbursement2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(loanDisbursement);
        expect(expectedResult).toContain(loanDisbursement2);
      });

      it('should accept null and undefined values', () => {
        const loanDisbursement: ILoanDisbursement = sampleWithRequiredData;
        expectedResult = service.addLoanDisbursementToCollectionIfMissing([], null, loanDisbursement, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(loanDisbursement);
      });

      it('should return initial array if no LoanDisbursement is added', () => {
        const loanDisbursementCollection: ILoanDisbursement[] = [sampleWithRequiredData];
        expectedResult = service.addLoanDisbursementToCollectionIfMissing(loanDisbursementCollection, undefined, null);
        expect(expectedResult).toEqual(loanDisbursementCollection);
      });
    });

    describe('compareLoanDisbursement', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareLoanDisbursement(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareLoanDisbursement(entity1, entity2);
        const compareResult2 = service.compareLoanDisbursement(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareLoanDisbursement(entity1, entity2);
        const compareResult2 = service.compareLoanDisbursement(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareLoanDisbursement(entity1, entity2);
        const compareResult2 = service.compareLoanDisbursement(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
