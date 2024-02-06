import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IItemCatalogo } from '../item-catalogo.model';

@Component({
  standalone: true,
  selector: 'jhi-item-catalogo-detail',
  templateUrl: './item-catalogo-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ItemCatalogoDetailComponent {
  @Input() itemCatalogo: IItemCatalogo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  previousState(): void {
    window.history.back();
  }
}
