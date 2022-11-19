package com.techsophy.tsf.rule.engine.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsophy.tsf.rule.engine.utils.WebClientWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.client.WebClient;
import java.nio.file.AccessDeniedException;
import java.time.Instant;
import java.util.*;

import static com.techsophy.tsf.rule.engine.constants.RuleEngineConstants.CLIENT_ROLES;
import static com.techsophy.tsf.rule.engine.constants.RuleEngineConstants.GET;
import static com.techsophy.tsf.rule.engine.constants.RuleEngineTestConstant.TEST_ACTIVE_PROFILE;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ActiveProfiles(TEST_ACTIVE_PROFILE)
@SpringBootTest
class JWTRoleConverterTest
{
    @Mock
    ObjectMapper mockObjectMapper;
    @Mock
    WebClientWrapper webClientWrapper;
    @InjectMocks
    JWTRoleConverter jwtRoleConverter;

    @Test
    void convertTest() throws JsonProcessingException
    {
        Map<String, Object> map = new HashMap<>();
        map.put("clientRoles", "abc");
        List<String> list=new ArrayList<>();
        list.add("awgment");
        list.add("awgment");
        Jwt jwt= new Jwt("1", Instant.now(),null,Map.of("abc","abc"),Map.of("abc","abc"));
        WebClient webClient= WebClient.builder().build();
        when(webClientWrapper.createWebClient(any())).thenReturn(webClient);
        when(webClientWrapper.webclientRequest(any(),any(),eq(GET),any()))
                .thenReturn("abc");
        when(mockObjectMapper.readValue("abc",Map.class)).thenReturn(map);
        when(mockObjectMapper.convertValue(any(),eq(List.class))).thenReturn(list);
        Collection grantedAuthority =  jwtRoleConverter.convert(jwt);
        Assertions.assertNotNull(grantedAuthority);
    }

    @Test
    void convertTestWhileThrowingException() throws JsonProcessingException {
        Jwt jwt = Mockito.mock(Jwt.class);
        List<String> awgmentRolesList = new ArrayList<>();
        WebClient client = Mockito.mock(WebClient.class);
        String userResponce = "";
        Mockito.when(webClientWrapper.webclientRequest(any(), any(), any(), any())).thenReturn(userResponce);

        Assertions.assertThrows(AccessDeniedException.class, () -> jwtRoleConverter.convert(jwt));
    }
}
