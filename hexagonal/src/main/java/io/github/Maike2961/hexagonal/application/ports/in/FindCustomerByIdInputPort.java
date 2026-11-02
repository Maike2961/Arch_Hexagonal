package io.github.Maike2961.hexagonal.application.ports.in;

import io.github.Maike2961.hexagonal.application.core.domain.Customer;

public interface FindCustomerByIdInputPort {
    Customer find(String id);
}
