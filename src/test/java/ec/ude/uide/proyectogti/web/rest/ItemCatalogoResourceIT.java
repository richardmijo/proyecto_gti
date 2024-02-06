package ec.ude.uide.proyectogti.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ec.ude.uide.proyectogti.IntegrationTest;
import ec.ude.uide.proyectogti.domain.ItemCatalogo;
import ec.ude.uide.proyectogti.repository.ItemCatalogoRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ItemCatalogoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ItemCatalogoResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_DETALLE = "AAAAAAAAAA";
    private static final String UPDATED_DETALLE = "BBBBBBBBBB";

    private static final String DEFAULT_CATALOG_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CATALOG_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_HABILITADO = false;
    private static final Boolean UPDATED_HABILITADO = true;

    private static final String ENTITY_API_URL = "/api/item-catalogos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ItemCatalogoRepository itemCatalogoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restItemCatalogoMockMvc;

    private ItemCatalogo itemCatalogo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemCatalogo createEntity(EntityManager em) {
        ItemCatalogo itemCatalogo = new ItemCatalogo()
            .nombre(DEFAULT_NOMBRE)
            .codigo(DEFAULT_CODIGO)
            .detalle(DEFAULT_DETALLE)
            .catalogCode(DEFAULT_CATALOG_CODE)
            .habilitado(DEFAULT_HABILITADO);
        return itemCatalogo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemCatalogo createUpdatedEntity(EntityManager em) {
        ItemCatalogo itemCatalogo = new ItemCatalogo()
            .nombre(UPDATED_NOMBRE)
            .codigo(UPDATED_CODIGO)
            .detalle(UPDATED_DETALLE)
            .catalogCode(UPDATED_CATALOG_CODE)
            .habilitado(UPDATED_HABILITADO);
        return itemCatalogo;
    }

    @BeforeEach
    public void initTest() {
        itemCatalogo = createEntity(em);
    }

    @Test
    @Transactional
    void createItemCatalogo() throws Exception {
        int databaseSizeBeforeCreate = itemCatalogoRepository.findAll().size();
        // Create the ItemCatalogo
        restItemCatalogoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itemCatalogo)))
            .andExpect(status().isCreated());

        // Validate the ItemCatalogo in the database
        List<ItemCatalogo> itemCatalogoList = itemCatalogoRepository.findAll();
        assertThat(itemCatalogoList).hasSize(databaseSizeBeforeCreate + 1);
        ItemCatalogo testItemCatalogo = itemCatalogoList.get(itemCatalogoList.size() - 1);
        assertThat(testItemCatalogo.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testItemCatalogo.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testItemCatalogo.getDetalle()).isEqualTo(DEFAULT_DETALLE);
        assertThat(testItemCatalogo.getCatalogCode()).isEqualTo(DEFAULT_CATALOG_CODE);
        assertThat(testItemCatalogo.getHabilitado()).isEqualTo(DEFAULT_HABILITADO);
    }

    @Test
    @Transactional
    void createItemCatalogoWithExistingId() throws Exception {
        // Create the ItemCatalogo with an existing ID
        itemCatalogo.setId(1L);

        int databaseSizeBeforeCreate = itemCatalogoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemCatalogoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itemCatalogo)))
            .andExpect(status().isBadRequest());

        // Validate the ItemCatalogo in the database
        List<ItemCatalogo> itemCatalogoList = itemCatalogoRepository.findAll();
        assertThat(itemCatalogoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemCatalogoRepository.findAll().size();
        // set the field null
        itemCatalogo.setNombre(null);

        // Create the ItemCatalogo, which fails.

        restItemCatalogoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itemCatalogo)))
            .andExpect(status().isBadRequest());

        List<ItemCatalogo> itemCatalogoList = itemCatalogoRepository.findAll();
        assertThat(itemCatalogoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemCatalogoRepository.findAll().size();
        // set the field null
        itemCatalogo.setCodigo(null);

        // Create the ItemCatalogo, which fails.

        restItemCatalogoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itemCatalogo)))
            .andExpect(status().isBadRequest());

        List<ItemCatalogo> itemCatalogoList = itemCatalogoRepository.findAll();
        assertThat(itemCatalogoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllItemCatalogos() throws Exception {
        // Initialize the database
        itemCatalogoRepository.saveAndFlush(itemCatalogo);

        // Get all the itemCatalogoList
        restItemCatalogoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemCatalogo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].detalle").value(hasItem(DEFAULT_DETALLE)))
            .andExpect(jsonPath("$.[*].catalogCode").value(hasItem(DEFAULT_CATALOG_CODE)))
            .andExpect(jsonPath("$.[*].habilitado").value(hasItem(DEFAULT_HABILITADO.booleanValue())));
    }

    @Test
    @Transactional
    void getItemCatalogo() throws Exception {
        // Initialize the database
        itemCatalogoRepository.saveAndFlush(itemCatalogo);

        // Get the itemCatalogo
        restItemCatalogoMockMvc
            .perform(get(ENTITY_API_URL_ID, itemCatalogo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(itemCatalogo.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.detalle").value(DEFAULT_DETALLE))
            .andExpect(jsonPath("$.catalogCode").value(DEFAULT_CATALOG_CODE))
            .andExpect(jsonPath("$.habilitado").value(DEFAULT_HABILITADO.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingItemCatalogo() throws Exception {
        // Get the itemCatalogo
        restItemCatalogoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingItemCatalogo() throws Exception {
        // Initialize the database
        itemCatalogoRepository.saveAndFlush(itemCatalogo);

        int databaseSizeBeforeUpdate = itemCatalogoRepository.findAll().size();

        // Update the itemCatalogo
        ItemCatalogo updatedItemCatalogo = itemCatalogoRepository.findById(itemCatalogo.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedItemCatalogo are not directly saved in db
        em.detach(updatedItemCatalogo);
        updatedItemCatalogo
            .nombre(UPDATED_NOMBRE)
            .codigo(UPDATED_CODIGO)
            .detalle(UPDATED_DETALLE)
            .catalogCode(UPDATED_CATALOG_CODE)
            .habilitado(UPDATED_HABILITADO);

        restItemCatalogoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedItemCatalogo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedItemCatalogo))
            )
            .andExpect(status().isOk());

        // Validate the ItemCatalogo in the database
        List<ItemCatalogo> itemCatalogoList = itemCatalogoRepository.findAll();
        assertThat(itemCatalogoList).hasSize(databaseSizeBeforeUpdate);
        ItemCatalogo testItemCatalogo = itemCatalogoList.get(itemCatalogoList.size() - 1);
        assertThat(testItemCatalogo.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testItemCatalogo.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testItemCatalogo.getDetalle()).isEqualTo(UPDATED_DETALLE);
        assertThat(testItemCatalogo.getCatalogCode()).isEqualTo(UPDATED_CATALOG_CODE);
        assertThat(testItemCatalogo.getHabilitado()).isEqualTo(UPDATED_HABILITADO);
    }

    @Test
    @Transactional
    void putNonExistingItemCatalogo() throws Exception {
        int databaseSizeBeforeUpdate = itemCatalogoRepository.findAll().size();
        itemCatalogo.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemCatalogoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, itemCatalogo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(itemCatalogo))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItemCatalogo in the database
        List<ItemCatalogo> itemCatalogoList = itemCatalogoRepository.findAll();
        assertThat(itemCatalogoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchItemCatalogo() throws Exception {
        int databaseSizeBeforeUpdate = itemCatalogoRepository.findAll().size();
        itemCatalogo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemCatalogoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(itemCatalogo))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItemCatalogo in the database
        List<ItemCatalogo> itemCatalogoList = itemCatalogoRepository.findAll();
        assertThat(itemCatalogoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamItemCatalogo() throws Exception {
        int databaseSizeBeforeUpdate = itemCatalogoRepository.findAll().size();
        itemCatalogo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemCatalogoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itemCatalogo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ItemCatalogo in the database
        List<ItemCatalogo> itemCatalogoList = itemCatalogoRepository.findAll();
        assertThat(itemCatalogoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateItemCatalogoWithPatch() throws Exception {
        // Initialize the database
        itemCatalogoRepository.saveAndFlush(itemCatalogo);

        int databaseSizeBeforeUpdate = itemCatalogoRepository.findAll().size();

        // Update the itemCatalogo using partial update
        ItemCatalogo partialUpdatedItemCatalogo = new ItemCatalogo();
        partialUpdatedItemCatalogo.setId(itemCatalogo.getId());

        partialUpdatedItemCatalogo.nombre(UPDATED_NOMBRE).codigo(UPDATED_CODIGO).detalle(UPDATED_DETALLE).catalogCode(UPDATED_CATALOG_CODE);

        restItemCatalogoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedItemCatalogo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedItemCatalogo))
            )
            .andExpect(status().isOk());

        // Validate the ItemCatalogo in the database
        List<ItemCatalogo> itemCatalogoList = itemCatalogoRepository.findAll();
        assertThat(itemCatalogoList).hasSize(databaseSizeBeforeUpdate);
        ItemCatalogo testItemCatalogo = itemCatalogoList.get(itemCatalogoList.size() - 1);
        assertThat(testItemCatalogo.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testItemCatalogo.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testItemCatalogo.getDetalle()).isEqualTo(UPDATED_DETALLE);
        assertThat(testItemCatalogo.getCatalogCode()).isEqualTo(UPDATED_CATALOG_CODE);
        assertThat(testItemCatalogo.getHabilitado()).isEqualTo(DEFAULT_HABILITADO);
    }

    @Test
    @Transactional
    void fullUpdateItemCatalogoWithPatch() throws Exception {
        // Initialize the database
        itemCatalogoRepository.saveAndFlush(itemCatalogo);

        int databaseSizeBeforeUpdate = itemCatalogoRepository.findAll().size();

        // Update the itemCatalogo using partial update
        ItemCatalogo partialUpdatedItemCatalogo = new ItemCatalogo();
        partialUpdatedItemCatalogo.setId(itemCatalogo.getId());

        partialUpdatedItemCatalogo
            .nombre(UPDATED_NOMBRE)
            .codigo(UPDATED_CODIGO)
            .detalle(UPDATED_DETALLE)
            .catalogCode(UPDATED_CATALOG_CODE)
            .habilitado(UPDATED_HABILITADO);

        restItemCatalogoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedItemCatalogo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedItemCatalogo))
            )
            .andExpect(status().isOk());

        // Validate the ItemCatalogo in the database
        List<ItemCatalogo> itemCatalogoList = itemCatalogoRepository.findAll();
        assertThat(itemCatalogoList).hasSize(databaseSizeBeforeUpdate);
        ItemCatalogo testItemCatalogo = itemCatalogoList.get(itemCatalogoList.size() - 1);
        assertThat(testItemCatalogo.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testItemCatalogo.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testItemCatalogo.getDetalle()).isEqualTo(UPDATED_DETALLE);
        assertThat(testItemCatalogo.getCatalogCode()).isEqualTo(UPDATED_CATALOG_CODE);
        assertThat(testItemCatalogo.getHabilitado()).isEqualTo(UPDATED_HABILITADO);
    }

    @Test
    @Transactional
    void patchNonExistingItemCatalogo() throws Exception {
        int databaseSizeBeforeUpdate = itemCatalogoRepository.findAll().size();
        itemCatalogo.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemCatalogoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, itemCatalogo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(itemCatalogo))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItemCatalogo in the database
        List<ItemCatalogo> itemCatalogoList = itemCatalogoRepository.findAll();
        assertThat(itemCatalogoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchItemCatalogo() throws Exception {
        int databaseSizeBeforeUpdate = itemCatalogoRepository.findAll().size();
        itemCatalogo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemCatalogoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(itemCatalogo))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItemCatalogo in the database
        List<ItemCatalogo> itemCatalogoList = itemCatalogoRepository.findAll();
        assertThat(itemCatalogoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamItemCatalogo() throws Exception {
        int databaseSizeBeforeUpdate = itemCatalogoRepository.findAll().size();
        itemCatalogo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemCatalogoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(itemCatalogo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ItemCatalogo in the database
        List<ItemCatalogo> itemCatalogoList = itemCatalogoRepository.findAll();
        assertThat(itemCatalogoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteItemCatalogo() throws Exception {
        // Initialize the database
        itemCatalogoRepository.saveAndFlush(itemCatalogo);

        int databaseSizeBeforeDelete = itemCatalogoRepository.findAll().size();

        // Delete the itemCatalogo
        restItemCatalogoMockMvc
            .perform(delete(ENTITY_API_URL_ID, itemCatalogo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ItemCatalogo> itemCatalogoList = itemCatalogoRepository.findAll();
        assertThat(itemCatalogoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
