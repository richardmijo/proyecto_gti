package ec.ude.uide.proyectogti.domain;

import static ec.ude.uide.proyectogti.domain.CatalogoTestSamples.*;
import static ec.ude.uide.proyectogti.domain.ItemCatalogoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ec.ude.uide.proyectogti.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ItemCatalogoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemCatalogo.class);
        ItemCatalogo itemCatalogo1 = getItemCatalogoSample1();
        ItemCatalogo itemCatalogo2 = new ItemCatalogo();
        assertThat(itemCatalogo1).isNotEqualTo(itemCatalogo2);

        itemCatalogo2.setId(itemCatalogo1.getId());
        assertThat(itemCatalogo1).isEqualTo(itemCatalogo2);

        itemCatalogo2 = getItemCatalogoSample2();
        assertThat(itemCatalogo1).isNotEqualTo(itemCatalogo2);
    }

    @Test
    void catalogoTest() throws Exception {
        ItemCatalogo itemCatalogo = getItemCatalogoRandomSampleGenerator();
        Catalogo catalogoBack = getCatalogoRandomSampleGenerator();

        itemCatalogo.setCatalogo(catalogoBack);
        assertThat(itemCatalogo.getCatalogo()).isEqualTo(catalogoBack);

        itemCatalogo.catalogo(null);
        assertThat(itemCatalogo.getCatalogo()).isNull();
    }
}
