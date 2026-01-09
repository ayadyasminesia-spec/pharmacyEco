package com.ayadyasmine.pharmacyecom.service.mapper;

import static com.ayadyasmine.pharmacyecom.domain.OrderStatusHistoryAsserts.*;
import static com.ayadyasmine.pharmacyecom.domain.OrderStatusHistoryTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderStatusHistoryMapperTest {

    private OrderStatusHistoryMapper orderStatusHistoryMapper;

    @BeforeEach
    void setUp() {
        orderStatusHistoryMapper = new OrderStatusHistoryMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getOrderStatusHistorySample1();
        var actual = orderStatusHistoryMapper.toEntity(orderStatusHistoryMapper.toDto(expected));
        assertOrderStatusHistoryAllPropertiesEquals(expected, actual);
    }
}
