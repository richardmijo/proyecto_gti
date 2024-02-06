import { ICatalogo, NewCatalogo } from './catalogo.model';

export const sampleWithRequiredData: ICatalogo = {
  id: 4014,
  nombre: 'always mmm',
  codigo: 'excepting helplessly bah',
};

export const sampleWithPartialData: ICatalogo = {
  id: 25154,
  nombre: 'lest ah',
  codigo: 'next cleverly download',
  habilitado: false,
};

export const sampleWithFullData: ICatalogo = {
  id: 29927,
  nombre: 'before',
  codigo: 'regular freely',
  detalle: 'thoroughly on',
  habilitado: false,
};

export const sampleWithNewData: NewCatalogo = {
  nombre: 'shocking consequently since',
  codigo: 'aw apropos savannah',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
