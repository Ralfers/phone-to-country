package org.example.phonetocountry.service;

import lombok.extern.slf4j.Slf4j;
import org.example.phonetocountry.properties.PhoneToCountryProperties;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class PhoneToCountryService {

    private static final Pattern CALLING_CODE_ENTRY_PATTERN = Pattern.compile("(\\+\\d+(?:\\s*\\d+)*):\\s*([A-Z]{2}(?:,\\s*[A-Z]{2})*)");

    private final PhoneToCountryProperties phoneToCountryProperties;
    private final Map<String, List<String>> callingCodeToCountryMap = new HashMap<>();
    private int maxCallingCodeDigits = 0;

    @Autowired
    public PhoneToCountryService(ConfigurableApplicationContext applicationContext, PhoneToCountryProperties phoneToCountryProperties) {
        this.phoneToCountryProperties = phoneToCountryProperties;

        try {
            loadPhoneToCountryMap();
        } catch (Exception exception) {
            log.error("Failed to load phone country codes", exception);
            System.exit(SpringApplication.exit(applicationContext));
        }
    }

    public List<String> getCountryCodes(String phoneNumber) {
        for (int i = maxCallingCodeDigits; i > 0; i--) {
            String prefix = phoneNumber.substring(0, i + 1);
            List<String> countryCodes = callingCodeToCountryMap.get(prefix);
            if (countryCodes != null) {
                return countryCodes;
            }
        }

        return null;
    }

    private void loadPhoneToCountryMap() throws Exception {
        Document document = Jsoup.connect(phoneToCountryProperties.getCallingCodeWiki().getUrl()).get();
        Element tableElement = document.selectFirst(phoneToCountryProperties.getCallingCodeWiki().getTableSelector());
        if (tableElement == null) {
            log.error("Calling code table not found using selector \"{}\"", phoneToCountryProperties.getCallingCodeWiki().getTableSelector());
            throw new Exception("Calling code table not found");
        }

        Elements tableCells = tableElement.select("td");
        for (Element tableCell : tableCells) {
            parseTableCell(tableCell);
        }

        addMissingCallingCodes();

        log.debug("Loaded calling code country map: {}", callingCodeToCountryMap);
    }

    private void parseTableCell(Element tableCell) {
        Matcher tableCellMatcher = CALLING_CODE_ENTRY_PATTERN.matcher(tableCell.text());
        tableCellMatcher.results().forEach(matchResult -> {
            String callingCode = matchResult.group(1).replaceAll("\\s", "");
            String countryCodes = matchResult.group(2);
            callingCodeToCountryMap.put(callingCode, Arrays.asList(countryCodes.split(",\\s*")));

            int callingCodeDigits = callingCode.length() - 1;
            if (callingCodeDigits > maxCallingCodeDigits) {
                maxCallingCodeDigits = callingCodeDigits;
            }
        });
    }

    private void addMissingCallingCodes() {
        callingCodeToCountryMap.put("+1", List.of("US", "CA"));
    }
}
