package io.github.Maike2961.hexagonal.application.ports.out;

import io.github.Maike2961.hexagonal.application.core.domain.Customer;

import java.util.Optional;

public interface FindCustomerByIdOutputPort {
    Optional<Customer> find(String id);
}
