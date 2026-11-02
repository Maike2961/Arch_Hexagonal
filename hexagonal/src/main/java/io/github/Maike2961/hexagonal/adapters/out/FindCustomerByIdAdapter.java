package io.github.Maike2961.hexagonal.adapters.out;

import io.github.Maike2961.hexagonal.adapters.out.repository.CustomerRepository;
import io.github.Maike2961.hexagonal.adapters.out.repository.mapper.CustomerEntityMapper;
import io.github.Maike2961.hexagonal.application.core.domain.Customer;
import io.github.Maike2961.hexagonal.application.ports.out.FindCustomerByIdOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FindCustomerByIdAdapter implements FindCustomerByIdOutputPort {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerEntityMapper customerEntityMapper;

    @Override
    public Optional<Customer> find(String id) {
        var customerEntity = customerRepository.findById(id);
        return customerEntity.map(entity -> customerEntityMapper.toCustomer(entity));
    }
}
