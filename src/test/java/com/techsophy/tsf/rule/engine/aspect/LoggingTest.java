package com.techsophy.tsf.rule.engine.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LoggingTest {

    @Mock
    Logger logger;

    @Mock
    JoinPoint joinPoint;
    @Mock
    Signature signature;

    @InjectMocks
    Logging logging;

    @Test
    void beforeServiceTest() {
        Mockito.when(joinPoint.getSignature()).thenReturn(signature);
        Mockito.when(signature.getName()).thenReturn("Signature_Name");
        logging.beforeService(joinPoint);
        verify(logger, times(1)).info(anyString());
    }

    @Test
    void afterServiceTest() {
        Mockito.when(joinPoint.getSignature()).thenReturn(signature);
        Mockito.when(signature.getName()).thenReturn("Signature_Name");
        logging.afterService(joinPoint);
        verify(logger, times(1)).info(anyString());
    }

    @Test
    void logAfterThrowingControllerTest() {
        Exception exception = Mockito.mock(Exception.class);
        Mockito.when(joinPoint.getSignature()).thenReturn(signature);
        Mockito.when(signature.getName()).thenReturn("Signature_Name");
        logging.logAfterThrowingController(joinPoint, exception);
        verify(exception, times(1)).getMessage();
    }

    @Test
    void logAfterThrowingServiceTest() {
        Exception exception = Mockito.mock(Exception.class);
        Mockito.when(joinPoint.getSignature()).thenReturn(signature);
        Mockito.when(signature.getName()).thenReturn("Signature_Name");
        logging.logAfterThrowingService(joinPoint, exception);
        verify(exception, times(1)).getMessage();
    }
}