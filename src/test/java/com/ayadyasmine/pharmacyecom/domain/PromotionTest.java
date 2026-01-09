package com.ayadyasmine.pharmacyecom.domain;

import static com.ayadyasmine.pharmacyecom.domain.ProductTestSamples.*;
import static com.ayadyasmine.pharmacyecom.domain.PromotionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.ayadyasmine.pharmacyecom.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PromotionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Promotion.class);
        Promotion promotion1 = getPromotionSample1();
        Promotion promotion2 = new Promotion();
        assertThat(promotion1).isNotEqualTo(promotion2);

        promotion2.setId(promotion1.getId());
        assertThat(promotion1).isEqualTo(promotion2);

        promotion2 = getPromotionSample2();
        assertThat(promotion1).isNotEqualTo(promotion2);
    }

    @Test
    void productsTest() {
        Promotion promotion = getPromotionRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        promotion.addProducts(productBack);
        assertThat(promotion.getProducts()).containsOnly(productBack);

        promotion.removeProducts(productBack);
        assertThat(promotion.getProducts()).doesNotContain(productBack);

        promotion.products(new HashSet<>(Set.of(productBack)));
        assertThat(promotion.getProducts()).containsOnly(productBack);

        promotion.setProducts(new HashSet<>());
        assertThat(promotion.getProducts()).doesNotContain(productBack);
    }
}
