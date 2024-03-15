package io.github.fabiooliveira.authapi.exceptionhandling;

import io.github.fabiooliveira.authapi.exceptionhandling.ErrorResponse;
import io.github.fabiooliveira.authapi.exceptions.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// Classe para tratar exceções
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class) // Anotação para tratar exceções do tipo BusinessException
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
        ErrorResponse errorResponse = new ErrorResponse(); // Instancia um objeto do tipo ErrorResponse
        errorResponse.setMessage(ex.getMessage()); // Atribui a mensagem da exceção ao objeto
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST); // Retorna um objeto do tipo ResponseEntity com o status 400 e o objeto errorResponse
    }
}