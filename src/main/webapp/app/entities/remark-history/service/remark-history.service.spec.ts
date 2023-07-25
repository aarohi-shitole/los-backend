import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IRemarkHistory } from '../remark-history.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../remark-history.test-samples';

import { RemarkHistoryService, RestRemarkHistory } from './remark-history.service';

const requireRestSample: RestRemarkHistory = {
  ...sampleWithRequiredData,
  createdOn: sampleWithRequiredData.createdOn?.toJSON(),
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
};

describe('RemarkHistory Service', () => {
  let service: RemarkHistoryService;
  let httpMock: HttpTestingController;
  let expectedResult: IRemarkHistory | IRemarkHistory[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(RemarkHistoryService);
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

    it('should create a RemarkHistory', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const remarkHistory = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(remarkHistory).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a RemarkHistory', () => {
      const remarkHistory = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(remarkHistory).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a RemarkHistory', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of RemarkHistory', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a RemarkHistory', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addRemarkHistoryToCollectionIfMissing', () => {
      it('should add a RemarkHistory to an empty array', () => {
        const remarkHistory: IRemarkHistory = sampleWithRequiredData;
        expectedResult = service.addRemarkHistoryToCollectionIfMissing([], remarkHistory);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(remarkHistory);
      });

      it('should not add a RemarkHistory to an array that contains it', () => {
        const remarkHistory: IRemarkHistory = sampleWithRequiredData;
        const remarkHistoryCollection: IRemarkHistory[] = [
          {
            ...remarkHistory,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addRemarkHistoryToCollectionIfMissing(remarkHistoryCollection, remarkHistory);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a RemarkHistory to an array that doesn't contain it", () => {
        const remarkHistory: IRemarkHistory = sampleWithRequiredData;
        const remarkHistoryCollection: IRemarkHistory[] = [sampleWithPartialData];
        expectedResult = service.addRemarkHistoryToCollectionIfMissing(remarkHistoryCollection, remarkHistory);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(remarkHistory);
      });

      it('should add only unique RemarkHistory to an array', () => {
        const remarkHistoryArray: IRemarkHistory[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const remarkHistoryCollection: IRemarkHistory[] = [sampleWithRequiredData];
        expectedResult = service.addRemarkHistoryToCollectionIfMissing(remarkHistoryCollection, ...remarkHistoryArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const remarkHistory: IRemarkHistory = sampleWithRequiredData;
        const remarkHistory2: IRemarkHistory = sampleWithPartialData;
        expectedResult = service.addRemarkHistoryToCollectionIfMissing([], remarkHistory, remarkHistory2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(remarkHistory);
        expect(expectedResult).toContain(remarkHistory2);
      });

      it('should accept null and undefined values', () => {
        const remarkHistory: IRemarkHistory = sampleWithRequiredData;
        expectedResult = service.addRemarkHistoryToCollectionIfMissing([], null, remarkHistory, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(remarkHistory);
      });

      it('should return initial array if no RemarkHistory is added', () => {
        const remarkHistoryCollection: IRemarkHistory[] = [sampleWithRequiredData];
        expectedResult = service.addRemarkHistoryToCollectionIfMissing(remarkHistoryCollection, undefined, null);
        expect(expectedResult).toEqual(remarkHistoryCollection);
      });
    });

    describe('compareRemarkHistory', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareRemarkHistory(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareRemarkHistory(entity1, entity2);
        const compareResult2 = service.compareRemarkHistory(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareRemarkHistory(entity1, entity2);
        const compareResult2 = service.compareRemarkHistory(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareRemarkHistory(entity1, entity2);
        const compareResult2 = service.compareRemarkHistory(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
