import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISequenceGenerator } from '../sequence-generator.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../sequence-generator.test-samples';

import { SequenceGeneratorService } from './sequence-generator.service';

const requireRestSample: ISequenceGenerator = {
  ...sampleWithRequiredData,
};

describe('SequenceGenerator Service', () => {
  let service: SequenceGeneratorService;
  let httpMock: HttpTestingController;
  let expectedResult: ISequenceGenerator | ISequenceGenerator[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SequenceGeneratorService);
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

    it('should create a SequenceGenerator', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const sequenceGenerator = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(sequenceGenerator).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SequenceGenerator', () => {
      const sequenceGenerator = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(sequenceGenerator).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SequenceGenerator', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SequenceGenerator', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SequenceGenerator', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSequenceGeneratorToCollectionIfMissing', () => {
      it('should add a SequenceGenerator to an empty array', () => {
        const sequenceGenerator: ISequenceGenerator = sampleWithRequiredData;
        expectedResult = service.addSequenceGeneratorToCollectionIfMissing([], sequenceGenerator);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sequenceGenerator);
      });

      it('should not add a SequenceGenerator to an array that contains it', () => {
        const sequenceGenerator: ISequenceGenerator = sampleWithRequiredData;
        const sequenceGeneratorCollection: ISequenceGenerator[] = [
          {
            ...sequenceGenerator,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSequenceGeneratorToCollectionIfMissing(sequenceGeneratorCollection, sequenceGenerator);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SequenceGenerator to an array that doesn't contain it", () => {
        const sequenceGenerator: ISequenceGenerator = sampleWithRequiredData;
        const sequenceGeneratorCollection: ISequenceGenerator[] = [sampleWithPartialData];
        expectedResult = service.addSequenceGeneratorToCollectionIfMissing(sequenceGeneratorCollection, sequenceGenerator);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sequenceGenerator);
      });

      it('should add only unique SequenceGenerator to an array', () => {
        const sequenceGeneratorArray: ISequenceGenerator[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const sequenceGeneratorCollection: ISequenceGenerator[] = [sampleWithRequiredData];
        expectedResult = service.addSequenceGeneratorToCollectionIfMissing(sequenceGeneratorCollection, ...sequenceGeneratorArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const sequenceGenerator: ISequenceGenerator = sampleWithRequiredData;
        const sequenceGenerator2: ISequenceGenerator = sampleWithPartialData;
        expectedResult = service.addSequenceGeneratorToCollectionIfMissing([], sequenceGenerator, sequenceGenerator2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sequenceGenerator);
        expect(expectedResult).toContain(sequenceGenerator2);
      });

      it('should accept null and undefined values', () => {
        const sequenceGenerator: ISequenceGenerator = sampleWithRequiredData;
        expectedResult = service.addSequenceGeneratorToCollectionIfMissing([], null, sequenceGenerator, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sequenceGenerator);
      });

      it('should return initial array if no SequenceGenerator is added', () => {
        const sequenceGeneratorCollection: ISequenceGenerator[] = [sampleWithRequiredData];
        expectedResult = service.addSequenceGeneratorToCollectionIfMissing(sequenceGeneratorCollection, undefined, null);
        expect(expectedResult).toEqual(sequenceGeneratorCollection);
      });
    });

    describe('compareSequenceGenerator', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSequenceGenerator(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSequenceGenerator(entity1, entity2);
        const compareResult2 = service.compareSequenceGenerator(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSequenceGenerator(entity1, entity2);
        const compareResult2 = service.compareSequenceGenerator(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSequenceGenerator(entity1, entity2);
        const compareResult2 = service.compareSequenceGenerator(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
