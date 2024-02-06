package ec.ude.uide.proyectogti.web.rest;

import ec.ude.uide.proyectogti.domain.ItemCatalogo;
import ec.ude.uide.proyectogti.repository.ItemCatalogoRepository;
import ec.ude.uide.proyectogti.service.ItemCatalogoService;
import ec.ude.uide.proyectogti.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ec.ude.uide.proyectogti.domain.ItemCatalogo}.
 */
@RestController
@RequestMapping("/api/item-catalogos")
public class ItemCatalogoResource {

    private final Logger log = LoggerFactory.getLogger(ItemCatalogoResource.class);

    private static final String ENTITY_NAME = "itemCatalogo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ItemCatalogoService itemCatalogoService;

    private final ItemCatalogoRepository itemCatalogoRepository;

    public ItemCatalogoResource(ItemCatalogoService itemCatalogoService, ItemCatalogoRepository itemCatalogoRepository) {
        this.itemCatalogoService = itemCatalogoService;
        this.itemCatalogoRepository = itemCatalogoRepository;
    }

    /**
     * {@code POST  /item-catalogos} : Create a new itemCatalogo.
     *
     * @param itemCatalogo the itemCatalogo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new itemCatalogo, or with status {@code 400 (Bad Request)} if the itemCatalogo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ItemCatalogo> createItemCatalogo(@Valid @RequestBody ItemCatalogo itemCatalogo) throws URISyntaxException {
        log.debug("REST request to save ItemCatalogo : {}", itemCatalogo);
        if (itemCatalogo.getId() != null) {
            throw new BadRequestAlertException("A new itemCatalogo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemCatalogo result = itemCatalogoService.save(itemCatalogo);
        return ResponseEntity
            .created(new URI("/api/item-catalogos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /item-catalogos/:id} : Updates an existing itemCatalogo.
     *
     * @param id the id of the itemCatalogo to save.
     * @param itemCatalogo the itemCatalogo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemCatalogo,
     * or with status {@code 400 (Bad Request)} if the itemCatalogo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the itemCatalogo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ItemCatalogo> updateItemCatalogo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ItemCatalogo itemCatalogo
    ) throws URISyntaxException {
        log.debug("REST request to update ItemCatalogo : {}, {}", id, itemCatalogo);
        if (itemCatalogo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, itemCatalogo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!itemCatalogoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ItemCatalogo result = itemCatalogoService.update(itemCatalogo);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, itemCatalogo.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /item-catalogos/:id} : Partial updates given fields of an existing itemCatalogo, field will ignore if it is null
     *
     * @param id the id of the itemCatalogo to save.
     * @param itemCatalogo the itemCatalogo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemCatalogo,
     * or with status {@code 400 (Bad Request)} if the itemCatalogo is not valid,
     * or with status {@code 404 (Not Found)} if the itemCatalogo is not found,
     * or with status {@code 500 (Internal Server Error)} if the itemCatalogo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ItemCatalogo> partialUpdateItemCatalogo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ItemCatalogo itemCatalogo
    ) throws URISyntaxException {
        log.debug("REST request to partial update ItemCatalogo partially : {}, {}", id, itemCatalogo);
        if (itemCatalogo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, itemCatalogo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!itemCatalogoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ItemCatalogo> result = itemCatalogoService.partialUpdate(itemCatalogo);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, itemCatalogo.getId().toString())
        );
    }

    /**
     * {@code GET  /item-catalogos} : get all the itemCatalogos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of itemCatalogos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ItemCatalogo>> getAllItemCatalogos(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ItemCatalogos");
        Page<ItemCatalogo> page = itemCatalogoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /item-catalogos/:id} : get the "id" itemCatalogo.
     *
     * @param id the id of the itemCatalogo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the itemCatalogo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ItemCatalogo> getItemCatalogo(@PathVariable("id") Long id) {
        log.debug("REST request to get ItemCatalogo : {}", id);
        Optional<ItemCatalogo> itemCatalogo = itemCatalogoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(itemCatalogo);
    }

    /**
     * {@code DELETE  /item-catalogos/:id} : delete the "id" itemCatalogo.
     *
     * @param id the id of the itemCatalogo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItemCatalogo(@PathVariable("id") Long id) {
        log.debug("REST request to delete ItemCatalogo : {}", id);
        itemCatalogoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
