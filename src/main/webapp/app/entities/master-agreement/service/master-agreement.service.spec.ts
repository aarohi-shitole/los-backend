import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IMasterAgreement } from '../master-agreement.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../master-agreement.test-samples';

import { MasterAgreementService, RestMasterAgreement } from './master-agreement.service';

const requireRestSample: RestMasterAgreement = {
  ...sampleWithRequiredData,
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
};

describe('MasterAgreement Service', () => {
  let service: MasterAgreementService;
  let httpMock: HttpTestingController;
  let expectedResult: IMasterAgreement | IMasterAgreement[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MasterAgreementService);
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

    it('should create a MasterAgreement', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const masterAgreement = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(masterAgreement).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MasterAgreement', () => {
      const masterAgreement = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(masterAgreement).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a MasterAgreement', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MasterAgreement', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a MasterAgreement', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addMasterAgreementToCollectionIfMissing', () => {
      it('should add a MasterAgreement to an empty array', () => {
        const masterAgreement: IMasterAgreement = sampleWithRequiredData;
        expectedResult = service.addMasterAgreementToCollectionIfMissing([], masterAgreement);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(masterAgreement);
      });

      it('should not add a MasterAgreement to an array that contains it', () => {
        const masterAgreement: IMasterAgreement = sampleWithRequiredData;
        const masterAgreementCollection: IMasterAgreement[] = [
          {
            ...masterAgreement,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMasterAgreementToCollectionIfMissing(masterAgreementCollection, masterAgreement);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MasterAgreement to an array that doesn't contain it", () => {
        const masterAgreement: IMasterAgreement = sampleWithRequiredData;
        const masterAgreementCollection: IMasterAgreement[] = [sampleWithPartialData];
        expectedResult = service.addMasterAgreementToCollectionIfMissing(masterAgreementCollection, masterAgreement);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(masterAgreement);
      });

      it('should add only unique MasterAgreement to an array', () => {
        const masterAgreementArray: IMasterAgreement[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const masterAgreementCollection: IMasterAgreement[] = [sampleWithRequiredData];
        expectedResult = service.addMasterAgreementToCollectionIfMissing(masterAgreementCollection, ...masterAgreementArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const masterAgreement: IMasterAgreement = sampleWithRequiredData;
        const masterAgreement2: IMasterAgreement = sampleWithPartialData;
        expectedResult = service.addMasterAgreementToCollectionIfMissing([], masterAgreement, masterAgreement2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(masterAgreement);
        expect(expectedResult).toContain(masterAgreement2);
      });

      it('should accept null and undefined values', () => {
        const masterAgreement: IMasterAgreement = sampleWithRequiredData;
        expectedResult = service.addMasterAgreementToCollectionIfMissing([], null, masterAgreement, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(masterAgreement);
      });

      it('should return initial array if no MasterAgreement is added', () => {
        const masterAgreementCollection: IMasterAgreement[] = [sampleWithRequiredData];
        expectedResult = service.addMasterAgreementToCollectionIfMissing(masterAgreementCollection, undefined, null);
        expect(expectedResult).toEqual(masterAgreementCollection);
      });
    });

    describe('compareMasterAgreement', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMasterAgreement(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareMasterAgreement(entity1, entity2);
        const compareResult2 = service.compareMasterAgreement(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareMasterAgreement(entity1, entity2);
        const compareResult2 = service.compareMasterAgreement(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareMasterAgreement(entity1, entity2);
        const compareResult2 = service.compareMasterAgreement(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
