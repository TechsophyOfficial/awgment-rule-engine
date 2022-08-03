package com.techsophy.tsf.rule.engine.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsophy.idgenerator.IdGeneratorImpl;
import com.techsophy.tsf.rule.engine.config.GlobalMessageSource;
import com.techsophy.tsf.rule.engine.config.LocaleConfig;
import com.techsophy.tsf.rule.engine.dto.ListDataRequestDTO;
import com.techsophy.tsf.rule.engine.dto.RuleEngineRequestDTO;
import com.techsophy.tsf.rule.engine.dto.RuleEngineSchema;
import com.techsophy.tsf.rule.engine.entity.RuleEngineDefinition;
import com.techsophy.tsf.rule.engine.exception.DMNExecutionException;
import com.techsophy.tsf.rule.engine.exception.RuleEngineNotFoundException;
import com.techsophy.tsf.rule.engine.exception.UserDetailsIdNotFoundException;
import com.techsophy.tsf.rule.engine.repository.RuleEngineRepository;
import com.techsophy.tsf.rule.engine.service.impl.RuleEngineServiceImpl;
import com.techsophy.tsf.rule.engine.utils.UserDetails;
import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.model.dmn.DmnModelInstance;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.techsophy.tsf.rule.engine.constants.RuleEngineConstants.*;
import static com.techsophy.tsf.rule.engine.constants.RuleEngineTestConstant.EMPTY_STRING;
import static com.techsophy.tsf.rule.engine.constants.RuleEngineTestConstant.TEST_ID;
import static com.techsophy.tsf.rule.engine.constants.RuleEngineTestConstant.UPDATED_BY_NAME;
import static com.techsophy.tsf.rule.engine.constants.RuleEngineTestConstant.*;

@ActiveProfiles(TEST_ACTIVE_PROFILE)
@ExtendWith({SpringExtension.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class RuleEngineServiceExceptionTest
{
    @Mock
    UserDetails mockUserDetails;
    @Mock
    RuleEngineRepository mockRuleEngineRepository;
    @Mock
    ObjectMapper mockObjectMapper;
    @Mock
    IdGeneratorImpl idGeneratorImpl;
    @Mock
    GlobalMessageSource mockGlobalMessageSource;
    @Mock
    LocaleConfig mockLocaleConfig;
    @Mock
    DmnModelInstance mockDmnModelInstance;
    @Mock(answer = Answers.CALLS_REAL_METHODS)
    DmnEngine mockDmnEngine;
    @Mock
    List<DmnDecision> mockDmnDecisions;
    @Mock
    DmnDecision mockDmnDecision;
    @Mock
    RuleEngineDefinition mockRuleEngineDefinition;
    @InjectMocks
    RuleEngineServiceImpl mockRuleEngineServiceImpl;

    List<Map<String, Object>> userList = new ArrayList<>();

    @BeforeEach
    public void init()
    {
        Map<String, Object> map = new HashMap<>();
        map.put(CREATED_BY_ID, NULL);
        map.put(CREATED_BY_NAME, NULL);
        map.put(CREATED_ON, NULL);
        map.put(UPDATED_BY_ID, NULL);
        map.put(UPDATED_BY_NAME, NULL);
        map.put(UPDATED_ON, NULL);
        map.put(TEST_ID, EMPTY_STRING);
        map.put(USER_NAME, USER_FIRST_NAME);
        map.put(FIRST_NAME, USER_LAST_NAME);
        map.put(LAST_NAME, USER_FIRST_NAME);
        map.put(MOBILE_NUMBER, NUMBER);
        map.put(EMAIL_ID, MAIL_ID);
        map.put(DEPARTMENT, NULL);
        userList.add(map);
    }

    @Test
    void saveDRDDetailsUserDetailsExceptionTest() throws JsonProcessingException
    {
        Mockito.when(mockUserDetails.getUserDetails())
                .thenReturn(userList);
        Assertions.assertThrows(UserDetailsIdNotFoundException.class,()-> mockRuleEngineServiceImpl.saveDRDDetails(TEST_ID,TEST_NAME,TEST_VERSION,TEST_CONTENT,TEST_DEPLOYMENT_NAME));
    }

    @Test
    void executeDMNTestExceptionTest()
    {
        Map<String,Object> bodyTest =new HashMap<>();
        bodyTest.put(TEST_ID,EMPTY_STRING);
        Map<String,Object> variables=new HashMap<>();
        variables.put(CIBIL,CIBIL_SCORE);
        variables.put(TICKET_TYPE,INSTALL);
        bodyTest.put(VARIABLES_KEY,variables);
        bodyTest.put(CIBIL,CIBIL_SCORE);
        RuleEngineRequestDTO ruleEngineRequestDTOTest=new RuleEngineRequestDTO(EMPTY_STRING, bodyTest,TEST_VERSION);
        Assertions.assertThrows(DMNExecutionException.class,()-> mockRuleEngineServiceImpl.executeDMN(ruleEngineRequestDTOTest));
    }

    @Test
    void getDRDDetailsByIdRuleEngineNotFoundExceptionTest()
    {
        Mockito.when(mockRuleEngineRepository.existsById(BigInteger.valueOf(Long.parseLong(ID_VALUE)))).thenReturn(false);
        Assertions.assertThrows(RuleEngineNotFoundException.class,()-> mockRuleEngineServiceImpl.getDRDDetailsById(ID_VALUE));
    }

    @Test
    void updateDRDDetailsTest() throws JsonProcessingException
    {
        Mockito.when(mockUserDetails.getUserDetails())
                .thenReturn(userList);
        RuleEngineSchema ruleEngineSchemaTest =new RuleEngineSchema(ID_VALUE, TEST_NAME,TEST_CONTENT,TEST_VERSION,TEST_DEPLOYMENT_NAME,
                CREATED_BY_ID_VALUE,CREATED_ON_INSTANT,CREATED_BY_NAME,UPDATED_BY_ID_VALUE,UPDATED_ON_INSTANT,UPDATED_BY_NAME);
        Assertions.assertThrows(UserDetailsIdNotFoundException.class,()-> mockRuleEngineServiceImpl.updateDRDDetails(ruleEngineSchemaTest));
    }

    @Test
    void deleteDRDDetailsByIdTest()
    {
        Mockito.when(mockRuleEngineRepository.existsById(BigInteger.valueOf(Long.parseLong(ID_VALUE)))).thenReturn(false);
        Assertions.assertThrows(RuleEngineNotFoundException.class,()-> mockRuleEngineServiceImpl.deleteDRDDetails(ID_VALUE));
    }

    @Test
    void evaluateDMNExecutionExceptionTest()
    {
        Map<String,Object> bodyTest =new HashMap<>();
        Map<String,Object> variables=new HashMap<>();
        variables.put(CIBIL,CIBIL_SCORE);
        variables.put(TICKET_TYPE,INSTALL);
        bodyTest.put(VARIABLES_KEY,variables);
        bodyTest.put(CIBIL,CIBIL_SCORE);
        ListDataRequestDTO listDataRequestDTO=new ListDataRequestDTO(null,null,List.of(variables));
        Assertions.assertThrows(DMNExecutionException.class,()-> mockRuleEngineServiceImpl.evaluateDMN(listDataRequestDTO));
    }
}
