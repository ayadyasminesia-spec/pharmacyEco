package com.ayadyasmine.pharmacyecom.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ProductVariantTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static ProductVariant getProductVariantSample1() {
        return new ProductVariant().id(1L).label("label1").stock(1).sku("sku1");
    }

    public static ProductVariant getProductVariantSample2() {
        return new ProductVariant().id(2L).label("label2").stock(2).sku("sku2");
    }

    public static ProductVariant getProductVariantRandomSampleGenerator() {
        return new ProductVariant()
            .id(longCount.incrementAndGet())
            .label(UUID.randomUUID().toString())
            .stock(intCount.incrementAndGet())
            .sku(UUID.randomUUID().toString());
    }
}
