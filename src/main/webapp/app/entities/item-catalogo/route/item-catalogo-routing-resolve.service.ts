import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IItemCatalogo } from '../item-catalogo.model';
import { ItemCatalogoService } from '../service/item-catalogo.service';

export const itemCatalogoResolve = (route: ActivatedRouteSnapshot): Observable<null | IItemCatalogo> => {
  const id = route.params['id'];
  if (id) {
    return inject(ItemCatalogoService)
      .find(id)
      .pipe(
        mergeMap((itemCatalogo: HttpResponse<IItemCatalogo>) => {
          if (itemCatalogo.body) {
            return of(itemCatalogo.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default itemCatalogoResolve;
