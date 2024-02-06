import { IItemCatalogo } from 'app/entities/item-catalogo/item-catalogo.model';

export interface ICatalogo {
  id: number;
  nombre?: string | null;
  codigo?: string | null;
  detalle?: string | null;
  habilitado?: boolean | null;
  itemsCatalogos?: IItemCatalogo[] | null;
}

export type NewCatalogo = Omit<ICatalogo, 'id'> & { id: null };
