package ec.ude.uide.proyectogti.domain;

import static ec.ude.uide.proyectogti.domain.ItemCatalogoTestSamples.*;
import static ec.ude.uide.proyectogti.domain.PersonaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ec.ude.uide.proyectogti.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PersonaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Persona.class);
        Persona persona1 = getPersonaSample1();
        Persona persona2 = new Persona();
        assertThat(persona1).isNotEqualTo(persona2);

        persona2.setId(persona1.getId());
        assertThat(persona1).isEqualTo(persona2);

        persona2 = getPersonaSample2();
        assertThat(persona1).isNotEqualTo(persona2);
    }

    @Test
    void estadoCivilTest() throws Exception {
        Persona persona = getPersonaRandomSampleGenerator();
        ItemCatalogo itemCatalogoBack = getItemCatalogoRandomSampleGenerator();

        persona.setEstadoCivil(itemCatalogoBack);
        assertThat(persona.getEstadoCivil()).isEqualTo(itemCatalogoBack);

        persona.estadoCivil(null);
        assertThat(persona.getEstadoCivil()).isNull();
    }

    @Test
    void tipoIdentificacionTest() throws Exception {
        Persona persona = getPersonaRandomSampleGenerator();
        ItemCatalogo itemCatalogoBack = getItemCatalogoRandomSampleGenerator();

        persona.setTipoIdentificacion(itemCatalogoBack);
        assertThat(persona.getTipoIdentificacion()).isEqualTo(itemCatalogoBack);

        persona.tipoIdentificacion(null);
        assertThat(persona.getTipoIdentificacion()).isNull();
    }

    @Test
    void paisProcedenciaTest() throws Exception {
        Persona persona = getPersonaRandomSampleGenerator();
        ItemCatalogo itemCatalogoBack = getItemCatalogoRandomSampleGenerator();

        persona.setPaisProcedencia(itemCatalogoBack);
        assertThat(persona.getPaisProcedencia()).isEqualTo(itemCatalogoBack);

        persona.paisProcedencia(null);
        assertThat(persona.getPaisProcedencia()).isNull();
    }

    @Test
    void nivelEducativoTest() throws Exception {
        Persona persona = getPersonaRandomSampleGenerator();
        ItemCatalogo itemCatalogoBack = getItemCatalogoRandomSampleGenerator();

        persona.setNivelEducativo(itemCatalogoBack);
        assertThat(persona.getNivelEducativo()).isEqualTo(itemCatalogoBack);

        persona.nivelEducativo(null);
        assertThat(persona.getNivelEducativo()).isNull();
    }
}
