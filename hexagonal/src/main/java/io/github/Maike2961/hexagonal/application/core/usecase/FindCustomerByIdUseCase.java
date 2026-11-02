package io.github.Maike2961.hexagonal.application.core.usecase;

import io.github.Maike2961.hexagonal.application.core.domain.Customer;
import io.github.Maike2961.hexagonal.application.core.exception.CustomerNotFoundException;
import io.github.Maike2961.hexagonal.application.ports.in.FindCustomerByIdInputPort;
import io.github.Maike2961.hexagonal.application.ports.out.FindCustomerByIdOutputPort;
import io.github.Maike2961.hexagonal.config.logger.Enum.CustomerEnum;
import io.github.Maike2961.hexagonal.config.logger.LogEvent;
import io.github.Maike2961.hexagonal.config.logger.AppLogger;

public class FindCustomerByIdUseCase implements FindCustomerByIdInputPort {

    private final FindCustomerByIdOutputPort findCustomerByIdOutputPort;

    public FindCustomerByIdUseCase(FindCustomerByIdOutputPort findCustomerByIdOutputPort) {
        this.findCustomerByIdOutputPort = findCustomerByIdOutputPort;
    }

    @Override
    public Customer find(String id) {
        Customer customer = findCustomerByIdOutputPort.find(id).orElseThrow(() -> {
            AppLogger.error(new LogEvent(
                    CustomerEnum.CUSTOMER_NOT_FOUND,
                    "FindCustomerByIdUseCase",
                    "Cliente não encontrado com o ID: " + id,
                    "ERROR"
            ));
            return new CustomerNotFoundException(id);
        });
        AppLogger.info(new LogEvent(
                CustomerEnum.CUSTOMER_FOUND,
                "FindCustomerByIdUseCase",
                "Cliente encontrado com o ID: " + id,
                "INFO"
        ));
        return customer;
    }
}
