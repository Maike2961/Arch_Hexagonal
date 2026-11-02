package io.github.Maike2961.hexagonal.adapters.out.repository.mapper;

import io.github.Maike2961.hexagonal.adapters.out.repository.entity.CustomerEntity;
import io.github.Maike2961.hexagonal.application.core.domain.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerEntityMapper {
    CustomerEntity toCustomerEntity(Customer customer);
    Customer toCustomer(CustomerEntity customerEntity);
}
