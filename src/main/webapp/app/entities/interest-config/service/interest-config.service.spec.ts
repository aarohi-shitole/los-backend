import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IInterestConfig } from '../interest-config.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../interest-config.test-samples';

import { InterestConfigService, RestInterestConfig } from './interest-config.service';

const requireRestSample: RestInterestConfig = {
  ...sampleWithRequiredData,
  startDate: sampleWithRequiredData.startDate?.toJSON(),
  endDate: sampleWithRequiredData.endDate?.toJSON(),
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
};

describe('InterestConfig Service', () => {
  let service: InterestConfigService;
  let httpMock: HttpTestingController;
  let expectedResult: IInterestConfig | IInterestConfig[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(InterestConfigService);
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

    it('should create a InterestConfig', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const interestConfig = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(interestConfig).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a InterestConfig', () => {
      const interestConfig = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(interestConfig).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a InterestConfig', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of InterestConfig', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a InterestConfig', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addInterestConfigToCollectionIfMissing', () => {
      it('should add a InterestConfig to an empty array', () => {
        const interestConfig: IInterestConfig = sampleWithRequiredData;
        expectedResult = service.addInterestConfigToCollectionIfMissing([], interestConfig);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(interestConfig);
      });

      it('should not add a InterestConfig to an array that contains it', () => {
        const interestConfig: IInterestConfig = sampleWithRequiredData;
        const interestConfigCollection: IInterestConfig[] = [
          {
            ...interestConfig,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addInterestConfigToCollectionIfMissing(interestConfigCollection, interestConfig);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a InterestConfig to an array that doesn't contain it", () => {
        const interestConfig: IInterestConfig = sampleWithRequiredData;
        const interestConfigCollection: IInterestConfig[] = [sampleWithPartialData];
        expectedResult = service.addInterestConfigToCollectionIfMissing(interestConfigCollection, interestConfig);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(interestConfig);
      });

      it('should add only unique InterestConfig to an array', () => {
        const interestConfigArray: IInterestConfig[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const interestConfigCollection: IInterestConfig[] = [sampleWithRequiredData];
        expectedResult = service.addInterestConfigToCollectionIfMissing(interestConfigCollection, ...interestConfigArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const interestConfig: IInterestConfig = sampleWithRequiredData;
        const interestConfig2: IInterestConfig = sampleWithPartialData;
        expectedResult = service.addInterestConfigToCollectionIfMissing([], interestConfig, interestConfig2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(interestConfig);
        expect(expectedResult).toContain(interestConfig2);
      });

      it('should accept null and undefined values', () => {
        const interestConfig: IInterestConfig = sampleWithRequiredData;
        expectedResult = service.addInterestConfigToCollectionIfMissing([], null, interestConfig, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(interestConfig);
      });

      it('should return initial array if no InterestConfig is added', () => {
        const interestConfigCollection: IInterestConfig[] = [sampleWithRequiredData];
        expectedResult = service.addInterestConfigToCollectionIfMissing(interestConfigCollection, undefined, null);
        expect(expectedResult).toEqual(interestConfigCollection);
      });
    });

    describe('compareInterestConfig', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareInterestConfig(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareInterestConfig(entity1, entity2);
        const compareResult2 = service.compareInterestConfig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareInterestConfig(entity1, entity2);
        const compareResult2 = service.compareInterestConfig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareInterestConfig(entity1, entity2);
        const compareResult2 = service.compareInterestConfig(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
