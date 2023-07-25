import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IGuarantor } from '../guarantor.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../guarantor.test-samples';

import { GuarantorService, RestGuarantor } from './guarantor.service';

const requireRestSample: RestGuarantor = {
  ...sampleWithRequiredData,
  dob: sampleWithRequiredData.dob?.format(DATE_FORMAT),
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
  createdOn: sampleWithRequiredData.createdOn?.toJSON(),
};

describe('Guarantor Service', () => {
  let service: GuarantorService;
  let httpMock: HttpTestingController;
  let expectedResult: IGuarantor | IGuarantor[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(GuarantorService);
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

    it('should create a Guarantor', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const guarantor = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(guarantor).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Guarantor', () => {
      const guarantor = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(guarantor).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Guarantor', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Guarantor', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Guarantor', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addGuarantorToCollectionIfMissing', () => {
      it('should add a Guarantor to an empty array', () => {
        const guarantor: IGuarantor = sampleWithRequiredData;
        expectedResult = service.addGuarantorToCollectionIfMissing([], guarantor);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(guarantor);
      });

      it('should not add a Guarantor to an array that contains it', () => {
        const guarantor: IGuarantor = sampleWithRequiredData;
        const guarantorCollection: IGuarantor[] = [
          {
            ...guarantor,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addGuarantorToCollectionIfMissing(guarantorCollection, guarantor);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Guarantor to an array that doesn't contain it", () => {
        const guarantor: IGuarantor = sampleWithRequiredData;
        const guarantorCollection: IGuarantor[] = [sampleWithPartialData];
        expectedResult = service.addGuarantorToCollectionIfMissing(guarantorCollection, guarantor);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(guarantor);
      });

      it('should add only unique Guarantor to an array', () => {
        const guarantorArray: IGuarantor[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const guarantorCollection: IGuarantor[] = [sampleWithRequiredData];
        expectedResult = service.addGuarantorToCollectionIfMissing(guarantorCollection, ...guarantorArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const guarantor: IGuarantor = sampleWithRequiredData;
        const guarantor2: IGuarantor = sampleWithPartialData;
        expectedResult = service.addGuarantorToCollectionIfMissing([], guarantor, guarantor2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(guarantor);
        expect(expectedResult).toContain(guarantor2);
      });

      it('should accept null and undefined values', () => {
        const guarantor: IGuarantor = sampleWithRequiredData;
        expectedResult = service.addGuarantorToCollectionIfMissing([], null, guarantor, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(guarantor);
      });

      it('should return initial array if no Guarantor is added', () => {
        const guarantorCollection: IGuarantor[] = [sampleWithRequiredData];
        expectedResult = service.addGuarantorToCollectionIfMissing(guarantorCollection, undefined, null);
        expect(expectedResult).toEqual(guarantorCollection);
      });
    });

    describe('compareGuarantor', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareGuarantor(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareGuarantor(entity1, entity2);
        const compareResult2 = service.compareGuarantor(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareGuarantor(entity1, entity2);
        const compareResult2 = service.compareGuarantor(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareGuarantor(entity1, entity2);
        const compareResult2 = service.compareGuarantor(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
