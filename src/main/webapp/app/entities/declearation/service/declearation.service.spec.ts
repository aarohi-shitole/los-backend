import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDeclearation } from '../declearation.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../declearation.test-samples';

import { DeclearationService, RestDeclearation } from './declearation.service';

const requireRestSample: RestDeclearation = {
  ...sampleWithRequiredData,
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
  createdOn: sampleWithRequiredData.createdOn?.toJSON(),
};

describe('Declearation Service', () => {
  let service: DeclearationService;
  let httpMock: HttpTestingController;
  let expectedResult: IDeclearation | IDeclearation[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DeclearationService);
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

    it('should create a Declearation', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const declearation = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(declearation).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Declearation', () => {
      const declearation = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(declearation).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Declearation', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Declearation', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Declearation', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDeclearationToCollectionIfMissing', () => {
      it('should add a Declearation to an empty array', () => {
        const declearation: IDeclearation = sampleWithRequiredData;
        expectedResult = service.addDeclearationToCollectionIfMissing([], declearation);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(declearation);
      });

      it('should not add a Declearation to an array that contains it', () => {
        const declearation: IDeclearation = sampleWithRequiredData;
        const declearationCollection: IDeclearation[] = [
          {
            ...declearation,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDeclearationToCollectionIfMissing(declearationCollection, declearation);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Declearation to an array that doesn't contain it", () => {
        const declearation: IDeclearation = sampleWithRequiredData;
        const declearationCollection: IDeclearation[] = [sampleWithPartialData];
        expectedResult = service.addDeclearationToCollectionIfMissing(declearationCollection, declearation);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(declearation);
      });

      it('should add only unique Declearation to an array', () => {
        const declearationArray: IDeclearation[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const declearationCollection: IDeclearation[] = [sampleWithRequiredData];
        expectedResult = service.addDeclearationToCollectionIfMissing(declearationCollection, ...declearationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const declearation: IDeclearation = sampleWithRequiredData;
        const declearation2: IDeclearation = sampleWithPartialData;
        expectedResult = service.addDeclearationToCollectionIfMissing([], declearation, declearation2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(declearation);
        expect(expectedResult).toContain(declearation2);
      });

      it('should accept null and undefined values', () => {
        const declearation: IDeclearation = sampleWithRequiredData;
        expectedResult = service.addDeclearationToCollectionIfMissing([], null, declearation, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(declearation);
      });

      it('should return initial array if no Declearation is added', () => {
        const declearationCollection: IDeclearation[] = [sampleWithRequiredData];
        expectedResult = service.addDeclearationToCollectionIfMissing(declearationCollection, undefined, null);
        expect(expectedResult).toEqual(declearationCollection);
      });
    });

    describe('compareDeclearation', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDeclearation(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDeclearation(entity1, entity2);
        const compareResult2 = service.compareDeclearation(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDeclearation(entity1, entity2);
        const compareResult2 = service.compareDeclearation(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDeclearation(entity1, entity2);
        const compareResult2 = service.compareDeclearation(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
