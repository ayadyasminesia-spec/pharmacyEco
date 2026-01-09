package com.ayadyasmine.pharmacyecom.service.mapper;

import com.ayadyasmine.pharmacyecom.domain.Product;
import com.ayadyasmine.pharmacyecom.domain.Promotion;
import com.ayadyasmine.pharmacyecom.service.dto.ProductDTO;
import com.ayadyasmine.pharmacyecom.service.dto.PromotionDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Promotion} and its DTO {@link PromotionDTO}.
 */
@Mapper(componentModel = "spring")
public interface PromotionMapper extends EntityMapper<PromotionDTO, Promotion> {
    @Mapping(target = "products", source = "products", qualifiedByName = "productNameSet")
    PromotionDTO toDto(Promotion s);

    @Mapping(target = "removeProducts", ignore = true)
    Promotion toEntity(PromotionDTO promotionDTO);

    @Named("productName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ProductDTO toDtoProductName(Product product);

    @Named("productNameSet")
    default Set<ProductDTO> toDtoProductNameSet(Set<Product> product) {
        return product.stream().map(this::toDtoProductName).collect(Collectors.toSet());
    }
}
