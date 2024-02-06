package ec.ude.uide.proyectogti.repository;

import ec.ude.uide.proyectogti.domain.Persona;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Persona entity.
 */
@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
    default Optional<Persona> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Persona> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Persona> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select persona from Persona persona left join fetch persona.estadoCivil left join fetch persona.tipoIdentificacion left join fetch persona.paisProcedencia left join fetch persona.nivelEducativo",
        countQuery = "select count(persona) from Persona persona"
    )
    Page<Persona> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select persona from Persona persona left join fetch persona.estadoCivil left join fetch persona.tipoIdentificacion left join fetch persona.paisProcedencia left join fetch persona.nivelEducativo"
    )
    List<Persona> findAllWithToOneRelationships();

    @Query(
        "select persona from Persona persona left join fetch persona.estadoCivil left join fetch persona.tipoIdentificacion left join fetch persona.paisProcedencia left join fetch persona.nivelEducativo where persona.id =:id"
    )
    Optional<Persona> findOneWithToOneRelationships(@Param("id") Long id);
}
