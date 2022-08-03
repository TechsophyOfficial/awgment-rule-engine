package com.techsophy.tsf.rule.engine.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ActiveProfiles;
import static com.techsophy.tsf.rule.engine.constants.RuleEngineConstants.ARGS;
import static com.techsophy.tsf.rule.engine.constants.RuleEngineConstants.KEY;
import static com.techsophy.tsf.rule.engine.constants.RuleEngineTestConstant.TEST_ACTIVE_PROFILE;
import static org.mockito.ArgumentMatchers.any;

@ActiveProfiles(TEST_ACTIVE_PROFILE)
@SpringBootTest
class GlobalMessageSourceTest
{
    @Mock
    MessageSource mockMessageSource;
    @InjectMocks
    GlobalMessageSource mockGlobalMessageSource;

    @BeforeEach
    public void setUp()
    {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getTestSingleArgument()
    {
        Mockito.when(mockMessageSource.getMessage(any(),any(),any())).thenReturn(KEY);
        String responseTest=mockGlobalMessageSource.get(KEY);
        Assertions.assertNotNull(responseTest);
    }

    @Test
    void getTestDoubleArguments()
    {
        Mockito.when(mockMessageSource.getMessage(any(),any(),any())).thenReturn(KEY);
        String responseTest=mockGlobalMessageSource.get(KEY,ARGS);
        Assertions.assertNotNull(responseTest);
    }
}