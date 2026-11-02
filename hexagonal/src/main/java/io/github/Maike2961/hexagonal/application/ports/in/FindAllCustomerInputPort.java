package io.github.Maike2961.hexagonal.application.ports.in;

import io.github.Maike2961.hexagonal.application.core.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FindAllCustomerInputPort {
    Page<Customer> findAll(Pageable pageable);

}
