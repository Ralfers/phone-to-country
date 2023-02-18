package org.example.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ApiErrors {

    private final List<ApiError> errors = new ArrayList<>();

    public void addError(ApiError error) {
        errors.add(error);
    }
}
