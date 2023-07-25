import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISchemesDetails } from '../schemes-details.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../schemes-details.test-samples';

import { SchemesDetailsService, RestSchemesDetails } from './schemes-details.service';

const requireRestSample: RestSchemesDetails = {
  ...sampleWithRequiredData,
  lastDayOfScheam: sampleWithRequiredData.lastDayOfScheam?.toJSON(),
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
  createdOn: sampleWithRequiredData.createdOn?.toJSON(),
};

describe('SchemesDetails Service', () => {
  let service: SchemesDetailsService;
  let httpMock: HttpTestingController;
  let expectedResult: ISchemesDetails | ISchemesDetails[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SchemesDetailsService);
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

    it('should create a SchemesDetails', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const schemesDetails = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(schemesDetails).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SchemesDetails', () => {
      const schemesDetails = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(schemesDetails).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SchemesDetails', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SchemesDetails', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SchemesDetails', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSchemesDetailsToCollectionIfMissing', () => {
      it('should add a SchemesDetails to an empty array', () => {
        const schemesDetails: ISchemesDetails = sampleWithRequiredData;
        expectedResult = service.addSchemesDetailsToCollectionIfMissing([], schemesDetails);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(schemesDetails);
      });

      it('should not add a SchemesDetails to an array that contains it', () => {
        const schemesDetails: ISchemesDetails = sampleWithRequiredData;
        const schemesDetailsCollection: ISchemesDetails[] = [
          {
            ...schemesDetails,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSchemesDetailsToCollectionIfMissing(schemesDetailsCollection, schemesDetails);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SchemesDetails to an array that doesn't contain it", () => {
        const schemesDetails: ISchemesDetails = sampleWithRequiredData;
        const schemesDetailsCollection: ISchemesDetails[] = [sampleWithPartialData];
        expectedResult = service.addSchemesDetailsToCollectionIfMissing(schemesDetailsCollection, schemesDetails);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(schemesDetails);
      });

      it('should add only unique SchemesDetails to an array', () => {
        const schemesDetailsArray: ISchemesDetails[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const schemesDetailsCollection: ISchemesDetails[] = [sampleWithRequiredData];
        expectedResult = service.addSchemesDetailsToCollectionIfMissing(schemesDetailsCollection, ...schemesDetailsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const schemesDetails: ISchemesDetails = sampleWithRequiredData;
        const schemesDetails2: ISchemesDetails = sampleWithPartialData;
        expectedResult = service.addSchemesDetailsToCollectionIfMissing([], schemesDetails, schemesDetails2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(schemesDetails);
        expect(expectedResult).toContain(schemesDetails2);
      });

      it('should accept null and undefined values', () => {
        const schemesDetails: ISchemesDetails = sampleWithRequiredData;
        expectedResult = service.addSchemesDetailsToCollectionIfMissing([], null, schemesDetails, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(schemesDetails);
      });

      it('should return initial array if no SchemesDetails is added', () => {
        const schemesDetailsCollection: ISchemesDetails[] = [sampleWithRequiredData];
        expectedResult = service.addSchemesDetailsToCollectionIfMissing(schemesDetailsCollection, undefined, null);
        expect(expectedResult).toEqual(schemesDetailsCollection);
      });
    });

    describe('compareSchemesDetails', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSchemesDetails(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSchemesDetails(entity1, entity2);
        const compareResult2 = service.compareSchemesDetails(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSchemesDetails(entity1, entity2);
        const compareResult2 = service.compareSchemesDetails(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSchemesDetails(entity1, entity2);
        const compareResult2 = service.compareSchemesDetails(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
