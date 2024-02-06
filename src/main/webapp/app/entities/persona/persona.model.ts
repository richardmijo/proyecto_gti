import dayjs from 'dayjs/esm';
import { IItemCatalogo } from 'app/entities/item-catalogo/item-catalogo.model';

export interface IPersona {
  id: number;
  numeroIdentificacion?: string | null;
  apellidos?: string | null;
  nombres?: string | null;
  nombre?: string | null;
  direccion?: string | null;
  telefonoFijo?: string | null;
  telefonoMovil?: string | null;
  fechaNacimiento?: dayjs.Dayjs | null;
  estadoCivil?: IItemCatalogo | null;
  tipoIdentificacion?: IItemCatalogo | null;
  paisProcedencia?: IItemCatalogo | null;
  nivelEducativo?: IItemCatalogo | null;
}

export type NewPersona = Omit<IPersona, 'id'> & { id: null };
