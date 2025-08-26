package com.castleedev.cabanassyc_backend.Exceptions;

import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import com.castleedev.cabanassyc_backend.Controllers.ApiResponse;
import com.fasterxml.jackson.databind.JsonMappingException;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception e) {
        ApiResponse<?> response = new ApiResponse<>(
            false, 
            "Server error: " + e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
    
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiResponse<Void>> handleResponseStatusException(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatusCode())
            .body(new ApiResponse<>(false, ex.getReason()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .collect(Collectors.joining(", "));
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ApiResponse<>(false, "Validation error: " + errorMessage));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleJsonParseException(HttpMessageNotReadableException ex) {
        String errorMessage = "Error en el formato de los datos";
        
        if (ex.getCause() instanceof JsonMappingException) {
            JsonMappingException jme = (JsonMappingException) ex.getCause();
            if (jme.getCause() instanceof DateTimeParseException) {
                errorMessage = "Formato de fecha inválido. Use el formato YYYY-MM-DD (ej: 2028-01-01)";
            }
        }
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(false, errorMessage));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<?>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String name = ex.getName();
        String type = Optional.ofNullable(ex.getRequiredType())
                              .map(Class::getSimpleName)
                              .orElse("unknown");
        Object value = ex.getValue();
        String message = String.format("El parámetro '%s' debe ser de tipo %s (valor recibido: '%s')", 
                                      name, type, value);

        return ResponseEntity.badRequest()
                .body(new ApiResponse<Void>(false, message, null));
    }

}
