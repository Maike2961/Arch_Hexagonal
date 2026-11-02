package io.github.Maike2961.hexagonal.application.core.exception;

public class CustomerNotFoundException extends DomainException {
    public CustomerNotFoundException(String id) {
        super(String.format("Usuário não encontrado ", id));
    }
}
