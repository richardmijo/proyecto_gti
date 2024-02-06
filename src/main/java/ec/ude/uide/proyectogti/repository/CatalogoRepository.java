package ec.ude.uide.proyectogti.repository;

import ec.ude.uide.proyectogti.domain.Catalogo;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Catalogo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CatalogoRepository extends JpaRepository<Catalogo, Long> {
    @Query("select catalogo from Catalogo catalogo join fetch catalogo.itemsCatalogos where catalogo.codigo = ?1 ")
    Catalogo obtenerCatalogPorCodigo(String codigo);

    @Query("select catalogo from Catalogo catalogo")
    List<Catalogo> obtenerTodos();
}
