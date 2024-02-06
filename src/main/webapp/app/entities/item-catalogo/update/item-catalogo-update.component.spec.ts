import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ICatalogo } from 'app/entities/catalogo/catalogo.model';
import { CatalogoService } from 'app/entities/catalogo/service/catalogo.service';
import { ItemCatalogoService } from '../service/item-catalogo.service';
import { IItemCatalogo } from '../item-catalogo.model';
import { ItemCatalogoFormService } from './item-catalogo-form.service';

import { ItemCatalogoUpdateComponent } from './item-catalogo-update.component';

describe('ItemCatalogo Management Update Component', () => {
  let comp: ItemCatalogoUpdateComponent;
  let fixture: ComponentFixture<ItemCatalogoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let itemCatalogoFormService: ItemCatalogoFormService;
  let itemCatalogoService: ItemCatalogoService;
  let catalogoService: CatalogoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ItemCatalogoUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ItemCatalogoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ItemCatalogoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    itemCatalogoFormService = TestBed.inject(ItemCatalogoFormService);
    itemCatalogoService = TestBed.inject(ItemCatalogoService);
    catalogoService = TestBed.inject(CatalogoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Catalogo query and add missing value', () => {
      const itemCatalogo: IItemCatalogo = { id: 456 };
      const catalogo: ICatalogo = { id: 26550 };
      itemCatalogo.catalogo = catalogo;

      const catalogoCollection: ICatalogo[] = [{ id: 19380 }];
      jest.spyOn(catalogoService, 'query').mockReturnValue(of(new HttpResponse({ body: catalogoCollection })));
      const additionalCatalogos = [catalogo];
      const expectedCollection: ICatalogo[] = [...additionalCatalogos, ...catalogoCollection];
      jest.spyOn(catalogoService, 'addCatalogoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ itemCatalogo });
      comp.ngOnInit();

      expect(catalogoService.query).toHaveBeenCalled();
      expect(catalogoService.addCatalogoToCollectionIfMissing).toHaveBeenCalledWith(
        catalogoCollection,
        ...additionalCatalogos.map(expect.objectContaining),
      );
      expect(comp.catalogosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const itemCatalogo: IItemCatalogo = { id: 456 };
      const catalogo: ICatalogo = { id: 24561 };
      itemCatalogo.catalogo = catalogo;

      activatedRoute.data = of({ itemCatalogo });
      comp.ngOnInit();

      expect(comp.catalogosSharedCollection).toContain(catalogo);
      expect(comp.itemCatalogo).toEqual(itemCatalogo);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IItemCatalogo>>();
      const itemCatalogo = { id: 123 };
      jest.spyOn(itemCatalogoFormService, 'getItemCatalogo').mockReturnValue(itemCatalogo);
      jest.spyOn(itemCatalogoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ itemCatalogo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: itemCatalogo }));
      saveSubject.complete();

      // THEN
      expect(itemCatalogoFormService.getItemCatalogo).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(itemCatalogoService.update).toHaveBeenCalledWith(expect.objectContaining(itemCatalogo));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IItemCatalogo>>();
      const itemCatalogo = { id: 123 };
      jest.spyOn(itemCatalogoFormService, 'getItemCatalogo').mockReturnValue({ id: null });
      jest.spyOn(itemCatalogoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ itemCatalogo: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: itemCatalogo }));
      saveSubject.complete();

      // THEN
      expect(itemCatalogoFormService.getItemCatalogo).toHaveBeenCalled();
      expect(itemCatalogoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IItemCatalogo>>();
      const itemCatalogo = { id: 123 };
      jest.spyOn(itemCatalogoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ itemCatalogo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(itemCatalogoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCatalogo', () => {
      it('Should forward to catalogoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(catalogoService, 'compareCatalogo');
        comp.compareCatalogo(entity, entity2);
        expect(catalogoService.compareCatalogo).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
