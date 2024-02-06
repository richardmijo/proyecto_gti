package ec.ude.uide.proyectogti.service.impl;

import ec.ude.uide.proyectogti.domain.ItemCatalogo;
import ec.ude.uide.proyectogti.repository.ItemCatalogoRepository;
import ec.ude.uide.proyectogti.service.ItemCatalogoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ec.ude.uide.proyectogti.domain.ItemCatalogo}.
 */
@Service
@Transactional
public class ItemCatalogoServiceImpl implements ItemCatalogoService {

    private final Logger log = LoggerFactory.getLogger(ItemCatalogoServiceImpl.class);

    private final ItemCatalogoRepository itemCatalogoRepository;

    public ItemCatalogoServiceImpl(ItemCatalogoRepository itemCatalogoRepository) {
        this.itemCatalogoRepository = itemCatalogoRepository;
    }

    @Override
    public ItemCatalogo save(ItemCatalogo itemCatalogo) {
        log.debug("Request to save ItemCatalogo : {}", itemCatalogo);
        return itemCatalogoRepository.save(itemCatalogo);
    }

    @Override
    public ItemCatalogo update(ItemCatalogo itemCatalogo) {
        log.debug("Request to update ItemCatalogo : {}", itemCatalogo);
        return itemCatalogoRepository.save(itemCatalogo);
    }

    @Override
    public Optional<ItemCatalogo> partialUpdate(ItemCatalogo itemCatalogo) {
        log.debug("Request to partially update ItemCatalogo : {}", itemCatalogo);

        return itemCatalogoRepository
            .findById(itemCatalogo.getId())
            .map(existingItemCatalogo -> {
                if (itemCatalogo.getNombre() != null) {
                    existingItemCatalogo.setNombre(itemCatalogo.getNombre());
                }
                if (itemCatalogo.getCodigo() != null) {
                    existingItemCatalogo.setCodigo(itemCatalogo.getCodigo());
                }
                if (itemCatalogo.getDetalle() != null) {
                    existingItemCatalogo.setDetalle(itemCatalogo.getDetalle());
                }
                if (itemCatalogo.getCatalogCode() != null) {
                    existingItemCatalogo.setCatalogCode(itemCatalogo.getCatalogCode());
                }
                if (itemCatalogo.getHabilitado() != null) {
                    existingItemCatalogo.setHabilitado(itemCatalogo.getHabilitado());
                }

                return existingItemCatalogo;
            })
            .map(itemCatalogoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ItemCatalogo> findAll(Pageable pageable) {
        log.debug("Request to get all ItemCatalogos");
        return itemCatalogoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ItemCatalogo> findOne(Long id) {
        log.debug("Request to get ItemCatalogo : {}", id);
        return itemCatalogoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ItemCatalogo : {}", id);
        itemCatalogoRepository.deleteById(id);
    }
}
