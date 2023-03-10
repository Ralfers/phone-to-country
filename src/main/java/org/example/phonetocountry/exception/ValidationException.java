package org.example.phonetocountry.exception;

import lombok.Getter;
import org.example.phonetocountry.model.ValidationError;
import org.example.phonetocountry.model.ValidationErrors;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Getter
public class ValidationException extends Exception {

    private final ValidationErrors validationErrors = new ValidationErrors();

    public ValidationException(String message, BindingResult bindingResult) {
        super(message);
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            validationErrors.addError(new ValidationError(fieldError.getField(), fieldError.getDefaultMessage()));
        }
    }
}
