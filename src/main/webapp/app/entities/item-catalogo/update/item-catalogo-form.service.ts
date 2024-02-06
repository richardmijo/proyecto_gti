import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IItemCatalogo, NewItemCatalogo } from '../item-catalogo.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IItemCatalogo for edit and NewItemCatalogoFormGroupInput for create.
 */
type ItemCatalogoFormGroupInput = IItemCatalogo | PartialWithRequiredKeyOf<NewItemCatalogo>;

type ItemCatalogoFormDefaults = Pick<NewItemCatalogo, 'id' | 'habilitado'>;

type ItemCatalogoFormGroupContent = {
  id: FormControl<IItemCatalogo['id'] | NewItemCatalogo['id']>;
  nombre: FormControl<IItemCatalogo['nombre']>;
  codigo: FormControl<IItemCatalogo['codigo']>;
  detalle: FormControl<IItemCatalogo['detalle']>;
  catalogCode: FormControl<IItemCatalogo['catalogCode']>;
  habilitado: FormControl<IItemCatalogo['habilitado']>;
  catalogo: FormControl<IItemCatalogo['catalogo']>;
};

export type ItemCatalogoFormGroup = FormGroup<ItemCatalogoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ItemCatalogoFormService {
  createItemCatalogoFormGroup(itemCatalogo: ItemCatalogoFormGroupInput = { id: null }): ItemCatalogoFormGroup {
    const itemCatalogoRawValue = {
      ...this.getFormDefaults(),
      ...itemCatalogo,
    };
    return new FormGroup<ItemCatalogoFormGroupContent>({
      id: new FormControl(
        { value: itemCatalogoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nombre: new FormControl(itemCatalogoRawValue.nombre, {
        validators: [Validators.required],
      }),
      codigo: new FormControl(itemCatalogoRawValue.codigo, {
        validators: [Validators.required],
      }),
      detalle: new FormControl(itemCatalogoRawValue.detalle),
      catalogCode: new FormControl(itemCatalogoRawValue.catalogCode),
      habilitado: new FormControl(itemCatalogoRawValue.habilitado),
      catalogo: new FormControl(itemCatalogoRawValue.catalogo),
    });
  }

  getItemCatalogo(form: ItemCatalogoFormGroup): IItemCatalogo | NewItemCatalogo {
    return form.getRawValue() as IItemCatalogo | NewItemCatalogo;
  }

  resetForm(form: ItemCatalogoFormGroup, itemCatalogo: ItemCatalogoFormGroupInput): void {
    const itemCatalogoRawValue = { ...this.getFormDefaults(), ...itemCatalogo };
    form.reset(
      {
        ...itemCatalogoRawValue,
        id: { value: itemCatalogoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ItemCatalogoFormDefaults {
    return {
      id: null,
      habilitado: false,
    };
  }
}
