package ec.ude.uide.proyectogti.service.impl;

import ec.ude.uide.proyectogti.domain.Persona;
import ec.ude.uide.proyectogti.repository.PersonaRepository;
import ec.ude.uide.proyectogti.service.PersonaService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ec.ude.uide.proyectogti.domain.Persona}.
 */
@Service
@Transactional
public class PersonaServiceImpl implements PersonaService {

    private final Logger log = LoggerFactory.getLogger(PersonaServiceImpl.class);

    private final PersonaRepository personaRepository;

    public PersonaServiceImpl(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    @Override
    public Persona save(Persona persona) {
        log.debug("Request to save Persona : {}", persona);
        return personaRepository.save(persona);
    }

    @Override
    public Persona update(Persona persona) {
        log.debug("Request to update Persona : {}", persona);
        return personaRepository.save(persona);
    }

    @Override
    public Optional<Persona> partialUpdate(Persona persona) {
        log.debug("Request to partially update Persona : {}", persona);

        return personaRepository
            .findById(persona.getId())
            .map(existingPersona -> {
                if (persona.getNumeroIdentificacion() != null) {
                    existingPersona.setNumeroIdentificacion(persona.getNumeroIdentificacion());
                }
                if (persona.getApellidos() != null) {
                    existingPersona.setApellidos(persona.getApellidos());
                }
                if (persona.getNombres() != null) {
                    existingPersona.setNombres(persona.getNombres());
                }
                if (persona.getNombre() != null) {
                    existingPersona.setNombre(persona.getNombre());
                }
                if (persona.getDireccion() != null) {
                    existingPersona.setDireccion(persona.getDireccion());
                }
                if (persona.getTelefonoFijo() != null) {
                    existingPersona.setTelefonoFijo(persona.getTelefonoFijo());
                }
                if (persona.getTelefonoMovil() != null) {
                    existingPersona.setTelefonoMovil(persona.getTelefonoMovil());
                }
                if (persona.getFechaNacimiento() != null) {
                    existingPersona.setFechaNacimiento(persona.getFechaNacimiento());
                }

                return existingPersona;
            })
            .map(personaRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Persona> findAll(Pageable pageable) {
        log.debug("Request to get all Personas");
        return personaRepository.findAll(pageable);
    }

    public Page<Persona> findAllWithEagerRelationships(Pageable pageable) {
        return personaRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Persona> findOne(Long id) {
        log.debug("Request to get Persona : {}", id);
        return personaRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Persona : {}", id);
        personaRepository.deleteById(id);
    }
}
