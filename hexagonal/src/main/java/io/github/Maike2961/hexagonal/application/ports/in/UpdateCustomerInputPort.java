package io.github.Maike2961.hexagonal.application.ports.in;

import io.github.Maike2961.hexagonal.application.core.domain.Customer;

public interface UpdateCustomerInputPort {
    void update(Customer customer, String zipCode);
}
