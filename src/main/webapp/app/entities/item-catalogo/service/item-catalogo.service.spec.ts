import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IItemCatalogo } from '../item-catalogo.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../item-catalogo.test-samples';

import { ItemCatalogoService } from './item-catalogo.service';

const requireRestSample: IItemCatalogo = {
  ...sampleWithRequiredData,
};

describe('ItemCatalogo Service', () => {
  let service: ItemCatalogoService;
  let httpMock: HttpTestingController;
  let expectedResult: IItemCatalogo | IItemCatalogo[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ItemCatalogoService);
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

    it('should create a ItemCatalogo', () => {
      const itemCatalogo = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(itemCatalogo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ItemCatalogo', () => {
      const itemCatalogo = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(itemCatalogo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ItemCatalogo', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ItemCatalogo', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ItemCatalogo', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addItemCatalogoToCollectionIfMissing', () => {
      it('should add a ItemCatalogo to an empty array', () => {
        const itemCatalogo: IItemCatalogo = sampleWithRequiredData;
        expectedResult = service.addItemCatalogoToCollectionIfMissing([], itemCatalogo);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(itemCatalogo);
      });

      it('should not add a ItemCatalogo to an array that contains it', () => {
        const itemCatalogo: IItemCatalogo = sampleWithRequiredData;
        const itemCatalogoCollection: IItemCatalogo[] = [
          {
            ...itemCatalogo,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addItemCatalogoToCollectionIfMissing(itemCatalogoCollection, itemCatalogo);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ItemCatalogo to an array that doesn't contain it", () => {
        const itemCatalogo: IItemCatalogo = sampleWithRequiredData;
        const itemCatalogoCollection: IItemCatalogo[] = [sampleWithPartialData];
        expectedResult = service.addItemCatalogoToCollectionIfMissing(itemCatalogoCollection, itemCatalogo);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(itemCatalogo);
      });

      it('should add only unique ItemCatalogo to an array', () => {
        const itemCatalogoArray: IItemCatalogo[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const itemCatalogoCollection: IItemCatalogo[] = [sampleWithRequiredData];
        expectedResult = service.addItemCatalogoToCollectionIfMissing(itemCatalogoCollection, ...itemCatalogoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const itemCatalogo: IItemCatalogo = sampleWithRequiredData;
        const itemCatalogo2: IItemCatalogo = sampleWithPartialData;
        expectedResult = service.addItemCatalogoToCollectionIfMissing([], itemCatalogo, itemCatalogo2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(itemCatalogo);
        expect(expectedResult).toContain(itemCatalogo2);
      });

      it('should accept null and undefined values', () => {
        const itemCatalogo: IItemCatalogo = sampleWithRequiredData;
        expectedResult = service.addItemCatalogoToCollectionIfMissing([], null, itemCatalogo, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(itemCatalogo);
      });

      it('should return initial array if no ItemCatalogo is added', () => {
        const itemCatalogoCollection: IItemCatalogo[] = [sampleWithRequiredData];
        expectedResult = service.addItemCatalogoToCollectionIfMissing(itemCatalogoCollection, undefined, null);
        expect(expectedResult).toEqual(itemCatalogoCollection);
      });
    });

    describe('compareItemCatalogo', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareItemCatalogo(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareItemCatalogo(entity1, entity2);
        const compareResult2 = service.compareItemCatalogo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareItemCatalogo(entity1, entity2);
        const compareResult2 = service.compareItemCatalogo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareItemCatalogo(entity1, entity2);
        const compareResult2 = service.compareItemCatalogo(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
