package ec.ude.uide.proyectogti.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ec.ude.uide.proyectogti.IntegrationTest;
import ec.ude.uide.proyectogti.domain.ItemCatalogo;
import ec.ude.uide.proyectogti.domain.Persona;
import ec.ude.uide.proyectogti.repository.PersonaRepository;
import ec.ude.uide.proyectogti.service.PersonaService;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PersonaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PersonaResourceIT {

    private static final String DEFAULT_NUMERO_IDENTIFICACION = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_IDENTIFICACION = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDOS = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDOS = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRES = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRES = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO_FIJO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO_FIJO = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO_MOVIL = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO_MOVIL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_NACIMIENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_NACIMIENTO = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/personas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PersonaRepository personaRepository;

    @Mock
    private PersonaRepository personaRepositoryMock;

    @Mock
    private PersonaService personaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPersonaMockMvc;

    private Persona persona;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Persona createEntity(EntityManager em) {
        Persona persona = new Persona()
            .numeroIdentificacion(DEFAULT_NUMERO_IDENTIFICACION)
            .apellidos(DEFAULT_APELLIDOS)
            .nombres(DEFAULT_NOMBRES)
            .nombre(DEFAULT_NOMBRE)
            .direccion(DEFAULT_DIRECCION)
            .telefonoFijo(DEFAULT_TELEFONO_FIJO)
            .telefonoMovil(DEFAULT_TELEFONO_MOVIL)
            .fechaNacimiento(DEFAULT_FECHA_NACIMIENTO);
        // Add required entity
        ItemCatalogo itemCatalogo;
        if (TestUtil.findAll(em, ItemCatalogo.class).isEmpty()) {
            itemCatalogo = ItemCatalogoResourceIT.createEntity(em);
            em.persist(itemCatalogo);
            em.flush();
        } else {
            itemCatalogo = TestUtil.findAll(em, ItemCatalogo.class).get(0);
        }
        persona.setEstadoCivil(itemCatalogo);
        // Add required entity
        persona.setTipoIdentificacion(itemCatalogo);
        // Add required entity
        persona.setPaisProcedencia(itemCatalogo);
        // Add required entity
        persona.setNivelEducativo(itemCatalogo);
        return persona;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Persona createUpdatedEntity(EntityManager em) {
        Persona persona = new Persona()
            .numeroIdentificacion(UPDATED_NUMERO_IDENTIFICACION)
            .apellidos(UPDATED_APELLIDOS)
            .nombres(UPDATED_NOMBRES)
            .nombre(UPDATED_NOMBRE)
            .direccion(UPDATED_DIRECCION)
            .telefonoFijo(UPDATED_TELEFONO_FIJO)
            .telefonoMovil(UPDATED_TELEFONO_MOVIL)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO);
        // Add required entity
        ItemCatalogo itemCatalogo;
        if (TestUtil.findAll(em, ItemCatalogo.class).isEmpty()) {
            itemCatalogo = ItemCatalogoResourceIT.createUpdatedEntity(em);
            em.persist(itemCatalogo);
            em.flush();
        } else {
            itemCatalogo = TestUtil.findAll(em, ItemCatalogo.class).get(0);
        }
        persona.setEstadoCivil(itemCatalogo);
        // Add required entity
        persona.setTipoIdentificacion(itemCatalogo);
        // Add required entity
        persona.setPaisProcedencia(itemCatalogo);
        // Add required entity
        persona.setNivelEducativo(itemCatalogo);
        return persona;
    }

    @BeforeEach
    public void initTest() {
        persona = createEntity(em);
    }

    @Test
    @Transactional
    void createPersona() throws Exception {
        int databaseSizeBeforeCreate = personaRepository.findAll().size();
        // Create the Persona
        restPersonaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(persona)))
            .andExpect(status().isCreated());

        // Validate the Persona in the database
        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeCreate + 1);
        Persona testPersona = personaList.get(personaList.size() - 1);
        assertThat(testPersona.getNumeroIdentificacion()).isEqualTo(DEFAULT_NUMERO_IDENTIFICACION);
        assertThat(testPersona.getApellidos()).isEqualTo(DEFAULT_APELLIDOS);
        assertThat(testPersona.getNombres()).isEqualTo(DEFAULT_NOMBRES);
        assertThat(testPersona.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testPersona.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testPersona.getTelefonoFijo()).isEqualTo(DEFAULT_TELEFONO_FIJO);
        assertThat(testPersona.getTelefonoMovil()).isEqualTo(DEFAULT_TELEFONO_MOVIL);
        assertThat(testPersona.getFechaNacimiento()).isEqualTo(DEFAULT_FECHA_NACIMIENTO);
    }

    @Test
    @Transactional
    void createPersonaWithExistingId() throws Exception {
        // Create the Persona with an existing ID
        persona.setId(1L);

        int databaseSizeBeforeCreate = personaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(persona)))
            .andExpect(status().isBadRequest());

        // Validate the Persona in the database
        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNumeroIdentificacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = personaRepository.findAll().size();
        // set the field null
        persona.setNumeroIdentificacion(null);

        // Create the Persona, which fails.

        restPersonaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(persona)))
            .andExpect(status().isBadRequest());

        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPersonas() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList
        restPersonaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(persona.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroIdentificacion").value(hasItem(DEFAULT_NUMERO_IDENTIFICACION)))
            .andExpect(jsonPath("$.[*].apellidos").value(hasItem(DEFAULT_APELLIDOS)))
            .andExpect(jsonPath("$.[*].nombres").value(hasItem(DEFAULT_NOMBRES)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
            .andExpect(jsonPath("$.[*].telefonoFijo").value(hasItem(DEFAULT_TELEFONO_FIJO)))
            .andExpect(jsonPath("$.[*].telefonoMovil").value(hasItem(DEFAULT_TELEFONO_MOVIL)))
            .andExpect(jsonPath("$.[*].fechaNacimiento").value(hasItem(DEFAULT_FECHA_NACIMIENTO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPersonasWithEagerRelationshipsIsEnabled() throws Exception {
        when(personaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPersonaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(personaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPersonasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(personaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPersonaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(personaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getPersona() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get the persona
        restPersonaMockMvc
            .perform(get(ENTITY_API_URL_ID, persona.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(persona.getId().intValue()))
            .andExpect(jsonPath("$.numeroIdentificacion").value(DEFAULT_NUMERO_IDENTIFICACION))
            .andExpect(jsonPath("$.apellidos").value(DEFAULT_APELLIDOS))
            .andExpect(jsonPath("$.nombres").value(DEFAULT_NOMBRES))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION))
            .andExpect(jsonPath("$.telefonoFijo").value(DEFAULT_TELEFONO_FIJO))
            .andExpect(jsonPath("$.telefonoMovil").value(DEFAULT_TELEFONO_MOVIL))
            .andExpect(jsonPath("$.fechaNacimiento").value(DEFAULT_FECHA_NACIMIENTO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPersona() throws Exception {
        // Get the persona
        restPersonaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPersona() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        int databaseSizeBeforeUpdate = personaRepository.findAll().size();

        // Update the persona
        Persona updatedPersona = personaRepository.findById(persona.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPersona are not directly saved in db
        em.detach(updatedPersona);
        updatedPersona
            .numeroIdentificacion(UPDATED_NUMERO_IDENTIFICACION)
            .apellidos(UPDATED_APELLIDOS)
            .nombres(UPDATED_NOMBRES)
            .nombre(UPDATED_NOMBRE)
            .direccion(UPDATED_DIRECCION)
            .telefonoFijo(UPDATED_TELEFONO_FIJO)
            .telefonoMovil(UPDATED_TELEFONO_MOVIL)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO);

        restPersonaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPersona.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPersona))
            )
            .andExpect(status().isOk());

        // Validate the Persona in the database
        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeUpdate);
        Persona testPersona = personaList.get(personaList.size() - 1);
        assertThat(testPersona.getNumeroIdentificacion()).isEqualTo(UPDATED_NUMERO_IDENTIFICACION);
        assertThat(testPersona.getApellidos()).isEqualTo(UPDATED_APELLIDOS);
        assertThat(testPersona.getNombres()).isEqualTo(UPDATED_NOMBRES);
        assertThat(testPersona.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPersona.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testPersona.getTelefonoFijo()).isEqualTo(UPDATED_TELEFONO_FIJO);
        assertThat(testPersona.getTelefonoMovil()).isEqualTo(UPDATED_TELEFONO_MOVIL);
        assertThat(testPersona.getFechaNacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
    }

    @Test
    @Transactional
    void putNonExistingPersona() throws Exception {
        int databaseSizeBeforeUpdate = personaRepository.findAll().size();
        persona.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, persona.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(persona))
            )
            .andExpect(status().isBadRequest());

        // Validate the Persona in the database
        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPersona() throws Exception {
        int databaseSizeBeforeUpdate = personaRepository.findAll().size();
        persona.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(persona))
            )
            .andExpect(status().isBadRequest());

        // Validate the Persona in the database
        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPersona() throws Exception {
        int databaseSizeBeforeUpdate = personaRepository.findAll().size();
        persona.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(persona)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Persona in the database
        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePersonaWithPatch() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        int databaseSizeBeforeUpdate = personaRepository.findAll().size();

        // Update the persona using partial update
        Persona partialUpdatedPersona = new Persona();
        partialUpdatedPersona.setId(persona.getId());

        partialUpdatedPersona
            .apellidos(UPDATED_APELLIDOS)
            .nombres(UPDATED_NOMBRES)
            .nombre(UPDATED_NOMBRE)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO);

        restPersonaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersona.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersona))
            )
            .andExpect(status().isOk());

        // Validate the Persona in the database
        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeUpdate);
        Persona testPersona = personaList.get(personaList.size() - 1);
        assertThat(testPersona.getNumeroIdentificacion()).isEqualTo(DEFAULT_NUMERO_IDENTIFICACION);
        assertThat(testPersona.getApellidos()).isEqualTo(UPDATED_APELLIDOS);
        assertThat(testPersona.getNombres()).isEqualTo(UPDATED_NOMBRES);
        assertThat(testPersona.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPersona.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testPersona.getTelefonoFijo()).isEqualTo(DEFAULT_TELEFONO_FIJO);
        assertThat(testPersona.getTelefonoMovil()).isEqualTo(DEFAULT_TELEFONO_MOVIL);
        assertThat(testPersona.getFechaNacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
    }

    @Test
    @Transactional
    void fullUpdatePersonaWithPatch() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        int databaseSizeBeforeUpdate = personaRepository.findAll().size();

        // Update the persona using partial update
        Persona partialUpdatedPersona = new Persona();
        partialUpdatedPersona.setId(persona.getId());

        partialUpdatedPersona
            .numeroIdentificacion(UPDATED_NUMERO_IDENTIFICACION)
            .apellidos(UPDATED_APELLIDOS)
            .nombres(UPDATED_NOMBRES)
            .nombre(UPDATED_NOMBRE)
            .direccion(UPDATED_DIRECCION)
            .telefonoFijo(UPDATED_TELEFONO_FIJO)
            .telefonoMovil(UPDATED_TELEFONO_MOVIL)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO);

        restPersonaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersona.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersona))
            )
            .andExpect(status().isOk());

        // Validate the Persona in the database
        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeUpdate);
        Persona testPersona = personaList.get(personaList.size() - 1);
        assertThat(testPersona.getNumeroIdentificacion()).isEqualTo(UPDATED_NUMERO_IDENTIFICACION);
        assertThat(testPersona.getApellidos()).isEqualTo(UPDATED_APELLIDOS);
        assertThat(testPersona.getNombres()).isEqualTo(UPDATED_NOMBRES);
        assertThat(testPersona.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPersona.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testPersona.getTelefonoFijo()).isEqualTo(UPDATED_TELEFONO_FIJO);
        assertThat(testPersona.getTelefonoMovil()).isEqualTo(UPDATED_TELEFONO_MOVIL);
        assertThat(testPersona.getFechaNacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
    }

    @Test
    @Transactional
    void patchNonExistingPersona() throws Exception {
        int databaseSizeBeforeUpdate = personaRepository.findAll().size();
        persona.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, persona.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(persona))
            )
            .andExpect(status().isBadRequest());

        // Validate the Persona in the database
        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPersona() throws Exception {
        int databaseSizeBeforeUpdate = personaRepository.findAll().size();
        persona.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(persona))
            )
            .andExpect(status().isBadRequest());

        // Validate the Persona in the database
        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPersona() throws Exception {
        int databaseSizeBeforeUpdate = personaRepository.findAll().size();
        persona.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(persona)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Persona in the database
        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePersona() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        int databaseSizeBeforeDelete = personaRepository.findAll().size();

        // Delete the persona
        restPersonaMockMvc
            .perform(delete(ENTITY_API_URL_ID, persona.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
