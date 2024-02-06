import { IItemCatalogo, NewItemCatalogo } from './item-catalogo.model';

export const sampleWithRequiredData: IItemCatalogo = {
  id: 17874,
  nombre: 'towards prohibit',
  codigo: 'flatline yippee flaky',
};

export const sampleWithPartialData: IItemCatalogo = {
  id: 18707,
  nombre: 'arrogantly',
  codigo: 'yum',
  catalogCode: 'oof',
};

export const sampleWithFullData: IItemCatalogo = {
  id: 19858,
  nombre: 'circumference lest',
  codigo: 'on',
  detalle: 'shrilly apud atop',
  catalogCode: 'faucet',
  habilitado: false,
};

export const sampleWithNewData: NewItemCatalogo = {
  nombre: 'innocently oof',
  codigo: 'atop foolish subtitle',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
