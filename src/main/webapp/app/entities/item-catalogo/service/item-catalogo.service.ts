import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IItemCatalogo, NewItemCatalogo } from '../item-catalogo.model';

export type PartialUpdateItemCatalogo = Partial<IItemCatalogo> & Pick<IItemCatalogo, 'id'>;

export type EntityResponseType = HttpResponse<IItemCatalogo>;
export type EntityArrayResponseType = HttpResponse<IItemCatalogo[]>;

@Injectable({ providedIn: 'root' })
export class ItemCatalogoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/item-catalogos');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(itemCatalogo: NewItemCatalogo): Observable<EntityResponseType> {
    return this.http.post<IItemCatalogo>(this.resourceUrl, itemCatalogo, { observe: 'response' });
  }

  update(itemCatalogo: IItemCatalogo): Observable<EntityResponseType> {
    return this.http.put<IItemCatalogo>(`${this.resourceUrl}/${this.getItemCatalogoIdentifier(itemCatalogo)}`, itemCatalogo, {
      observe: 'response',
    });
  }

  partialUpdate(itemCatalogo: PartialUpdateItemCatalogo): Observable<EntityResponseType> {
    return this.http.patch<IItemCatalogo>(`${this.resourceUrl}/${this.getItemCatalogoIdentifier(itemCatalogo)}`, itemCatalogo, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IItemCatalogo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IItemCatalogo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getItemCatalogoIdentifier(itemCatalogo: Pick<IItemCatalogo, 'id'>): number {
    return itemCatalogo.id;
  }

  compareItemCatalogo(o1: Pick<IItemCatalogo, 'id'> | null, o2: Pick<IItemCatalogo, 'id'> | null): boolean {
    return o1 && o2 ? this.getItemCatalogoIdentifier(o1) === this.getItemCatalogoIdentifier(o2) : o1 === o2;
  }

  addItemCatalogoToCollectionIfMissing<Type extends Pick<IItemCatalogo, 'id'>>(
    itemCatalogoCollection: Type[],
    ...itemCatalogosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const itemCatalogos: Type[] = itemCatalogosToCheck.filter(isPresent);
    if (itemCatalogos.length > 0) {
      const itemCatalogoCollectionIdentifiers = itemCatalogoCollection.map(
        itemCatalogoItem => this.getItemCatalogoIdentifier(itemCatalogoItem)!,
      );
      const itemCatalogosToAdd = itemCatalogos.filter(itemCatalogoItem => {
        const itemCatalogoIdentifier = this.getItemCatalogoIdentifier(itemCatalogoItem);
        if (itemCatalogoCollectionIdentifiers.includes(itemCatalogoIdentifier)) {
          return false;
        }
        itemCatalogoCollectionIdentifiers.push(itemCatalogoIdentifier);
        return true;
      });
      return [...itemCatalogosToAdd, ...itemCatalogoCollection];
    }
    return itemCatalogoCollection;
  }
}
