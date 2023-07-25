import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ILoanApplications } from '../loan-applications.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../loan-applications.test-samples';

import { LoanApplicationsService, RestLoanApplications } from './loan-applications.service';

const requireRestSample: RestLoanApplications = {
  ...sampleWithRequiredData,
  mortgageDate: sampleWithRequiredData.mortgageDate?.toJSON(),
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
};

describe('LoanApplications Service', () => {
  let service: LoanApplicationsService;
  let httpMock: HttpTestingController;
  let expectedResult: ILoanApplications | ILoanApplications[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LoanApplicationsService);
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

    it('should create a LoanApplications', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const loanApplications = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(loanApplications).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a LoanApplications', () => {
      const loanApplications = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(loanApplications).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a LoanApplications', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of LoanApplications', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a LoanApplications', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addLoanApplicationsToCollectionIfMissing', () => {
      it('should add a LoanApplications to an empty array', () => {
        const loanApplications: ILoanApplications = sampleWithRequiredData;
        expectedResult = service.addLoanApplicationsToCollectionIfMissing([], loanApplications);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(loanApplications);
      });

      it('should not add a LoanApplications to an array that contains it', () => {
        const loanApplications: ILoanApplications = sampleWithRequiredData;
        const loanApplicationsCollection: ILoanApplications[] = [
          {
            ...loanApplications,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addLoanApplicationsToCollectionIfMissing(loanApplicationsCollection, loanApplications);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a LoanApplications to an array that doesn't contain it", () => {
        const loanApplications: ILoanApplications = sampleWithRequiredData;
        const loanApplicationsCollection: ILoanApplications[] = [sampleWithPartialData];
        expectedResult = service.addLoanApplicationsToCollectionIfMissing(loanApplicationsCollection, loanApplications);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(loanApplications);
      });

      it('should add only unique LoanApplications to an array', () => {
        const loanApplicationsArray: ILoanApplications[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const loanApplicationsCollection: ILoanApplications[] = [sampleWithRequiredData];
        expectedResult = service.addLoanApplicationsToCollectionIfMissing(loanApplicationsCollection, ...loanApplicationsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const loanApplications: ILoanApplications = sampleWithRequiredData;
        const loanApplications2: ILoanApplications = sampleWithPartialData;
        expectedResult = service.addLoanApplicationsToCollectionIfMissing([], loanApplications, loanApplications2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(loanApplications);
        expect(expectedResult).toContain(loanApplications2);
      });

      it('should accept null and undefined values', () => {
        const loanApplications: ILoanApplications = sampleWithRequiredData;
        expectedResult = service.addLoanApplicationsToCollectionIfMissing([], null, loanApplications, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(loanApplications);
      });

      it('should return initial array if no LoanApplications is added', () => {
        const loanApplicationsCollection: ILoanApplications[] = [sampleWithRequiredData];
        expectedResult = service.addLoanApplicationsToCollectionIfMissing(loanApplicationsCollection, undefined, null);
        expect(expectedResult).toEqual(loanApplicationsCollection);
      });
    });

    describe('compareLoanApplications', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareLoanApplications(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareLoanApplications(entity1, entity2);
        const compareResult2 = service.compareLoanApplications(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareLoanApplications(entity1, entity2);
        const compareResult2 = service.compareLoanApplications(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareLoanApplications(entity1, entity2);
        const compareResult2 = service.compareLoanApplications(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
