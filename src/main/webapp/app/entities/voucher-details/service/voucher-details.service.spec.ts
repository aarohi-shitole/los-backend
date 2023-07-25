import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IVoucherDetails } from '../voucher-details.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../voucher-details.test-samples';

import { VoucherDetailsService, RestVoucherDetails } from './voucher-details.service';

const requireRestSample: RestVoucherDetails = {
  ...sampleWithRequiredData,
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
};

describe('VoucherDetails Service', () => {
  let service: VoucherDetailsService;
  let httpMock: HttpTestingController;
  let expectedResult: IVoucherDetails | IVoucherDetails[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(VoucherDetailsService);
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

    it('should create a VoucherDetails', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const voucherDetails = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(voucherDetails).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a VoucherDetails', () => {
      const voucherDetails = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(voucherDetails).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a VoucherDetails', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of VoucherDetails', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a VoucherDetails', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addVoucherDetailsToCollectionIfMissing', () => {
      it('should add a VoucherDetails to an empty array', () => {
        const voucherDetails: IVoucherDetails = sampleWithRequiredData;
        expectedResult = service.addVoucherDetailsToCollectionIfMissing([], voucherDetails);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(voucherDetails);
      });

      it('should not add a VoucherDetails to an array that contains it', () => {
        const voucherDetails: IVoucherDetails = sampleWithRequiredData;
        const voucherDetailsCollection: IVoucherDetails[] = [
          {
            ...voucherDetails,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addVoucherDetailsToCollectionIfMissing(voucherDetailsCollection, voucherDetails);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a VoucherDetails to an array that doesn't contain it", () => {
        const voucherDetails: IVoucherDetails = sampleWithRequiredData;
        const voucherDetailsCollection: IVoucherDetails[] = [sampleWithPartialData];
        expectedResult = service.addVoucherDetailsToCollectionIfMissing(voucherDetailsCollection, voucherDetails);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(voucherDetails);
      });

      it('should add only unique VoucherDetails to an array', () => {
        const voucherDetailsArray: IVoucherDetails[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const voucherDetailsCollection: IVoucherDetails[] = [sampleWithRequiredData];
        expectedResult = service.addVoucherDetailsToCollectionIfMissing(voucherDetailsCollection, ...voucherDetailsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const voucherDetails: IVoucherDetails = sampleWithRequiredData;
        const voucherDetails2: IVoucherDetails = sampleWithPartialData;
        expectedResult = service.addVoucherDetailsToCollectionIfMissing([], voucherDetails, voucherDetails2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(voucherDetails);
        expect(expectedResult).toContain(voucherDetails2);
      });

      it('should accept null and undefined values', () => {
        const voucherDetails: IVoucherDetails = sampleWithRequiredData;
        expectedResult = service.addVoucherDetailsToCollectionIfMissing([], null, voucherDetails, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(voucherDetails);
      });

      it('should return initial array if no VoucherDetails is added', () => {
        const voucherDetailsCollection: IVoucherDetails[] = [sampleWithRequiredData];
        expectedResult = service.addVoucherDetailsToCollectionIfMissing(voucherDetailsCollection, undefined, null);
        expect(expectedResult).toEqual(voucherDetailsCollection);
      });
    });

    describe('compareVoucherDetails', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareVoucherDetails(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareVoucherDetails(entity1, entity2);
        const compareResult2 = service.compareVoucherDetails(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareVoucherDetails(entity1, entity2);
        const compareResult2 = service.compareVoucherDetails(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareVoucherDetails(entity1, entity2);
        const compareResult2 = service.compareVoucherDetails(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
