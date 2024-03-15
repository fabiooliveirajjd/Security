package io.github.fabiooliveira.authapi.exceptions;

// Classe para representar uma exceção de negócio
public class BusinessException extends  RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
