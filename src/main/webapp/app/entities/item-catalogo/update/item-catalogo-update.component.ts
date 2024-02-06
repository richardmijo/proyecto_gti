import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICatalogo } from 'app/entities/catalogo/catalogo.model';
import { CatalogoService } from 'app/entities/catalogo/service/catalogo.service';
import { IItemCatalogo } from '../item-catalogo.model';
import { ItemCatalogoService } from '../service/item-catalogo.service';
import { ItemCatalogoFormService, ItemCatalogoFormGroup } from './item-catalogo-form.service';

@Component({
  standalone: true,
  selector: 'jhi-item-catalogo-update',
  templateUrl: './item-catalogo-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ItemCatalogoUpdateComponent implements OnInit {
  isSaving = false;
  itemCatalogo: IItemCatalogo | null = null;

  catalogosSharedCollection: ICatalogo[] = [];

  editForm: ItemCatalogoFormGroup = this.itemCatalogoFormService.createItemCatalogoFormGroup();

  constructor(
    protected itemCatalogoService: ItemCatalogoService,
    protected itemCatalogoFormService: ItemCatalogoFormService,
    protected catalogoService: CatalogoService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareCatalogo = (o1: ICatalogo | null, o2: ICatalogo | null): boolean => this.catalogoService.compareCatalogo(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ itemCatalogo }) => {
      this.itemCatalogo = itemCatalogo;
      if (itemCatalogo) {
        this.updateForm(itemCatalogo);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const itemCatalogo = this.itemCatalogoFormService.getItemCatalogo(this.editForm);
    if (itemCatalogo.id !== null) {
      this.subscribeToSaveResponse(this.itemCatalogoService.update(itemCatalogo));
    } else {
      this.subscribeToSaveResponse(this.itemCatalogoService.create(itemCatalogo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IItemCatalogo>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(itemCatalogo: IItemCatalogo): void {
    this.itemCatalogo = itemCatalogo;
    this.itemCatalogoFormService.resetForm(this.editForm, itemCatalogo);

    this.catalogosSharedCollection = this.catalogoService.addCatalogoToCollectionIfMissing<ICatalogo>(
      this.catalogosSharedCollection,
      itemCatalogo.catalogo,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.catalogoService
      .query()
      .pipe(map((res: HttpResponse<ICatalogo[]>) => res.body ?? []))
      .pipe(
        map((catalogos: ICatalogo[]) =>
          this.catalogoService.addCatalogoToCollectionIfMissing<ICatalogo>(catalogos, this.itemCatalogo?.catalogo),
        ),
      )
      .subscribe((catalogos: ICatalogo[]) => (this.catalogosSharedCollection = catalogos));
  }
}
