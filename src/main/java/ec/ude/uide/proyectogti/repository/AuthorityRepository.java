package ec.ude.uide.proyectogti.repository;

import ec.ude.uide.proyectogti.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
