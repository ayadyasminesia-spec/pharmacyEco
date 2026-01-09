package com.ayadyasmine.pharmacyecom.domain;

import static com.ayadyasmine.pharmacyecom.domain.BrandTestSamples.*;
import static com.ayadyasmine.pharmacyecom.domain.CategoryTestSamples.*;
import static com.ayadyasmine.pharmacyecom.domain.ProductTestSamples.*;
import static com.ayadyasmine.pharmacyecom.domain.PromotionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.ayadyasmine.pharmacyecom.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Product.class);
        Product product1 = getProductSample1();
        Product product2 = new Product();
        assertThat(product1).isNotEqualTo(product2);

        product2.setId(product1.getId());
        assertThat(product1).isEqualTo(product2);

        product2 = getProductSample2();
        assertThat(product1).isNotEqualTo(product2);
    }

    @Test
    void categoryTest() {
        Product product = getProductRandomSampleGenerator();
        Category categoryBack = getCategoryRandomSampleGenerator();

        product.setCategory(categoryBack);
        assertThat(product.getCategory()).isEqualTo(categoryBack);

        product.category(null);
        assertThat(product.getCategory()).isNull();
    }

    @Test
    void brandTest() {
        Product product = getProductRandomSampleGenerator();
        Brand brandBack = getBrandRandomSampleGenerator();

        product.setBrand(brandBack);
        assertThat(product.getBrand()).isEqualTo(brandBack);

        product.brand(null);
        assertThat(product.getBrand()).isNull();
    }

    @Test
    void promotionsTest() {
        Product product = getProductRandomSampleGenerator();
        Promotion promotionBack = getPromotionRandomSampleGenerator();

        product.addPromotions(promotionBack);
        assertThat(product.getPromotions()).containsOnly(promotionBack);
        assertThat(promotionBack.getProducts()).containsOnly(product);

        product.removePromotions(promotionBack);
        assertThat(product.getPromotions()).doesNotContain(promotionBack);
        assertThat(promotionBack.getProducts()).doesNotContain(product);

        product.promotions(new HashSet<>(Set.of(promotionBack)));
        assertThat(product.getPromotions()).containsOnly(promotionBack);
        assertThat(promotionBack.getProducts()).containsOnly(product);

        product.setPromotions(new HashSet<>());
        assertThat(product.getPromotions()).doesNotContain(promotionBack);
        assertThat(promotionBack.getProducts()).doesNotContain(product);
    }
}
