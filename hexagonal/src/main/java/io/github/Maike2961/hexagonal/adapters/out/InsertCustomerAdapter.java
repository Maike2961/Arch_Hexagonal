package io.github.Maike2961.hexagonal.adapters.out;

import io.github.Maike2961.hexagonal.adapters.out.repository.CustomerRepository;
import io.github.Maike2961.hexagonal.adapters.out.repository.mapper.CustomerEntityMapper;
import io.github.Maike2961.hexagonal.application.core.domain.Customer;
import io.github.Maike2961.hexagonal.application.ports.out.InsertCustomerOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InsertCustomerAdapter implements InsertCustomerOutputPort {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerEntityMapper customerEntityMapper;

    @Override
    public Customer insert(Customer customer) {
        var customerEntity = customerEntityMapper.toCustomerEntity(customer);
        var savedEntity = customerRepository.save(customerEntity);
        return customerEntityMapper.toCustomer(savedEntity);
    }
}
