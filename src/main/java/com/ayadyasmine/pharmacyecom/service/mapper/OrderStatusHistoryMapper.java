package com.ayadyasmine.pharmacyecom.service.mapper;

import com.ayadyasmine.pharmacyecom.domain.Order;
import com.ayadyasmine.pharmacyecom.domain.OrderStatusHistory;
import com.ayadyasmine.pharmacyecom.service.dto.OrderDTO;
import com.ayadyasmine.pharmacyecom.service.dto.OrderStatusHistoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrderStatusHistory} and its DTO {@link OrderStatusHistoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrderStatusHistoryMapper extends EntityMapper<OrderStatusHistoryDTO, OrderStatusHistory> {
    @Mapping(target = "order", source = "order", qualifiedByName = "orderId")
    OrderStatusHistoryDTO toDto(OrderStatusHistory s);

    @Named("orderId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrderDTO toDtoOrderId(Order order);
}
