package io.github.Maike2961.hexagonal.adapters.in.controller.mapper;

import io.github.Maike2961.hexagonal.adapters.in.controller.request.CustomerRequest;
import io.github.Maike2961.hexagonal.adapters.in.controller.response.CustomerResponse;
import io.github.Maike2961.hexagonal.application.core.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "isValidCpf", ignore = true)
    Customer toCustomer(CustomerRequest request);

    CustomerResponse toCustomerResponse(Customer customer);
}
