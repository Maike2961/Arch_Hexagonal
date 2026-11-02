package io.github.Maike2961.hexagonal.application.ports.out;

import io.github.Maike2961.hexagonal.application.core.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FindAllCustomerOutputPort {
    Page<Customer> findAll(Pageable pageable);
}
