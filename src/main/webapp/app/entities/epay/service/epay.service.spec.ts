import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IEpay } from '../epay.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../epay.test-samples';

import { EpayService, RestEpay } from './epay.service';

const requireRestSample: RestEpay = {
  ...sampleWithRequiredData,
  dtFromDate: sampleWithRequiredData.dtFromDate?.format(DATE_FORMAT),
  dtToDate: sampleWithRequiredData.dtToDate?.format(DATE_FORMAT),
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
};

describe('Epay Service', () => {
  let service: EpayService;
  let httpMock: HttpTestingController;
  let expectedResult: IEpay | IEpay[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EpayService);
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

    it('should create a Epay', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const epay = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(epay).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Epay', () => {
      const epay = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(epay).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Epay', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Epay', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Epay', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEpayToCollectionIfMissing', () => {
      it('should add a Epay to an empty array', () => {
        const epay: IEpay = sampleWithRequiredData;
        expectedResult = service.addEpayToCollectionIfMissing([], epay);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(epay);
      });

      it('should not add a Epay to an array that contains it', () => {
        const epay: IEpay = sampleWithRequiredData;
        const epayCollection: IEpay[] = [
          {
            ...epay,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEpayToCollectionIfMissing(epayCollection, epay);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Epay to an array that doesn't contain it", () => {
        const epay: IEpay = sampleWithRequiredData;
        const epayCollection: IEpay[] = [sampleWithPartialData];
        expectedResult = service.addEpayToCollectionIfMissing(epayCollection, epay);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(epay);
      });

      it('should add only unique Epay to an array', () => {
        const epayArray: IEpay[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const epayCollection: IEpay[] = [sampleWithRequiredData];
        expectedResult = service.addEpayToCollectionIfMissing(epayCollection, ...epayArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const epay: IEpay = sampleWithRequiredData;
        const epay2: IEpay = sampleWithPartialData;
        expectedResult = service.addEpayToCollectionIfMissing([], epay, epay2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(epay);
        expect(expectedResult).toContain(epay2);
      });

      it('should accept null and undefined values', () => {
        const epay: IEpay = sampleWithRequiredData;
        expectedResult = service.addEpayToCollectionIfMissing([], null, epay, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(epay);
      });

      it('should return initial array if no Epay is added', () => {
        const epayCollection: IEpay[] = [sampleWithRequiredData];
        expectedResult = service.addEpayToCollectionIfMissing(epayCollection, undefined, null);
        expect(expectedResult).toEqual(epayCollection);
      });
    });

    describe('compareEpay', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEpay(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareEpay(entity1, entity2);
        const compareResult2 = service.compareEpay(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareEpay(entity1, entity2);
        const compareResult2 = service.compareEpay(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareEpay(entity1, entity2);
        const compareResult2 = service.compareEpay(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
