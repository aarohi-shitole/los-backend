import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ILoanRepaymentDetails } from '../loan-repayment-details.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../loan-repayment-details.test-samples';

import { LoanRepaymentDetailsService, RestLoanRepaymentDetails } from './loan-repayment-details.service';

const requireRestSample: RestLoanRepaymentDetails = {
  ...sampleWithRequiredData,
  repaymentDate: sampleWithRequiredData.repaymentDate?.toJSON(),
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
};

describe('LoanRepaymentDetails Service', () => {
  let service: LoanRepaymentDetailsService;
  let httpMock: HttpTestingController;
  let expectedResult: ILoanRepaymentDetails | ILoanRepaymentDetails[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LoanRepaymentDetailsService);
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

    it('should create a LoanRepaymentDetails', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const loanRepaymentDetails = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(loanRepaymentDetails).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a LoanRepaymentDetails', () => {
      const loanRepaymentDetails = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(loanRepaymentDetails).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a LoanRepaymentDetails', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of LoanRepaymentDetails', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a LoanRepaymentDetails', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addLoanRepaymentDetailsToCollectionIfMissing', () => {
      it('should add a LoanRepaymentDetails to an empty array', () => {
        const loanRepaymentDetails: ILoanRepaymentDetails = sampleWithRequiredData;
        expectedResult = service.addLoanRepaymentDetailsToCollectionIfMissing([], loanRepaymentDetails);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(loanRepaymentDetails);
      });

      it('should not add a LoanRepaymentDetails to an array that contains it', () => {
        const loanRepaymentDetails: ILoanRepaymentDetails = sampleWithRequiredData;
        const loanRepaymentDetailsCollection: ILoanRepaymentDetails[] = [
          {
            ...loanRepaymentDetails,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addLoanRepaymentDetailsToCollectionIfMissing(loanRepaymentDetailsCollection, loanRepaymentDetails);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a LoanRepaymentDetails to an array that doesn't contain it", () => {
        const loanRepaymentDetails: ILoanRepaymentDetails = sampleWithRequiredData;
        const loanRepaymentDetailsCollection: ILoanRepaymentDetails[] = [sampleWithPartialData];
        expectedResult = service.addLoanRepaymentDetailsToCollectionIfMissing(loanRepaymentDetailsCollection, loanRepaymentDetails);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(loanRepaymentDetails);
      });

      it('should add only unique LoanRepaymentDetails to an array', () => {
        const loanRepaymentDetailsArray: ILoanRepaymentDetails[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const loanRepaymentDetailsCollection: ILoanRepaymentDetails[] = [sampleWithRequiredData];
        expectedResult = service.addLoanRepaymentDetailsToCollectionIfMissing(loanRepaymentDetailsCollection, ...loanRepaymentDetailsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const loanRepaymentDetails: ILoanRepaymentDetails = sampleWithRequiredData;
        const loanRepaymentDetails2: ILoanRepaymentDetails = sampleWithPartialData;
        expectedResult = service.addLoanRepaymentDetailsToCollectionIfMissing([], loanRepaymentDetails, loanRepaymentDetails2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(loanRepaymentDetails);
        expect(expectedResult).toContain(loanRepaymentDetails2);
      });

      it('should accept null and undefined values', () => {
        const loanRepaymentDetails: ILoanRepaymentDetails = sampleWithRequiredData;
        expectedResult = service.addLoanRepaymentDetailsToCollectionIfMissing([], null, loanRepaymentDetails, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(loanRepaymentDetails);
      });

      it('should return initial array if no LoanRepaymentDetails is added', () => {
        const loanRepaymentDetailsCollection: ILoanRepaymentDetails[] = [sampleWithRequiredData];
        expectedResult = service.addLoanRepaymentDetailsToCollectionIfMissing(loanRepaymentDetailsCollection, undefined, null);
        expect(expectedResult).toEqual(loanRepaymentDetailsCollection);
      });
    });

    describe('compareLoanRepaymentDetails', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareLoanRepaymentDetails(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareLoanRepaymentDetails(entity1, entity2);
        const compareResult2 = service.compareLoanRepaymentDetails(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareLoanRepaymentDetails(entity1, entity2);
        const compareResult2 = service.compareLoanRepaymentDetails(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareLoanRepaymentDetails(entity1, entity2);
        const compareResult2 = service.compareLoanRepaymentDetails(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
