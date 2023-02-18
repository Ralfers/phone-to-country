package org.example.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "phone-to-country")
public class PhoneToCountryProperties {

    private CallingCodeWikiProperties callingCodeWiki = new CallingCodeWikiProperties();
}
