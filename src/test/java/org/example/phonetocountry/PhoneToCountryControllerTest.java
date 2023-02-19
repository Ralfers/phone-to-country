package org.example.phonetocountry;

import org.example.phonetocountry.service.PhoneToCountryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PhoneToCountryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PhoneToCountryService phoneToCountryService;

    private static Stream<Arguments> invalidCountryCodeRequestParamSource() {
        return Stream.of(
                Arguments.of("invalidphone"),
                Arguments.of("+invalidphone"),
                Arguments.of("12345678"),
                Arguments.of("1234 56789"),
                Arguments.of("+1234 56789"),
                Arguments.of("+1 23456789"),
                Arguments.of("++123456789"),
                Arguments.of("+123456"),
                Arguments.of("+1234567890000000")
        );
    }

    @ParameterizedTest
    @MethodSource("invalidCountryCodeRequestParamSource")
    public void testCountryCodeRequestParamsAreValidated(String invalidPhoneNumber) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/country")
                .queryParam("phoneNumber", invalidPhoneNumber))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field", equalTo("phoneNumber")))
                .andExpect(jsonPath("$.errors[0].message", equalTo("Phone number must be in the E.164 format with 7-15 digits, e.g. \"+441514960453\"")))
                .andReturn();
    }

    @Test
    public void testCountryCodeEndpoint() throws Exception {
        // given
        String validPhoneNumber = "+440846382091";
        Mockito.when(phoneToCountryService.getCountryCodes(validPhoneNumber)).thenReturn(List.of("GB", "GG", "IM", "JE"));

        // when - then
        mockMvc.perform(MockMvcRequestBuilders.get("/country")
                .queryParam("phoneNumber", validPhoneNumber))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.countryCodes[*]", containsInAnyOrder("GB", "GG", "IM", "JE")))
                .andReturn();
    }
}
