package com.ayadyasmine.pharmacyecom.service.mapper;

import com.ayadyasmine.pharmacyecom.domain.Brand;
import com.ayadyasmine.pharmacyecom.domain.Category;
import com.ayadyasmine.pharmacyecom.domain.Product;
import com.ayadyasmine.pharmacyecom.domain.Promotion;
import com.ayadyasmine.pharmacyecom.service.dto.BrandDTO;
import com.ayadyasmine.pharmacyecom.service.dto.CategoryDTO;
import com.ayadyasmine.pharmacyecom.service.dto.ProductDTO;
import com.ayadyasmine.pharmacyecom.service.dto.PromotionDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Product} and its DTO {@link ProductDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {
    @Mapping(target = "category", source = "category", qualifiedByName = "categoryName")
    @Mapping(target = "brand", source = "brand", qualifiedByName = "brandName")
    @Mapping(target = "promotions", source = "promotions", qualifiedByName = "promotionTitleSet")
    ProductDTO toDto(Product s);

    @Mapping(target = "promotions", ignore = true)
    @Mapping(target = "removePromotions", ignore = true)
    Product toEntity(ProductDTO productDTO);

    @Named("categoryName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    CategoryDTO toDtoCategoryName(Category category);

    @Named("brandName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    BrandDTO toDtoBrandName(Brand brand);

    @Named("promotionTitle")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    PromotionDTO toDtoPromotionTitle(Promotion promotion);

    @Named("promotionTitleSet")
    default Set<PromotionDTO> toDtoPromotionTitleSet(Set<Promotion> promotion) {
        return promotion.stream().map(this::toDtoPromotionTitle).collect(Collectors.toSet());
    }
}
