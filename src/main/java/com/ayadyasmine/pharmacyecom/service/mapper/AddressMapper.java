package com.ayadyasmine.pharmacyecom.service.mapper;

import com.ayadyasmine.pharmacyecom.domain.Address;
import com.ayadyasmine.pharmacyecom.domain.Customer;
import com.ayadyasmine.pharmacyecom.service.dto.AddressDTO;
import com.ayadyasmine.pharmacyecom.service.dto.CustomerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Address} and its DTO {@link AddressDTO}.
 */
@Mapper(componentModel = "spring")
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {
    @Mapping(target = "customer", source = "customer", qualifiedByName = "customerId")
    AddressDTO toDto(Address s);

    @Named("customerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CustomerDTO toDtoCustomerId(Customer customer);
}
