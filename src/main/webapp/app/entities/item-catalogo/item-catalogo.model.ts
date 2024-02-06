import { ICatalogo } from 'app/entities/catalogo/catalogo.model';

export interface IItemCatalogo {
  id: number;
  nombre?: string | null;
  codigo?: string | null;
  detalle?: string | null;
  catalogCode?: string | null;
  habilitado?: boolean | null;
  catalogo?: ICatalogo | null;
}

export type NewItemCatalogo = Omit<IItemCatalogo, 'id'> & { id: null };
