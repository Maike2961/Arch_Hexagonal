package io.github.Maike2961.hexagonal.application.ports.out;

import io.github.Maike2961.hexagonal.application.core.domain.Customer;

public interface InsertCustomerOutputPort {
    Customer insert(Customer customer);
}
