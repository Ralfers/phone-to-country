package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.exception.ApiException;
import org.example.exception.ValidationException;
import org.example.model.CountryCodeRequest;
import org.example.model.CountryCodeResponse;
import org.example.service.PhoneToCountryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PhoneToCountryController {

    private final PhoneToCountryService phoneToCountryService;

    @GetMapping(path = "/country", produces = MediaType.APPLICATION_JSON_VALUE)
    public CountryCodeResponse getCountryCode(@Valid CountryCodeRequest countryParams, BindingResult bindingResult) throws Exception {
        log.debug("Finding country code from phone number {}", countryParams.getPhoneNumber());
        if (bindingResult.hasErrors()) {
            throw new ValidationException("Country code request validation failed", bindingResult);
        }

        List<String> countryCodes = phoneToCountryService.getCountryCodes(countryParams.getPhoneNumber());
        if (countryCodes == null) {
            throw new ApiException("Unknown phone number",
                    String.format("Country codes not found for phone number %s", countryParams.getPhoneNumber()), HttpStatus.BAD_REQUEST);
        }

        log.debug("Found the following country codes for phone number {}: {}", countryParams.getPhoneNumber(), countryCodes);
        return new CountryCodeResponse(countryCodes);
    }
}
