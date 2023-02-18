package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.exception.ApiException;
import org.example.exception.ValidationException;
import org.example.model.ApiErrors;
import org.example.model.ValidationErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class PhoneToCountryExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ValidationErrors> handleValidationException(ValidationException validationException) {
        log.error("Validation exception occurred: {}", validationException.getMessage(), validationException);
        return new ResponseEntity<>(validationException.getValidationErrors(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiErrors> handleApiException(ApiException apiException) {
        log.error("API exception occurred: {}", apiException.getMessage(), apiException);
        return new ResponseEntity<>(apiException.getErrors(), apiException.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleException(Exception exception) {
        log.error("Unhandled exception occurred", exception);

        return ResponseEntity.internalServerError().build();
    }
}
