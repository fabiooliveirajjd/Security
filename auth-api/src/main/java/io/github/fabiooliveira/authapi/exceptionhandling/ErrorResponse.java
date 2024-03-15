package io.github.fabiooliveira.authapi.exceptionhandling;

import lombok.Data;

// Classe para representar um erro
@Data
public class ErrorResponse {
    private String message;
}