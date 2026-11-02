package io.github.Maike2961.hexagonal.application.core.usecase;

import io.github.Maike2961.hexagonal.application.ports.in.DeleteCustomerByIdInputPort;
import io.github.Maike2961.hexagonal.application.ports.in.FindCustomerByIdInputPort;
import io.github.Maike2961.hexagonal.application.ports.out.DeleteCustomerByIdOutputPort;
import io.github.Maike2961.hexagonal.config.logger.Enum.CustomerEnum;
import io.github.Maike2961.hexagonal.config.logger.LogEvent;
import io.github.Maike2961.hexagonal.config.logger.AppLogger;

public class    DeleteCustomerByIdUseCase implements DeleteCustomerByIdInputPort {
    private final FindCustomerByIdInputPort findCustomerByIdInputPort;
    private final DeleteCustomerByIdOutputPort deleteCustomerByIdOutputPort;

    public DeleteCustomerByIdUseCase(FindCustomerByIdInputPort findCustomerByIdInputPort, DeleteCustomerByIdOutputPort deleteCustomerByIdOutputPort) {
        this.findCustomerByIdInputPort = findCustomerByIdInputPort;
        this.deleteCustomerByIdOutputPort = deleteCustomerByIdOutputPort;
    }

    @Override
    public void delete(String id) {
        findCustomerByIdInputPort.find(id);
        AppLogger.info(
                new LogEvent(
                        CustomerEnum.CUSTOMER_FOUND,
                        "DeleteCustomerByIdUseCase",
                        "Cliente encontrado com o ID: " + id,
                        "INFO"
                )
        );
        deleteCustomerByIdOutputPort.delete(id);
        AppLogger.info(
                new LogEvent(
                        CustomerEnum.CUSTOMER_DELETED,
                        "DeleteCustomerByIdUseCase",
                        "Cliente deletado com o ID: " + id,
                        "INFO"
                )
        );
    }
}
