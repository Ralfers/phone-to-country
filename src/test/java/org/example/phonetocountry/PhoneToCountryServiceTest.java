package org.example.phonetocountry;

import org.example.phonetocountry.service.PhoneToCountryService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PhoneToCountryServiceTest {

    @Autowired
    PhoneToCountryService phoneToCountryService;

    private static Stream<Arguments> countryCodeTestSource() {
        return Stream.of(
                Arguments.of("+37126655891", List.of("LV")),
                Arguments.of("+112385746308472", List.of("US", "CA")),
                Arguments.of("+186885746308", List.of("TT")),
                Arguments.of("+1264857463", List.of("AI")),
                Arguments.of("+85284638209", List.of("HK")),
                Arguments.of("+440846382091", List.of("GB", "GG", "IM", "JE")),
                Arguments.of("+2106644", null),
                Arguments.of("+384348239482903", null),
                Arguments.of("+42734434345", null),
                Arguments.of("+800584935894", null),
                Arguments.of("+9913449", null),
                Arguments.of("+8838475758", null)
        );
    }

    @ParameterizedTest
    @MethodSource("countryCodeTestSource")
    public void testCorrectCountryCodesAreReturned(String phoneNumber, List<String> expectedCountryCodes) {
        // when
        List<String> countryCodes = phoneToCountryService.getCountryCodes(phoneNumber);
        System.out.println("countryCodes: " + countryCodes);
        System.out.println("expectedCountryCodes: " + expectedCountryCodes);

        // then
        assertThat(countryCodes, expectedCountryCodes != null
                ? containsInAnyOrder(expectedCountryCodes.toArray())
                : equalTo(null));
    }
}
