package org.example.phonetocountry.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class CountryCodeResponse {

    private final List<String> countryCodes;
}
