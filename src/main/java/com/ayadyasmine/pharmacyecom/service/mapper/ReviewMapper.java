package com.ayadyasmine.pharmacyecom.service.mapper;

import com.ayadyasmine.pharmacyecom.domain.Customer;
import com.ayadyasmine.pharmacyecom.domain.Product;
import com.ayadyasmine.pharmacyecom.domain.Review;
import com.ayadyasmine.pharmacyecom.service.dto.CustomerDTO;
import com.ayadyasmine.pharmacyecom.service.dto.ProductDTO;
import com.ayadyasmine.pharmacyecom.service.dto.ReviewDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Review} and its DTO {@link ReviewDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReviewMapper extends EntityMapper<ReviewDTO, Review> {
    @Mapping(target = "product", source = "product", qualifiedByName = "productName")
    @Mapping(target = "customer", source = "customer", qualifiedByName = "customerId")
    ReviewDTO toDto(Review s);

    @Named("productName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ProductDTO toDtoProductName(Product product);

    @Named("customerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CustomerDTO toDtoCustomerId(Customer customer);
}
