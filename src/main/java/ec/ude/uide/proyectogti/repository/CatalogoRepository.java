package ec.ude.uide.proyectogti.repository;

import ec.ude.uide.proyectogti.domain.Catalogo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Catalogo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CatalogoRepository extends JpaRepository<Catalogo, Long> {}
