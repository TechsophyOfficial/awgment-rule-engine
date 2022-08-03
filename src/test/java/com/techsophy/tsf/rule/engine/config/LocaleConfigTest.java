package com.techsophy.tsf.rule.engine.config;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

public class LocaleConfigTest
{
    @Mock
    HttpServletRequest mockHttpServletRequest;
    @Mock
    List<Locale> mockLocales;
    @Mock
    List<Locale.LanguageRange> mockList;
    @InjectMocks
    LocaleConfig mockLocaleConfig;

//    @Test
//    void resolveLocaleTest()
//    {
//        when(mockLocaleConfig.resolveLocale(mockHttpServletRequest)).thenReturn(any());
//        Locale responseTest= mockLocaleConfig.resolveLocale(mockHttpServletRequest);
//        Assertions.assertNotNull(responseTest);
//    }
}
