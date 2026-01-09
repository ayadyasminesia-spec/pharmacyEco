package com.ayadyasmine.pharmacyecom.domain;

import static com.ayadyasmine.pharmacyecom.domain.CustomerTestSamples.*;
import static com.ayadyasmine.pharmacyecom.domain.ProductTestSamples.*;
import static com.ayadyasmine.pharmacyecom.domain.ReviewTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.ayadyasmine.pharmacyecom.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReviewTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Review.class);
        Review review1 = getReviewSample1();
        Review review2 = new Review();
        assertThat(review1).isNotEqualTo(review2);

        review2.setId(review1.getId());
        assertThat(review1).isEqualTo(review2);

        review2 = getReviewSample2();
        assertThat(review1).isNotEqualTo(review2);
    }

    @Test
    void productTest() {
        Review review = getReviewRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        review.setProduct(productBack);
        assertThat(review.getProduct()).isEqualTo(productBack);

        review.product(null);
        assertThat(review.getProduct()).isNull();
    }

    @Test
    void customerTest() {
        Review review = getReviewRandomSampleGenerator();
        Customer customerBack = getCustomerRandomSampleGenerator();

        review.setCustomer(customerBack);
        assertThat(review.getCustomer()).isEqualTo(customerBack);

        review.customer(null);
        assertThat(review.getCustomer()).isNull();
    }
}
