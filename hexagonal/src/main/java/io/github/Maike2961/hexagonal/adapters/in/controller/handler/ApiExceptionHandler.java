package io.github.Maike2961.hexagonal.adapters.in.controller.handler;

import io.github.Maike2961.hexagonal.application.core.exception.CustomerNotFoundException;
import io.github.Maike2961.hexagonal.application.core.exception.ZipCodeException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<StandardError> customerNotFoundExceptionHandler(CustomerNotFoundException e, HttpServletRequest request){
        var status = HttpStatus.NOT_FOUND.value();
        StandardError error = new StandardError(System.currentTimeMillis(), status, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(ZipCodeException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public ResponseEntity<StandardError> zipCodeExceptionHandleException(Exception e, HttpServletRequest request) {
        var status = HttpStatus.BAD_GATEWAY.value();
        StandardError error = new StandardError(System.currentTimeMillis(), status, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_CONTENT)
    public ResponseEntity<?> handleCampoObrigatorioException(MethodArgumentNotValidException e) {

        List<FieldError> fieldErrorList = e.getFieldErrors();

        List<ErroCampo> errors = fieldErrorList.stream()
                .map(err -> new ErroCampo(
                        "Campo obrigatório",
                        List.of(
                                err.getField(),
                                err.getDefaultMessage()
                        )
                )).toList();

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT.value()).body(errors);
    }
}
