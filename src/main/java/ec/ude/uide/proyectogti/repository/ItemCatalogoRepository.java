package ec.ude.uide.proyectogti.repository;

import ec.ude.uide.proyectogti.domain.ItemCatalogo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ItemCatalogo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemCatalogoRepository extends JpaRepository<ItemCatalogo, Long> {}
