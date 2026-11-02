package io.github.Maike2961.hexagonal.application.core.usecase;

import io.github.Maike2961.hexagonal.application.core.domain.Customer;
import io.github.Maike2961.hexagonal.application.ports.in.FindCustomerByIdInputPort;
import io.github.Maike2961.hexagonal.application.ports.in.UpdateCustomerInputPort;
import io.github.Maike2961.hexagonal.application.ports.out.FindAddressZipCodeOutputPort;
import io.github.Maike2961.hexagonal.application.ports.out.UpdateCustomerOutputPort;
import io.github.Maike2961.hexagonal.config.logger.Enum.AddressEnum;
import io.github.Maike2961.hexagonal.config.logger.Enum.CustomerEnum;
import io.github.Maike2961.hexagonal.config.logger.LogEvent;
import io.github.Maike2961.hexagonal.config.logger.AppLogger;

public class UpdateCustomerUseCase implements UpdateCustomerInputPort {

    private final FindCustomerByIdInputPort findCustomerByIdInputPort;
    private final FindAddressZipCodeOutputPort findAddressZipCodeOutputPort;
    private final UpdateCustomerOutputPort updateCustomerOutputPort;

    public UpdateCustomerUseCase(
            FindCustomerByIdInputPort findCustomerByIdInputPort,
            FindAddressZipCodeOutputPort findAddressZipCodeOutputPort,
            UpdateCustomerOutputPort updateCustomerOutputPort
    ) {
        this.findCustomerByIdInputPort = findCustomerByIdInputPort;
        this.findAddressZipCodeOutputPort = findAddressZipCodeOutputPort;
        this.updateCustomerOutputPort = updateCustomerOutputPort;
    }

    @Override
    public void update(Customer customer, String zipCode){
        findCustomerByIdInputPort.find(customer.getId());
        AppLogger.info(new LogEvent(
                CustomerEnum.CUSTOMER_FOUND,
                "UpdateCustomerUseCase",
                "Cliente encontrado com o ID: " + customer.getId(),
                "INFO"
        ));
        var address = findAddressZipCodeOutputPort.find((zipCode));

        AppLogger.info(new LogEvent(
                AddressEnum.ADDRESS_FOUND,
                "UpdateCustomerUseCase",
                "Endereço encontrado para o CEP: " + address.getStreet(),
                "INFO"
        ));
        customer.setAddress(address);
        updateCustomerOutputPort.update(customer);

        AppLogger.info(new LogEvent(
                CustomerEnum.CUSTOMER_UPDATED,
                "UpdateCustomerUseCase",
                "Cliente atualizado com sucesso: " + customer.getName(),
                "INFO"
        ));
    }
}
