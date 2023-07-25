import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IVouchersHistory } from '../vouchers-history.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../vouchers-history.test-samples';

import { VouchersHistoryService, RestVouchersHistory } from './vouchers-history.service';

const requireRestSample: RestVouchersHistory = {
  ...sampleWithRequiredData,
  createdOn: sampleWithRequiredData.createdOn?.toJSON(),
  voucherDate: sampleWithRequiredData.voucherDate?.toJSON(),
};

describe('VouchersHistory Service', () => {
  let service: VouchersHistoryService;
  let httpMock: HttpTestingController;
  let expectedResult: IVouchersHistory | IVouchersHistory[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(VouchersHistoryService);
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

    it('should create a VouchersHistory', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const vouchersHistory = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(vouchersHistory).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a VouchersHistory', () => {
      const vouchersHistory = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(vouchersHistory).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a VouchersHistory', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of VouchersHistory', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a VouchersHistory', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addVouchersHistoryToCollectionIfMissing', () => {
      it('should add a VouchersHistory to an empty array', () => {
        const vouchersHistory: IVouchersHistory = sampleWithRequiredData;
        expectedResult = service.addVouchersHistoryToCollectionIfMissing([], vouchersHistory);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(vouchersHistory);
      });

      it('should not add a VouchersHistory to an array that contains it', () => {
        const vouchersHistory: IVouchersHistory = sampleWithRequiredData;
        const vouchersHistoryCollection: IVouchersHistory[] = [
          {
            ...vouchersHistory,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addVouchersHistoryToCollectionIfMissing(vouchersHistoryCollection, vouchersHistory);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a VouchersHistory to an array that doesn't contain it", () => {
        const vouchersHistory: IVouchersHistory = sampleWithRequiredData;
        const vouchersHistoryCollection: IVouchersHistory[] = [sampleWithPartialData];
        expectedResult = service.addVouchersHistoryToCollectionIfMissing(vouchersHistoryCollection, vouchersHistory);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(vouchersHistory);
      });

      it('should add only unique VouchersHistory to an array', () => {
        const vouchersHistoryArray: IVouchersHistory[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const vouchersHistoryCollection: IVouchersHistory[] = [sampleWithRequiredData];
        expectedResult = service.addVouchersHistoryToCollectionIfMissing(vouchersHistoryCollection, ...vouchersHistoryArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const vouchersHistory: IVouchersHistory = sampleWithRequiredData;
        const vouchersHistory2: IVouchersHistory = sampleWithPartialData;
        expectedResult = service.addVouchersHistoryToCollectionIfMissing([], vouchersHistory, vouchersHistory2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(vouchersHistory);
        expect(expectedResult).toContain(vouchersHistory2);
      });

      it('should accept null and undefined values', () => {
        const vouchersHistory: IVouchersHistory = sampleWithRequiredData;
        expectedResult = service.addVouchersHistoryToCollectionIfMissing([], null, vouchersHistory, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(vouchersHistory);
      });

      it('should return initial array if no VouchersHistory is added', () => {
        const vouchersHistoryCollection: IVouchersHistory[] = [sampleWithRequiredData];
        expectedResult = service.addVouchersHistoryToCollectionIfMissing(vouchersHistoryCollection, undefined, null);
        expect(expectedResult).toEqual(vouchersHistoryCollection);
      });
    });

    describe('compareVouchersHistory', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareVouchersHistory(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareVouchersHistory(entity1, entity2);
        const compareResult2 = service.compareVouchersHistory(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareVouchersHistory(entity1, entity2);
        const compareResult2 = service.compareVouchersHistory(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareVouchersHistory(entity1, entity2);
        const compareResult2 = service.compareVouchersHistory(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
