package io.github.Maike2961.hexagonal.adapters.out.client.mapper;

import io.github.Maike2961.hexagonal.adapters.out.client.response.AddressResponse;
import io.github.Maike2961.hexagonal.application.core.domain.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressResponseMapper {
    Address toAddress(AddressResponse addressResponse);
}
