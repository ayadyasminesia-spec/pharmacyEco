package com.ayadyasmine.pharmacyecom.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ProductTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Product getProductSample1() {
        return new Product()
            .id(1L)
            .name("name1")
            .slug("slug1")
            .stock(1)
            .sku("sku1")
            .metaTitle("metaTitle1")
            .metaDescription("metaDescription1")
            .viewsCount(1L);
    }

    public static Product getProductSample2() {
        return new Product()
            .id(2L)
            .name("name2")
            .slug("slug2")
            .stock(2)
            .sku("sku2")
            .metaTitle("metaTitle2")
            .metaDescription("metaDescription2")
            .viewsCount(2L);
    }

    public static Product getProductRandomSampleGenerator() {
        return new Product()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .slug(UUID.randomUUID().toString())
            .stock(intCount.incrementAndGet())
            .sku(UUID.randomUUID().toString())
            .metaTitle(UUID.randomUUID().toString())
            .metaDescription(UUID.randomUUID().toString())
            .viewsCount(longCount.incrementAndGet());
    }
}
