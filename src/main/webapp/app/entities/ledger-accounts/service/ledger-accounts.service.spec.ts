import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ILedgerAccounts } from '../ledger-accounts.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../ledger-accounts.test-samples';

import { LedgerAccountsService, RestLedgerAccounts } from './ledger-accounts.service';

const requireRestSample: RestLedgerAccounts = {
  ...sampleWithRequiredData,
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
  createdOn: sampleWithRequiredData.createdOn?.toJSON(),
};

describe('LedgerAccounts Service', () => {
  let service: LedgerAccountsService;
  let httpMock: HttpTestingController;
  let expectedResult: ILedgerAccounts | ILedgerAccounts[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LedgerAccountsService);
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

    it('should create a LedgerAccounts', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const ledgerAccounts = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(ledgerAccounts).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a LedgerAccounts', () => {
      const ledgerAccounts = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(ledgerAccounts).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a LedgerAccounts', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of LedgerAccounts', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a LedgerAccounts', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addLedgerAccountsToCollectionIfMissing', () => {
      it('should add a LedgerAccounts to an empty array', () => {
        const ledgerAccounts: ILedgerAccounts = sampleWithRequiredData;
        expectedResult = service.addLedgerAccountsToCollectionIfMissing([], ledgerAccounts);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ledgerAccounts);
      });

      it('should not add a LedgerAccounts to an array that contains it', () => {
        const ledgerAccounts: ILedgerAccounts = sampleWithRequiredData;
        const ledgerAccountsCollection: ILedgerAccounts[] = [
          {
            ...ledgerAccounts,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addLedgerAccountsToCollectionIfMissing(ledgerAccountsCollection, ledgerAccounts);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a LedgerAccounts to an array that doesn't contain it", () => {
        const ledgerAccounts: ILedgerAccounts = sampleWithRequiredData;
        const ledgerAccountsCollection: ILedgerAccounts[] = [sampleWithPartialData];
        expectedResult = service.addLedgerAccountsToCollectionIfMissing(ledgerAccountsCollection, ledgerAccounts);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ledgerAccounts);
      });

      it('should add only unique LedgerAccounts to an array', () => {
        const ledgerAccountsArray: ILedgerAccounts[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const ledgerAccountsCollection: ILedgerAccounts[] = [sampleWithRequiredData];
        expectedResult = service.addLedgerAccountsToCollectionIfMissing(ledgerAccountsCollection, ...ledgerAccountsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const ledgerAccounts: ILedgerAccounts = sampleWithRequiredData;
        const ledgerAccounts2: ILedgerAccounts = sampleWithPartialData;
        expectedResult = service.addLedgerAccountsToCollectionIfMissing([], ledgerAccounts, ledgerAccounts2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ledgerAccounts);
        expect(expectedResult).toContain(ledgerAccounts2);
      });

      it('should accept null and undefined values', () => {
        const ledgerAccounts: ILedgerAccounts = sampleWithRequiredData;
        expectedResult = service.addLedgerAccountsToCollectionIfMissing([], null, ledgerAccounts, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ledgerAccounts);
      });

      it('should return initial array if no LedgerAccounts is added', () => {
        const ledgerAccountsCollection: ILedgerAccounts[] = [sampleWithRequiredData];
        expectedResult = service.addLedgerAccountsToCollectionIfMissing(ledgerAccountsCollection, undefined, null);
        expect(expectedResult).toEqual(ledgerAccountsCollection);
      });
    });

    describe('compareLedgerAccounts', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareLedgerAccounts(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareLedgerAccounts(entity1, entity2);
        const compareResult2 = service.compareLedgerAccounts(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareLedgerAccounts(entity1, entity2);
        const compareResult2 = service.compareLedgerAccounts(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareLedgerAccounts(entity1, entity2);
        const compareResult2 = service.compareLedgerAccounts(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
