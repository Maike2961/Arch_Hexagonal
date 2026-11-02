package io.github.Maike2961.hexagonal.application.core.exception;

public class ZipCodeException extends DomainException {
    public ZipCodeException(String zipCode) {
        super(String.format("Erro ao buscar endereço pelo CEP: %s", zipCode));
    }
}
