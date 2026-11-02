package io.github.Maike2961.hexagonal.application.core.exception;

public abstract class DomainException extends RuntimeException {
    public DomainException(String message){
        super(message);
    }
}
