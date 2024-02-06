import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ItemCatalogoDetailComponent } from './item-catalogo-detail.component';

describe('ItemCatalogo Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ItemCatalogoDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ItemCatalogoDetailComponent,
              resolve: { itemCatalogo: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ItemCatalogoDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load itemCatalogo on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ItemCatalogoDetailComponent);

      // THEN
      expect(instance.itemCatalogo).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
