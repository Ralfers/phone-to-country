package org.example.exception;

import lombok.Getter;
import org.example.model.ApiError;
import org.example.model.ApiErrors;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends Exception {

    private final HttpStatus httpStatus;

    private final ApiErrors errors = new ApiErrors();

    public ApiException(String title, String detail, HttpStatus httpStatus) {
        super(detail);
        this.httpStatus = httpStatus;
        errors.addError(new ApiError(title, detail));
    }
}
