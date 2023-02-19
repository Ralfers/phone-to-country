package org.example.phonetocountry.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CountryCodeRequest {

    @NotNull
    @Pattern(regexp = "^\\+\\d{7,15}$", message = "Phone number must be in the E.164 format with 7-15 digits, e.g. \"+441514960453\"")
    String phoneNumber;
}
