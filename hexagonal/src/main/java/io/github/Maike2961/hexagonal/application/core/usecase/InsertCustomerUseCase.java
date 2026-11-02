package io.github.Maike2961.hexagonal.application.core.usecase;

import io.github.Maike2961.hexagonal.adapters.in.consumer.message.CustomerMessage;
import io.github.Maike2961.hexagonal.application.core.domain.Customer;
import io.github.Maike2961.hexagonal.application.ports.in.InsertCustomerInputPort;
import io.github.Maike2961.hexagonal.application.ports.out.FindAddressZipCodeOutputPort;
import io.github.Maike2961.hexagonal.application.ports.out.InsertCustomerOutputPort;
import io.github.Maike2961.hexagonal.application.ports.out.SendCpfForValidationOutputPort;
import io.github.Maike2961.hexagonal.config.logger.Enum.AddressEnum;
import io.github.Maike2961.hexagonal.config.logger.Enum.CustomerEnum;
import io.github.Maike2961.hexagonal.config.logger.Enum.CPFEnum;
import io.github.Maike2961.hexagonal.config.logger.LogEvent;
import io.github.Maike2961.hexagonal.config.logger.AppLogger;

public class InsertCustomerUseCase implements InsertCustomerInputPort {
    private final FindAddressZipCodeOutputPort findAddressZipCodeOutputPort;
    private final InsertCustomerOutputPort insertCustomerOutputPort;
    private final SendCpfForValidationOutputPort sendCpfForValidationOutputPort;

    public InsertCustomerUseCase(FindAddressZipCodeOutputPort findAddressZipCodeOutputPort, InsertCustomerOutputPort insertCustomerOutputPort, SendCpfForValidationOutputPort sendCpfForValidationOutputPort) {
        this.findAddressZipCodeOutputPort = findAddressZipCodeOutputPort;
        this.insertCustomerOutputPort = insertCustomerOutputPort;
        this.sendCpfForValidationOutputPort = sendCpfForValidationOutputPort;
    }

    @Override
    public void insert(Customer customer, String zipCode) {
        var address = findAddressZipCodeOutputPort.find(zipCode);

        AppLogger.info(new LogEvent(
                AddressEnum.ADDRESS_FOUND,
                "InsertCustomerUseCase",
                "Endereço encontrado para o CEP: " + address.getStreet(),
                "INFO"
        ));
        customer.setAddress(address);

        customer = insertCustomerOutputPort.insert(customer);
        AppLogger.info(new LogEvent(
                CustomerEnum.CUSTOMER_CREATED,
                "InsertCustomerUseCase",
                "Cliente criado com sucesso: " + customer.getName(),
                "INFO"
        ));
        
        CustomerMessage message = new CustomerMessage(
            customer.getId(),
            customer.getName(),
            zipCode,
            customer.getCpf(),
            false
        );
        sendCpfForValidationOutputPort.send(message);

        AppLogger.info(new LogEvent(
                CPFEnum.SEND_CPF_TO_VALIDATION_TOPIC,
                "InsertCustomerUseCase",
                "Enviando CPF para validação: " + customer.getCpf(),
                "INFO"
        ));
    }
}
