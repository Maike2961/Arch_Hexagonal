package io.github.Maike2961.hexagonal.application.ports.in;

import io.github.Maike2961.hexagonal.application.core.domain.Customer;

public interface InsertCustomerInputPort {
    void insert(Customer customer, String zipCode);
}
