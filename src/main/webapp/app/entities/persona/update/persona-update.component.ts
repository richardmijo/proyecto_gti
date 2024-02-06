import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IItemCatalogo } from 'app/entities/item-catalogo/item-catalogo.model';
import { ItemCatalogoService } from 'app/entities/item-catalogo/service/item-catalogo.service';
import { IPersona } from '../persona.model';
import { PersonaService } from '../service/persona.service';
import { PersonaFormService, PersonaFormGroup } from './persona-form.service';

@Component({
  standalone: true,
  selector: 'jhi-persona-update',
  templateUrl: './persona-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PersonaUpdateComponent implements OnInit {
  isSaving = false;
  persona: IPersona | null = null;

  itemCatalogosSharedCollection: IItemCatalogo[] = [];

  editForm: PersonaFormGroup = this.personaFormService.createPersonaFormGroup();

  constructor(
    protected personaService: PersonaService,
    protected personaFormService: PersonaFormService,
    protected itemCatalogoService: ItemCatalogoService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareItemCatalogo = (o1: IItemCatalogo | null, o2: IItemCatalogo | null): boolean =>
    this.itemCatalogoService.compareItemCatalogo(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ persona }) => {
      this.persona = persona;
      if (persona) {
        this.updateForm(persona);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const persona = this.personaFormService.getPersona(this.editForm);
    if (persona.id !== null) {
      this.subscribeToSaveResponse(this.personaService.update(persona));
    } else {
      this.subscribeToSaveResponse(this.personaService.create(persona));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersona>>): void {
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

  protected updateForm(persona: IPersona): void {
    this.persona = persona;
    this.personaFormService.resetForm(this.editForm, persona);

    this.itemCatalogosSharedCollection = this.itemCatalogoService.addItemCatalogoToCollectionIfMissing<IItemCatalogo>(
      this.itemCatalogosSharedCollection,
      persona.estadoCivil,
      persona.tipoIdentificacion,
      persona.paisProcedencia,
      persona.nivelEducativo,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.itemCatalogoService
      .query()
      .pipe(map((res: HttpResponse<IItemCatalogo[]>) => res.body ?? []))
      .pipe(
        map((itemCatalogos: IItemCatalogo[]) =>
          this.itemCatalogoService.addItemCatalogoToCollectionIfMissing<IItemCatalogo>(
            itemCatalogos,
            this.persona?.estadoCivil,
            this.persona?.tipoIdentificacion,
            this.persona?.paisProcedencia,
            this.persona?.nivelEducativo,
          ),
        ),
      )
      .subscribe((itemCatalogos: IItemCatalogo[]) => (this.itemCatalogosSharedCollection = itemCatalogos));
  }
}
