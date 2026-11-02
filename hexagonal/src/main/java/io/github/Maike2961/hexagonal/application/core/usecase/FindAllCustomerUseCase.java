package io.github.Maike2961.hexagonal.application.core.usecase;

import io.github.Maike2961.hexagonal.application.core.domain.Customer;
import io.github.Maike2961.hexagonal.application.ports.in.FindAllCustomerInputPort;
import io.github.Maike2961.hexagonal.application.ports.out.FindAllCustomerOutputPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class FindAllCustomerUseCase implements FindAllCustomerInputPort {

    private final FindAllCustomerOutputPort findAllCustomerOutputPort;

    public FindAllCustomerUseCase(FindAllCustomerOutputPort findAllCustomerOutputPort) {
        this.findAllCustomerOutputPort = findAllCustomerOutputPort;
    }

    @Override
    public Page<Customer> findAll(Pageable pageable) {
        return findAllCustomerOutputPort.findAll(pageable);
    }
}
