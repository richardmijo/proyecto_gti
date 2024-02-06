import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ItemCatalogoComponent } from './list/item-catalogo.component';
import { ItemCatalogoDetailComponent } from './detail/item-catalogo-detail.component';
import { ItemCatalogoUpdateComponent } from './update/item-catalogo-update.component';
import ItemCatalogoResolve from './route/item-catalogo-routing-resolve.service';

const itemCatalogoRoute: Routes = [
  {
    path: '',
    component: ItemCatalogoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ItemCatalogoDetailComponent,
    resolve: {
      itemCatalogo: ItemCatalogoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ItemCatalogoUpdateComponent,
    resolve: {
      itemCatalogo: ItemCatalogoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ItemCatalogoUpdateComponent,
    resolve: {
      itemCatalogo: ItemCatalogoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default itemCatalogoRoute;
