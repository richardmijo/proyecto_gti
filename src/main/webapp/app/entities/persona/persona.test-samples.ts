import dayjs from 'dayjs/esm';

import { IPersona, NewPersona } from './persona.model';

export const sampleWithRequiredData: IPersona = {
  id: 16883,
  numeroIdentificacion: 'gale including while',
};

export const sampleWithPartialData: IPersona = {
  id: 3449,
  numeroIdentificacion: 'against',
  apellidos: 'meanwhile for',
  nombre: 'amid eek whoa',
  direccion: 'badly painfully yippee',
  telefonoFijo: 'shameful',
  telefonoMovil: 'scrummage',
};

export const sampleWithFullData: IPersona = {
  id: 2077,
  numeroIdentificacion: 'smoothly after',
  apellidos: 'trained',
  nombres: 'knavishly lest',
  nombre: 'other',
  direccion: 'once',
  telefonoFijo: 'ha',
  telefonoMovil: 'hippodrome debut till',
  fechaNacimiento: dayjs('2024-02-05'),
};

export const sampleWithNewData: NewPersona = {
  numeroIdentificacion: 'blindly ick',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
