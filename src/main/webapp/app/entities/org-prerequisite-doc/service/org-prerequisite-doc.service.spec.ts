import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IOrgPrerequisiteDoc } from '../org-prerequisite-doc.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../org-prerequisite-doc.test-samples';

import { OrgPrerequisiteDocService, RestOrgPrerequisiteDoc } from './org-prerequisite-doc.service';

const requireRestSample: RestOrgPrerequisiteDoc = {
  ...sampleWithRequiredData,
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
  createdOn: sampleWithRequiredData.createdOn?.toJSON(),
};

describe('OrgPrerequisiteDoc Service', () => {
  let service: OrgPrerequisiteDocService;
  let httpMock: HttpTestingController;
  let expectedResult: IOrgPrerequisiteDoc | IOrgPrerequisiteDoc[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(OrgPrerequisiteDocService);
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

    it('should create a OrgPrerequisiteDoc', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const orgPrerequisiteDoc = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(orgPrerequisiteDoc).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a OrgPrerequisiteDoc', () => {
      const orgPrerequisiteDoc = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(orgPrerequisiteDoc).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a OrgPrerequisiteDoc', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of OrgPrerequisiteDoc', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a OrgPrerequisiteDoc', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addOrgPrerequisiteDocToCollectionIfMissing', () => {
      it('should add a OrgPrerequisiteDoc to an empty array', () => {
        const orgPrerequisiteDoc: IOrgPrerequisiteDoc = sampleWithRequiredData;
        expectedResult = service.addOrgPrerequisiteDocToCollectionIfMissing([], orgPrerequisiteDoc);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(orgPrerequisiteDoc);
      });

      it('should not add a OrgPrerequisiteDoc to an array that contains it', () => {
        const orgPrerequisiteDoc: IOrgPrerequisiteDoc = sampleWithRequiredData;
        const orgPrerequisiteDocCollection: IOrgPrerequisiteDoc[] = [
          {
            ...orgPrerequisiteDoc,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addOrgPrerequisiteDocToCollectionIfMissing(orgPrerequisiteDocCollection, orgPrerequisiteDoc);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a OrgPrerequisiteDoc to an array that doesn't contain it", () => {
        const orgPrerequisiteDoc: IOrgPrerequisiteDoc = sampleWithRequiredData;
        const orgPrerequisiteDocCollection: IOrgPrerequisiteDoc[] = [sampleWithPartialData];
        expectedResult = service.addOrgPrerequisiteDocToCollectionIfMissing(orgPrerequisiteDocCollection, orgPrerequisiteDoc);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(orgPrerequisiteDoc);
      });

      it('should add only unique OrgPrerequisiteDoc to an array', () => {
        const orgPrerequisiteDocArray: IOrgPrerequisiteDoc[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const orgPrerequisiteDocCollection: IOrgPrerequisiteDoc[] = [sampleWithRequiredData];
        expectedResult = service.addOrgPrerequisiteDocToCollectionIfMissing(orgPrerequisiteDocCollection, ...orgPrerequisiteDocArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const orgPrerequisiteDoc: IOrgPrerequisiteDoc = sampleWithRequiredData;
        const orgPrerequisiteDoc2: IOrgPrerequisiteDoc = sampleWithPartialData;
        expectedResult = service.addOrgPrerequisiteDocToCollectionIfMissing([], orgPrerequisiteDoc, orgPrerequisiteDoc2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(orgPrerequisiteDoc);
        expect(expectedResult).toContain(orgPrerequisiteDoc2);
      });

      it('should accept null and undefined values', () => {
        const orgPrerequisiteDoc: IOrgPrerequisiteDoc = sampleWithRequiredData;
        expectedResult = service.addOrgPrerequisiteDocToCollectionIfMissing([], null, orgPrerequisiteDoc, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(orgPrerequisiteDoc);
      });

      it('should return initial array if no OrgPrerequisiteDoc is added', () => {
        const orgPrerequisiteDocCollection: IOrgPrerequisiteDoc[] = [sampleWithRequiredData];
        expectedResult = service.addOrgPrerequisiteDocToCollectionIfMissing(orgPrerequisiteDocCollection, undefined, null);
        expect(expectedResult).toEqual(orgPrerequisiteDocCollection);
      });
    });

    describe('compareOrgPrerequisiteDoc', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareOrgPrerequisiteDoc(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareOrgPrerequisiteDoc(entity1, entity2);
        const compareResult2 = service.compareOrgPrerequisiteDoc(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareOrgPrerequisiteDoc(entity1, entity2);
        const compareResult2 = service.compareOrgPrerequisiteDoc(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareOrgPrerequisiteDoc(entity1, entity2);
        const compareResult2 = service.compareOrgPrerequisiteDoc(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
