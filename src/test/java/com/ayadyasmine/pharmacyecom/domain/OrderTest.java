package com.ayadyasmine.pharmacyecom.domain;

import static com.ayadyasmine.pharmacyecom.domain.CustomerTestSamples.*;
import static com.ayadyasmine.pharmacyecom.domain.OrderItemTestSamples.*;
import static com.ayadyasmine.pharmacyecom.domain.OrderStatusHistoryTestSamples.*;
import static com.ayadyasmine.pharmacyecom.domain.OrderTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.ayadyasmine.pharmacyecom.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class OrderTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Order.class);
        Order order1 = getOrderSample1();
        Order order2 = new Order();
        assertThat(order1).isNotEqualTo(order2);

        order2.setId(order1.getId());
        assertThat(order1).isEqualTo(order2);

        order2 = getOrderSample2();
        assertThat(order1).isNotEqualTo(order2);
    }

    @Test
    void itemsTest() {
        Order order = getOrderRandomSampleGenerator();
        OrderItem orderItemBack = getOrderItemRandomSampleGenerator();

        order.addItems(orderItemBack);
        assertThat(order.getItems()).containsOnly(orderItemBack);
        assertThat(orderItemBack.getOrder()).isEqualTo(order);

        order.removeItems(orderItemBack);
        assertThat(order.getItems()).doesNotContain(orderItemBack);
        assertThat(orderItemBack.getOrder()).isNull();

        order.items(new HashSet<>(Set.of(orderItemBack)));
        assertThat(order.getItems()).containsOnly(orderItemBack);
        assertThat(orderItemBack.getOrder()).isEqualTo(order);

        order.setItems(new HashSet<>());
        assertThat(order.getItems()).doesNotContain(orderItemBack);
        assertThat(orderItemBack.getOrder()).isNull();
    }

    @Test
    void customerTest() {
        Order order = getOrderRandomSampleGenerator();
        Customer customerBack = getCustomerRandomSampleGenerator();

        order.setCustomer(customerBack);
        assertThat(order.getCustomer()).isEqualTo(customerBack);

        order.customer(null);
        assertThat(order.getCustomer()).isNull();
    }

    @Test
    void statusHistoryTest() {
        Order order = getOrderRandomSampleGenerator();
        OrderStatusHistory orderStatusHistoryBack = getOrderStatusHistoryRandomSampleGenerator();

        order.addStatusHistory(orderStatusHistoryBack);
        assertThat(order.getStatusHistories()).containsOnly(orderStatusHistoryBack);
        assertThat(orderStatusHistoryBack.getOrder()).isEqualTo(order);

        order.removeStatusHistory(orderStatusHistoryBack);
        assertThat(order.getStatusHistories()).doesNotContain(orderStatusHistoryBack);
        assertThat(orderStatusHistoryBack.getOrder()).isNull();

        order.statusHistories(new HashSet<>(Set.of(orderStatusHistoryBack)));
        assertThat(order.getStatusHistories()).containsOnly(orderStatusHistoryBack);
        assertThat(orderStatusHistoryBack.getOrder()).isEqualTo(order);

        order.setStatusHistories(new HashSet<>());
        assertThat(order.getStatusHistories()).doesNotContain(orderStatusHistoryBack);
        assertThat(orderStatusHistoryBack.getOrder()).isNull();
    }
}
