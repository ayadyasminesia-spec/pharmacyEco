package com.ayadyasmine.pharmacyecom.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ProductImageTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static ProductImage getProductImageSample1() {
        return new ProductImage().id(1L).imageUri("imageUri1").position(1);
    }

    public static ProductImage getProductImageSample2() {
        return new ProductImage().id(2L).imageUri("imageUri2").position(2);
    }

    public static ProductImage getProductImageRandomSampleGenerator() {
        return new ProductImage()
            .id(longCount.incrementAndGet())
            .imageUri(UUID.randomUUID().toString())
            .position(intCount.incrementAndGet());
    }
}
