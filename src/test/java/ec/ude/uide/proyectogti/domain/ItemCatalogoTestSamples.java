package ec.ude.uide.proyectogti.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ItemCatalogoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ItemCatalogo getItemCatalogoSample1() {
        return new ItemCatalogo().id(1L).nombre("nombre1").codigo("codigo1").detalle("detalle1").catalogCode("catalogCode1");
    }

    public static ItemCatalogo getItemCatalogoSample2() {
        return new ItemCatalogo().id(2L).nombre("nombre2").codigo("codigo2").detalle("detalle2").catalogCode("catalogCode2");
    }

    public static ItemCatalogo getItemCatalogoRandomSampleGenerator() {
        return new ItemCatalogo()
            .id(longCount.incrementAndGet())
            .nombre(UUID.randomUUID().toString())
            .codigo(UUID.randomUUID().toString())
            .detalle(UUID.randomUUID().toString())
            .catalogCode(UUID.randomUUID().toString());
    }
}
