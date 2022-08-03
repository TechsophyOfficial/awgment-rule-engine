package com.techsophy.tsf.rule.engine.utils;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import static com.techsophy.tsf.rule.engine.constants.RuleEngineConstants.*;
import static com.techsophy.tsf.rule.engine.constants.RuleEngineTestConstant.TEST_ACTIVE_PROFILE;
import static javax.swing.text.html.FormSubmitEvent.MethodType.POST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith({SpringExtension.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles(TEST_ACTIVE_PROFILE)
class WebClientWrapperTest
{
    @InjectMocks
    WebClientWrapper webClientWrapper;
    private WebClient webClientMock;

    @BeforeEach
    void mockWebClient()
    {
        WebClient.RequestBodyUriSpec requestBodyUriMock = mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestHeadersUriSpec requestHeadersUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec requestHeadersMock = mock(WebClient.RequestHeadersSpec.class);
        WebClient.RequestBodySpec requestBodyMock = mock(WebClient.RequestBodySpec.class);
        WebClient.ResponseSpec responseMock = mock(WebClient.ResponseSpec.class);
        webClientMock = mock(WebClient.class);
        when(webClientMock.get()).thenReturn(requestHeadersUriSpec);
        when(webClientMock.method(HttpMethod.DELETE)).thenReturn(requestBodyUriMock);
        when(webClientMock.post()).thenReturn(requestBodyUriMock);
        when(webClientMock.put()).thenReturn(requestBodyUriMock);
        when(requestBodyUriMock.uri(LOCAL_HOST_URL)).thenReturn(requestBodyMock);
        when(requestHeadersUriSpec.uri(LOCAL_HOST_URL)).thenReturn(requestHeadersMock);
        when(requestBodyMock.bodyValue(TOKEN)).thenReturn(requestHeadersMock);
        when(requestBodyMock.retrieve()).thenReturn(responseMock);
        when(requestHeadersMock.retrieve()).thenReturn(responseMock);
        when(responseMock.bodyToMono(String.class))
                .thenReturn(Mono.just(TEST));
    }

    @Order(1)
    @Test
    void createWebClientTest()
    {
        WebClient webClientTest=  webClientWrapper.createWebClient(TOKEN);
        Assertions.assertNotNull(webClientTest);
    }

    @Order(2)
    @Test
    void getWebClientRequestTest()
    {
        String getResponse= webClientWrapper.webclientRequest(webClientMock,LOCAL_HOST_URL, GET,null);
        assertEquals(TEST,getResponse);
        String putResponse= webClientWrapper.webclientRequest(webClientMock,LOCAL_HOST_URL,PUT,TOKEN);
        assertEquals(TEST,putResponse);
        String deleteResponse= webClientWrapper.webclientRequest(webClientMock,LOCAL_HOST_URL,DELETE,null);
        assertEquals(TEST,deleteResponse);
        String deleteBodyResponse= webClientWrapper.webclientRequest(webClientMock,LOCAL_HOST_URL,DELETE,TOKEN);
        assertEquals(TEST,deleteBodyResponse);
        String postResponse= webClientWrapper.webclientRequest(webClientMock,LOCAL_HOST_URL, String.valueOf(POST),TOKEN);
        assertEquals(TEST,postResponse);
    }
}
