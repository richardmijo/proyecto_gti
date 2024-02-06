import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICatalogo, NewCatalogo } from '../catalogo.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICatalogo for edit and NewCatalogoFormGroupInput for create.
 */
type CatalogoFormGroupInput = ICatalogo | PartialWithRequiredKeyOf<NewCatalogo>;

type CatalogoFormDefaults = Pick<NewCatalogo, 'id' | 'habilitado'>;

type CatalogoFormGroupContent = {
  id: FormControl<ICatalogo['id'] | NewCatalogo['id']>;
  nombre: FormControl<ICatalogo['nombre']>;
  codigo: FormControl<ICatalogo['codigo']>;
  detalle: FormControl<ICatalogo['detalle']>;
  habilitado: FormControl<ICatalogo['habilitado']>;
};

export type CatalogoFormGroup = FormGroup<CatalogoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CatalogoFormService {
  createCatalogoFormGroup(catalogo: CatalogoFormGroupInput = { id: null }): CatalogoFormGroup {
    const catalogoRawValue = {
      ...this.getFormDefaults(),
      ...catalogo,
    };
    return new FormGroup<CatalogoFormGroupContent>({
      id: new FormControl(
        { value: catalogoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nombre: new FormControl(catalogoRawValue.nombre, {
        validators: [Validators.required],
      }),
      codigo: new FormControl(catalogoRawValue.codigo, {
        validators: [Validators.required],
      }),
      detalle: new FormControl(catalogoRawValue.detalle),
      habilitado: new FormControl(catalogoRawValue.habilitado),
    });
  }

  getCatalogo(form: CatalogoFormGroup): ICatalogo | NewCatalogo {
    return form.getRawValue() as ICatalogo | NewCatalogo;
  }

  resetForm(form: CatalogoFormGroup, catalogo: CatalogoFormGroupInput): void {
    const catalogoRawValue = { ...this.getFormDefaults(), ...catalogo };
    form.reset(
      {
        ...catalogoRawValue,
        id: { value: catalogoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CatalogoFormDefaults {
    return {
      id: null,
      habilitado: false,
    };
  }
}
