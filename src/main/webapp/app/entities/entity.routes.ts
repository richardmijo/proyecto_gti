import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'catalogo',
    data: { pageTitle: 'proyectoGtiApp.catalogo.home.title' },
    loadChildren: () => import('./catalogo/catalogo.routes'),
  },
  {
    path: 'item-catalogo',
    data: { pageTitle: 'proyectoGtiApp.itemCatalogo.home.title' },
    loadChildren: () => import('./item-catalogo/item-catalogo.routes'),
  },
  {
    path: 'persona',
    data: { pageTitle: 'proyectoGtiApp.persona.home.title' },
    loadChildren: () => import('./persona/persona.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
