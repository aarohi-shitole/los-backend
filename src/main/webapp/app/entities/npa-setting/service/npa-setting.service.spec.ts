import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { INpaSetting } from '../npa-setting.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../npa-setting.test-samples';

import { NpaSettingService, RestNpaSetting } from './npa-setting.service';

const requireRestSample: RestNpaSetting = {
  ...sampleWithRequiredData,
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
  createdOn: sampleWithRequiredData.createdOn?.toJSON(),
};

describe('NpaSetting Service', () => {
  let service: NpaSettingService;
  let httpMock: HttpTestingController;
  let expectedResult: INpaSetting | INpaSetting[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(NpaSettingService);
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

    it('should create a NpaSetting', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const npaSetting = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(npaSetting).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a NpaSetting', () => {
      const npaSetting = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(npaSetting).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a NpaSetting', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of NpaSetting', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a NpaSetting', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addNpaSettingToCollectionIfMissing', () => {
      it('should add a NpaSetting to an empty array', () => {
        const npaSetting: INpaSetting = sampleWithRequiredData;
        expectedResult = service.addNpaSettingToCollectionIfMissing([], npaSetting);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(npaSetting);
      });

      it('should not add a NpaSetting to an array that contains it', () => {
        const npaSetting: INpaSetting = sampleWithRequiredData;
        const npaSettingCollection: INpaSetting[] = [
          {
            ...npaSetting,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addNpaSettingToCollectionIfMissing(npaSettingCollection, npaSetting);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a NpaSetting to an array that doesn't contain it", () => {
        const npaSetting: INpaSetting = sampleWithRequiredData;
        const npaSettingCollection: INpaSetting[] = [sampleWithPartialData];
        expectedResult = service.addNpaSettingToCollectionIfMissing(npaSettingCollection, npaSetting);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(npaSetting);
      });

      it('should add only unique NpaSetting to an array', () => {
        const npaSettingArray: INpaSetting[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const npaSettingCollection: INpaSetting[] = [sampleWithRequiredData];
        expectedResult = service.addNpaSettingToCollectionIfMissing(npaSettingCollection, ...npaSettingArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const npaSetting: INpaSetting = sampleWithRequiredData;
        const npaSetting2: INpaSetting = sampleWithPartialData;
        expectedResult = service.addNpaSettingToCollectionIfMissing([], npaSetting, npaSetting2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(npaSetting);
        expect(expectedResult).toContain(npaSetting2);
      });

      it('should accept null and undefined values', () => {
        const npaSetting: INpaSetting = sampleWithRequiredData;
        expectedResult = service.addNpaSettingToCollectionIfMissing([], null, npaSetting, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(npaSetting);
      });

      it('should return initial array if no NpaSetting is added', () => {
        const npaSettingCollection: INpaSetting[] = [sampleWithRequiredData];
        expectedResult = service.addNpaSettingToCollectionIfMissing(npaSettingCollection, undefined, null);
        expect(expectedResult).toEqual(npaSettingCollection);
      });
    });

    describe('compareNpaSetting', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareNpaSetting(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareNpaSetting(entity1, entity2);
        const compareResult2 = service.compareNpaSetting(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareNpaSetting(entity1, entity2);
        const compareResult2 = service.compareNpaSetting(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareNpaSetting(entity1, entity2);
        const compareResult2 = service.compareNpaSetting(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
