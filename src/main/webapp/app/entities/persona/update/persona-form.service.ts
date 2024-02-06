import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPersona, NewPersona } from '../persona.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPersona for edit and NewPersonaFormGroupInput for create.
 */
type PersonaFormGroupInput = IPersona | PartialWithRequiredKeyOf<NewPersona>;

type PersonaFormDefaults = Pick<NewPersona, 'id'>;

type PersonaFormGroupContent = {
  id: FormControl<IPersona['id'] | NewPersona['id']>;
  numeroIdentificacion: FormControl<IPersona['numeroIdentificacion']>;
  apellidos: FormControl<IPersona['apellidos']>;
  nombres: FormControl<IPersona['nombres']>;
  nombre: FormControl<IPersona['nombre']>;
  direccion: FormControl<IPersona['direccion']>;
  telefonoFijo: FormControl<IPersona['telefonoFijo']>;
  telefonoMovil: FormControl<IPersona['telefonoMovil']>;
  fechaNacimiento: FormControl<IPersona['fechaNacimiento']>;
  estadoCivil: FormControl<IPersona['estadoCivil']>;
  tipoIdentificacion: FormControl<IPersona['tipoIdentificacion']>;
  paisProcedencia: FormControl<IPersona['paisProcedencia']>;
  nivelEducativo: FormControl<IPersona['nivelEducativo']>;
};

export type PersonaFormGroup = FormGroup<PersonaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PersonaFormService {
  createPersonaFormGroup(persona: PersonaFormGroupInput = { id: null }): PersonaFormGroup {
    const personaRawValue = {
      ...this.getFormDefaults(),
      ...persona,
    };
    return new FormGroup<PersonaFormGroupContent>({
      id: new FormControl(
        { value: personaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      numeroIdentificacion: new FormControl(personaRawValue.numeroIdentificacion, {
        validators: [Validators.required],
      }),
      apellidos: new FormControl(personaRawValue.apellidos),
      nombres: new FormControl(personaRawValue.nombres),
      nombre: new FormControl(personaRawValue.nombre),
      direccion: new FormControl(personaRawValue.direccion),
      telefonoFijo: new FormControl(personaRawValue.telefonoFijo),
      telefonoMovil: new FormControl(personaRawValue.telefonoMovil),
      fechaNacimiento: new FormControl(personaRawValue.fechaNacimiento),
      estadoCivil: new FormControl(personaRawValue.estadoCivil, {
        validators: [Validators.required],
      }),
      tipoIdentificacion: new FormControl(personaRawValue.tipoIdentificacion, {
        validators: [Validators.required],
      }),
      paisProcedencia: new FormControl(personaRawValue.paisProcedencia, {
        validators: [Validators.required],
      }),
      nivelEducativo: new FormControl(personaRawValue.nivelEducativo, {
        validators: [Validators.required],
      }),
    });
  }

  getPersona(form: PersonaFormGroup): IPersona | NewPersona {
    return form.getRawValue() as IPersona | NewPersona;
  }

  resetForm(form: PersonaFormGroup, persona: PersonaFormGroupInput): void {
    const personaRawValue = { ...this.getFormDefaults(), ...persona };
    form.reset(
      {
        ...personaRawValue,
        id: { value: personaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PersonaFormDefaults {
    return {
      id: null,
    };
  }
}
