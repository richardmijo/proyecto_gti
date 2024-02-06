import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../item-catalogo.test-samples';

import { ItemCatalogoFormService } from './item-catalogo-form.service';

describe('ItemCatalogo Form Service', () => {
  let service: ItemCatalogoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ItemCatalogoFormService);
  });

  describe('Service methods', () => {
    describe('createItemCatalogoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createItemCatalogoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombre: expect.any(Object),
            codigo: expect.any(Object),
            detalle: expect.any(Object),
            catalogCode: expect.any(Object),
            habilitado: expect.any(Object),
            catalogo: expect.any(Object),
          }),
        );
      });

      it('passing IItemCatalogo should create a new form with FormGroup', () => {
        const formGroup = service.createItemCatalogoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombre: expect.any(Object),
            codigo: expect.any(Object),
            detalle: expect.any(Object),
            catalogCode: expect.any(Object),
            habilitado: expect.any(Object),
            catalogo: expect.any(Object),
          }),
        );
      });
    });

    describe('getItemCatalogo', () => {
      it('should return NewItemCatalogo for default ItemCatalogo initial value', () => {
        const formGroup = service.createItemCatalogoFormGroup(sampleWithNewData);

        const itemCatalogo = service.getItemCatalogo(formGroup) as any;

        expect(itemCatalogo).toMatchObject(sampleWithNewData);
      });

      it('should return NewItemCatalogo for empty ItemCatalogo initial value', () => {
        const formGroup = service.createItemCatalogoFormGroup();

        const itemCatalogo = service.getItemCatalogo(formGroup) as any;

        expect(itemCatalogo).toMatchObject({});
      });

      it('should return IItemCatalogo', () => {
        const formGroup = service.createItemCatalogoFormGroup(sampleWithRequiredData);

        const itemCatalogo = service.getItemCatalogo(formGroup) as any;

        expect(itemCatalogo).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IItemCatalogo should not enable id FormControl', () => {
        const formGroup = service.createItemCatalogoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewItemCatalogo should disable id FormControl', () => {
        const formGroup = service.createItemCatalogoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
