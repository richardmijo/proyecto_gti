package ec.ude.uide.proyectogti.domain;

import static ec.ude.uide.proyectogti.domain.CatalogoTestSamples.*;
import static ec.ude.uide.proyectogti.domain.ItemCatalogoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ec.ude.uide.proyectogti.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CatalogoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Catalogo.class);
        Catalogo catalogo1 = getCatalogoSample1();
        Catalogo catalogo2 = new Catalogo();
        assertThat(catalogo1).isNotEqualTo(catalogo2);

        catalogo2.setId(catalogo1.getId());
        assertThat(catalogo1).isEqualTo(catalogo2);

        catalogo2 = getCatalogoSample2();
        assertThat(catalogo1).isNotEqualTo(catalogo2);
    }

    @Test
    void itemsCatalogoTest() throws Exception {
        Catalogo catalogo = getCatalogoRandomSampleGenerator();
        ItemCatalogo itemCatalogoBack = getItemCatalogoRandomSampleGenerator();

        catalogo.addItemsCatalogo(itemCatalogoBack);
        assertThat(catalogo.getItemsCatalogos()).containsOnly(itemCatalogoBack);
        assertThat(itemCatalogoBack.getCatalogo()).isEqualTo(catalogo);

        catalogo.removeItemsCatalogo(itemCatalogoBack);
        assertThat(catalogo.getItemsCatalogos()).doesNotContain(itemCatalogoBack);
        assertThat(itemCatalogoBack.getCatalogo()).isNull();

        catalogo.itemsCatalogos(new HashSet<>(Set.of(itemCatalogoBack)));
        assertThat(catalogo.getItemsCatalogos()).containsOnly(itemCatalogoBack);
        assertThat(itemCatalogoBack.getCatalogo()).isEqualTo(catalogo);

        catalogo.setItemsCatalogos(new HashSet<>());
        assertThat(catalogo.getItemsCatalogos()).doesNotContain(itemCatalogoBack);
        assertThat(itemCatalogoBack.getCatalogo()).isNull();
    }
}
