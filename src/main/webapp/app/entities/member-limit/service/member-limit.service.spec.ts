import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IMemberLimit } from '../member-limit.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../member-limit.test-samples';

import { MemberLimitService, RestMemberLimit } from './member-limit.service';

const requireRestSample: RestMemberLimit = {
  ...sampleWithRequiredData,
  dtLimitSanctioned: sampleWithRequiredData.dtLimitSanctioned?.format(DATE_FORMAT),
  dtLimitExpired: sampleWithRequiredData.dtLimitExpired?.format(DATE_FORMAT),
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
};

describe('MemberLimit Service', () => {
  let service: MemberLimitService;
  let httpMock: HttpTestingController;
  let expectedResult: IMemberLimit | IMemberLimit[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MemberLimitService);
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

    it('should create a MemberLimit', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const memberLimit = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(memberLimit).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MemberLimit', () => {
      const memberLimit = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(memberLimit).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a MemberLimit', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MemberLimit', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a MemberLimit', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addMemberLimitToCollectionIfMissing', () => {
      it('should add a MemberLimit to an empty array', () => {
        const memberLimit: IMemberLimit = sampleWithRequiredData;
        expectedResult = service.addMemberLimitToCollectionIfMissing([], memberLimit);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(memberLimit);
      });

      it('should not add a MemberLimit to an array that contains it', () => {
        const memberLimit: IMemberLimit = sampleWithRequiredData;
        const memberLimitCollection: IMemberLimit[] = [
          {
            ...memberLimit,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMemberLimitToCollectionIfMissing(memberLimitCollection, memberLimit);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MemberLimit to an array that doesn't contain it", () => {
        const memberLimit: IMemberLimit = sampleWithRequiredData;
        const memberLimitCollection: IMemberLimit[] = [sampleWithPartialData];
        expectedResult = service.addMemberLimitToCollectionIfMissing(memberLimitCollection, memberLimit);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(memberLimit);
      });

      it('should add only unique MemberLimit to an array', () => {
        const memberLimitArray: IMemberLimit[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const memberLimitCollection: IMemberLimit[] = [sampleWithRequiredData];
        expectedResult = service.addMemberLimitToCollectionIfMissing(memberLimitCollection, ...memberLimitArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const memberLimit: IMemberLimit = sampleWithRequiredData;
        const memberLimit2: IMemberLimit = sampleWithPartialData;
        expectedResult = service.addMemberLimitToCollectionIfMissing([], memberLimit, memberLimit2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(memberLimit);
        expect(expectedResult).toContain(memberLimit2);
      });

      it('should accept null and undefined values', () => {
        const memberLimit: IMemberLimit = sampleWithRequiredData;
        expectedResult = service.addMemberLimitToCollectionIfMissing([], null, memberLimit, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(memberLimit);
      });

      it('should return initial array if no MemberLimit is added', () => {
        const memberLimitCollection: IMemberLimit[] = [sampleWithRequiredData];
        expectedResult = service.addMemberLimitToCollectionIfMissing(memberLimitCollection, undefined, null);
        expect(expectedResult).toEqual(memberLimitCollection);
      });
    });

    describe('compareMemberLimit', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMemberLimit(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareMemberLimit(entity1, entity2);
        const compareResult2 = service.compareMemberLimit(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareMemberLimit(entity1, entity2);
        const compareResult2 = service.compareMemberLimit(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareMemberLimit(entity1, entity2);
        const compareResult2 = service.compareMemberLimit(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
