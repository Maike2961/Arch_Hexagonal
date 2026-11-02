package io.github.Maike2961.hexagonal.application.ports.out;

import io.github.Maike2961.hexagonal.adapters.in.consumer.message.CustomerMessage;

public interface SendCpfForValidationOutputPort {
    void send(CustomerMessage message);
}
