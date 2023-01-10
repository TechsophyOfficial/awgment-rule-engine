package com.techsophy.tsf.rule.engine.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

import static com.techsophy.tsf.rule.engine.constants.RuleEngineConstants.ACCEPT_LANGUAGE;

@ExtendWith(MockitoExtension.class)
public class LocaleConfigTest
{
    @Mock
    HttpServletRequest mockHttpServletRequest;
    @Mock
    List<Locale> mockLocales;
    @Mock
    List<Locale.LanguageRange> mockList;
    @Mock
    HttpServletRequest request;
    @InjectMocks
    LocaleConfig mockLocaleConfig;

    @Test
    void resolveLocaleTest() {
        Locale actualOutput = mockLocaleConfig.resolveLocale(request);
        Locale expectedOutput = Locale.US;
        Assertions.assertEquals(expectedOutput, actualOutput);
    }
}
