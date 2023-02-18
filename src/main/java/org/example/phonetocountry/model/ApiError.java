package org.example.phonetocountry.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiError {

    private final String title;

    private final String detail;
}
