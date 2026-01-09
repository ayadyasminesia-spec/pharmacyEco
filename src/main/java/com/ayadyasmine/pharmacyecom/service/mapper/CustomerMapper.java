package com.ayadyasmine.pharmacyecom.service.mapper;

import com.ayadyasmine.pharmacyecom.domain.Cart;
import com.ayadyasmine.pharmacyecom.domain.Customer;
import com.ayadyasmine.pharmacyecom.domain.User;
import com.ayadyasmine.pharmacyecom.service.dto.CartDTO;
import com.ayadyasmine.pharmacyecom.service.dto.CustomerDTO;
import com.ayadyasmine.pharmacyecom.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Customer} and its DTO {@link CustomerDTO}.
 */
@Mapper(componentModel = "spring")
public interface CustomerMapper extends EntityMapper<CustomerDTO, Customer> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    @Mapping(target = "cart", source = "cart", qualifiedByName = "cartId")
    CustomerDTO toDto(Customer s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("cartId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CartDTO toDtoCartId(Cart cart);
}
