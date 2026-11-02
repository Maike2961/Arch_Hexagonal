package io.github.Maike2961.hexagonal.adapters.out;

import io.github.Maike2961.hexagonal.adapters.out.repository.CustomerRepository;
import io.github.Maike2961.hexagonal.adapters.out.repository.entity.CustomerEntity;
import io.github.Maike2961.hexagonal.adapters.out.repository.mapper.CustomerEntityMapper;
import io.github.Maike2961.hexagonal.application.core.domain.Customer;

import io.github.Maike2961.hexagonal.application.ports.out.FindAllCustomerOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class FindAllCustomerAdapter implements FindAllCustomerOutputPort {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerEntityMapper customerEntityMapper;

    @Override
    public Page<Customer> findAll(Pageable pageable) {
        Page<CustomerEntity> customerEntities = customerRepository.findAll(pageable);
        return customerEntities.map(customerEntityMapper::toCustomer);
    }
}
