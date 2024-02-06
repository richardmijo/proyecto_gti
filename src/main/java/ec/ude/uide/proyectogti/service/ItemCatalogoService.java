package ec.ude.uide.proyectogti.service;

import ec.ude.uide.proyectogti.domain.ItemCatalogo;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ec.ude.uide.proyectogti.domain.ItemCatalogo}.
 */
public interface ItemCatalogoService {
    /**
     * Save a itemCatalogo.
     *
     * @param itemCatalogo the entity to save.
     * @return the persisted entity.
     */
    ItemCatalogo save(ItemCatalogo itemCatalogo);

    /**
     * Updates a itemCatalogo.
     *
     * @param itemCatalogo the entity to update.
     * @return the persisted entity.
     */
    ItemCatalogo update(ItemCatalogo itemCatalogo);

    /**
     * Partially updates a itemCatalogo.
     *
     * @param itemCatalogo the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ItemCatalogo> partialUpdate(ItemCatalogo itemCatalogo);

    /**
     * Get all the itemCatalogos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ItemCatalogo> findAll(Pageable pageable);

    /**
     * Get the "id" itemCatalogo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ItemCatalogo> findOne(Long id);

    /**
     * Delete the "id" itemCatalogo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
