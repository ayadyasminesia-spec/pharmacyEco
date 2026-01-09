package com.ayadyasmine.pharmacyecom.domain;

import static com.ayadyasmine.pharmacyecom.domain.AddressTestSamples.*;
import static com.ayadyasmine.pharmacyecom.domain.CartTestSamples.*;
import static com.ayadyasmine.pharmacyecom.domain.CustomerTestSamples.*;
import static com.ayadyasmine.pharmacyecom.domain.OrderTestSamples.*;
import static com.ayadyasmine.pharmacyecom.domain.ReviewTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.ayadyasmine.pharmacyecom.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CustomerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Customer.class);
        Customer customer1 = getCustomerSample1();
        Customer customer2 = new Customer();
        assertThat(customer1).isNotEqualTo(customer2);

        customer2.setId(customer1.getId());
        assertThat(customer1).isEqualTo(customer2);

        customer2 = getCustomerSample2();
        assertThat(customer1).isNotEqualTo(customer2);
    }

    @Test
    void cartTest() {
        Customer customer = getCustomerRandomSampleGenerator();
        Cart cartBack = getCartRandomSampleGenerator();

        customer.setCart(cartBack);
        assertThat(customer.getCart()).isEqualTo(cartBack);

        customer.cart(null);
        assertThat(customer.getCart()).isNull();
    }

    @Test
    void addressesTest() {
        Customer customer = getCustomerRandomSampleGenerator();
        Address addressBack = getAddressRandomSampleGenerator();

        customer.addAddresses(addressBack);
        assertThat(customer.getAddresses()).containsOnly(addressBack);
        assertThat(addressBack.getCustomer()).isEqualTo(customer);

        customer.removeAddresses(addressBack);
        assertThat(customer.getAddresses()).doesNotContain(addressBack);
        assertThat(addressBack.getCustomer()).isNull();

        customer.addresses(new HashSet<>(Set.of(addressBack)));
        assertThat(customer.getAddresses()).containsOnly(addressBack);
        assertThat(addressBack.getCustomer()).isEqualTo(customer);

        customer.setAddresses(new HashSet<>());
        assertThat(customer.getAddresses()).doesNotContain(addressBack);
        assertThat(addressBack.getCustomer()).isNull();
    }

    @Test
    void ordersTest() {
        Customer customer = getCustomerRandomSampleGenerator();
        Order orderBack = getOrderRandomSampleGenerator();

        customer.addOrders(orderBack);
        assertThat(customer.getOrders()).containsOnly(orderBack);
        assertThat(orderBack.getCustomer()).isEqualTo(customer);

        customer.removeOrders(orderBack);
        assertThat(customer.getOrders()).doesNotContain(orderBack);
        assertThat(orderBack.getCustomer()).isNull();

        customer.orders(new HashSet<>(Set.of(orderBack)));
        assertThat(customer.getOrders()).containsOnly(orderBack);
        assertThat(orderBack.getCustomer()).isEqualTo(customer);

        customer.setOrders(new HashSet<>());
        assertThat(customer.getOrders()).doesNotContain(orderBack);
        assertThat(orderBack.getCustomer()).isNull();
    }

    @Test
    void reviewsTest() {
        Customer customer = getCustomerRandomSampleGenerator();
        Review reviewBack = getReviewRandomSampleGenerator();

        customer.addReviews(reviewBack);
        assertThat(customer.getReviews()).containsOnly(reviewBack);
        assertThat(reviewBack.getCustomer()).isEqualTo(customer);

        customer.removeReviews(reviewBack);
        assertThat(customer.getReviews()).doesNotContain(reviewBack);
        assertThat(reviewBack.getCustomer()).isNull();

        customer.reviews(new HashSet<>(Set.of(reviewBack)));
        assertThat(customer.getReviews()).containsOnly(reviewBack);
        assertThat(reviewBack.getCustomer()).isEqualTo(customer);

        customer.setReviews(new HashSet<>());
        assertThat(customer.getReviews()).doesNotContain(reviewBack);
        assertThat(reviewBack.getCustomer()).isNull();
    }
}
