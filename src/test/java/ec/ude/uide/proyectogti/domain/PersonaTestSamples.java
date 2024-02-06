package ec.ude.uide.proyectogti.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PersonaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Persona getPersonaSample1() {
        return new Persona()
            .id(1L)
            .numeroIdentificacion("numeroIdentificacion1")
            .apellidos("apellidos1")
            .nombres("nombres1")
            .nombre("nombre1")
            .direccion("direccion1")
            .telefonoFijo("telefonoFijo1")
            .telefonoMovil("telefonoMovil1");
    }

    public static Persona getPersonaSample2() {
        return new Persona()
            .id(2L)
            .numeroIdentificacion("numeroIdentificacion2")
            .apellidos("apellidos2")
            .nombres("nombres2")
            .nombre("nombre2")
            .direccion("direccion2")
            .telefonoFijo("telefonoFijo2")
            .telefonoMovil("telefonoMovil2");
    }

    public static Persona getPersonaRandomSampleGenerator() {
        return new Persona()
            .id(longCount.incrementAndGet())
            .numeroIdentificacion(UUID.randomUUID().toString())
            .apellidos(UUID.randomUUID().toString())
            .nombres(UUID.randomUUID().toString())
            .nombre(UUID.randomUUID().toString())
            .direccion(UUID.randomUUID().toString())
            .telefonoFijo(UUID.randomUUID().toString())
            .telefonoMovil(UUID.randomUUID().toString());
    }
}
