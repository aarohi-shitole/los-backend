import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IMemberExistingfacility } from '../member-existingfacility.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../member-existingfacility.test-samples';

import { MemberExistingfacilityService, RestMemberExistingfacility } from './member-existingfacility.service';

const requireRestSample: RestMemberExistingfacility = {
  ...sampleWithRequiredData,
  sectionDate: sampleWithRequiredData.sectionDate?.toJSON(),
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
  createdOn: sampleWithRequiredData.createdOn?.toJSON(),
};

describe('MemberExistingfacility Service', () => {
  let service: MemberExistingfacilityService;
  let httpMock: HttpTestingController;
  let expectedResult: IMemberExistingfacility | IMemberExistingfacility[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MemberExistingfacilityService);
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

    it('should create a MemberExistingfacility', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const memberExistingfacility = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(memberExistingfacility).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MemberExistingfacility', () => {
      const memberExistingfacility = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(memberExistingfacility).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a MemberExistingfacility', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MemberExistingfacility', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a MemberExistingfacility', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addMemberExistingfacilityToCollectionIfMissing', () => {
      it('should add a MemberExistingfacility to an empty array', () => {
        const memberExistingfacility: IMemberExistingfacility = sampleWithRequiredData;
        expectedResult = service.addMemberExistingfacilityToCollectionIfMissing([], memberExistingfacility);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(memberExistingfacility);
      });

      it('should not add a MemberExistingfacility to an array that contains it', () => {
        const memberExistingfacility: IMemberExistingfacility = sampleWithRequiredData;
        const memberExistingfacilityCollection: IMemberExistingfacility[] = [
          {
            ...memberExistingfacility,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMemberExistingfacilityToCollectionIfMissing(memberExistingfacilityCollection, memberExistingfacility);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MemberExistingfacility to an array that doesn't contain it", () => {
        const memberExistingfacility: IMemberExistingfacility = sampleWithRequiredData;
        const memberExistingfacilityCollection: IMemberExistingfacility[] = [sampleWithPartialData];
        expectedResult = service.addMemberExistingfacilityToCollectionIfMissing(memberExistingfacilityCollection, memberExistingfacility);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(memberExistingfacility);
      });

      it('should add only unique MemberExistingfacility to an array', () => {
        const memberExistingfacilityArray: IMemberExistingfacility[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const memberExistingfacilityCollection: IMemberExistingfacility[] = [sampleWithRequiredData];
        expectedResult = service.addMemberExistingfacilityToCollectionIfMissing(
          memberExistingfacilityCollection,
          ...memberExistingfacilityArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const memberExistingfacility: IMemberExistingfacility = sampleWithRequiredData;
        const memberExistingfacility2: IMemberExistingfacility = sampleWithPartialData;
        expectedResult = service.addMemberExistingfacilityToCollectionIfMissing([], memberExistingfacility, memberExistingfacility2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(memberExistingfacility);
        expect(expectedResult).toContain(memberExistingfacility2);
      });

      it('should accept null and undefined values', () => {
        const memberExistingfacility: IMemberExistingfacility = sampleWithRequiredData;
        expectedResult = service.addMemberExistingfacilityToCollectionIfMissing([], null, memberExistingfacility, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(memberExistingfacility);
      });

      it('should return initial array if no MemberExistingfacility is added', () => {
        const memberExistingfacilityCollection: IMemberExistingfacility[] = [sampleWithRequiredData];
        expectedResult = service.addMemberExistingfacilityToCollectionIfMissing(memberExistingfacilityCollection, undefined, null);
        expect(expectedResult).toEqual(memberExistingfacilityCollection);
      });
    });

    describe('compareMemberExistingfacility', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMemberExistingfacility(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareMemberExistingfacility(entity1, entity2);
        const compareResult2 = service.compareMemberExistingfacility(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareMemberExistingfacility(entity1, entity2);
        const compareResult2 = service.compareMemberExistingfacility(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareMemberExistingfacility(entity1, entity2);
        const compareResult2 = service.compareMemberExistingfacility(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
