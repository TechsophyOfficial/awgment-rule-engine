package com.techsophy.tsf.rule.engine.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsophy.idgenerator.IdGeneratorImpl;
import com.techsophy.tsf.rule.engine.config.GlobalMessageSource;
import com.techsophy.tsf.rule.engine.dto.ListDataRequestDTO;
import com.techsophy.tsf.rule.engine.dto.RuleEngineRequestDTO;
import com.techsophy.tsf.rule.engine.dto.RuleEngineSchema;
import com.techsophy.tsf.rule.engine.entity.RuleEngineDefinition;
import com.techsophy.tsf.rule.engine.exception.RuleEngineNotFoundException;
import com.techsophy.tsf.rule.engine.repository.RuleEngineRepository;
import com.techsophy.tsf.rule.engine.service.impl.RuleEngineServiceImpl;
import com.techsophy.tsf.rule.engine.utils.UserDetails;
import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnDecisionRuleResult;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionImpl;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionRuleResultImpl;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableResultImpl;
import org.camunda.bpm.model.dmn.Dmn;
import org.camunda.bpm.model.dmn.DmnModelInstance;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.techsophy.tsf.rule.engine.constants.RuleEngineConstants.*;
import static com.techsophy.tsf.rule.engine.constants.RuleEngineTestConstant.TEST_ID;
import static com.techsophy.tsf.rule.engine.constants.RuleEngineTestConstant.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@EnableWebMvc
@ActiveProfiles(TEST_ACTIVE_PROFILE)
@ExtendWith({ SpringExtension.class})
class RuleEngineServiceTest
{
    @Mock
    MessageSource mockMessageSource;
    @Mock
    ObjectMapper mockObjectMapper;
    @Mock
    IdGeneratorImpl mockIdGenerator;
    @Mock
    GlobalMessageSource mockGlobalMessageSource;
    @Mock
    UserDetails mockUserDetails;
    @Mock
    RuleEngineRepository mockRuleEngineRepository;
    @Mock
    DmnModelInstance mockDmnModelInstance;
    @Mock(answer = Answers.CALLS_REAL_METHODS)
    DmnEngine mockDmnEngine;
    @Mock
    List<DmnDecision> mockDmnDecisions;
    @Mock
    DmnDecision mockDmnDecision;
    @Mock
    InputStream mockInputStream;
    @Mock
    RuleEngineDefinition mockRuleEngineDefinition;
    @Mock
    DmnDecisionTableResult mockDmnDecisionTableResult;
    @InjectMocks
    RuleEngineServiceImpl mockRuleEngineServiceImpl;

    List<Map<String, Object>> userList = new ArrayList<>();

    @BeforeEach
    public void init()
    {
        Map<String, Object> map = new HashMap<>();
        map.put(CREATED_BY_ID, NULL);
        map.put(CREATED_ON, NULL);
        map.put(UPDATED_BY_ID, NULL);
        map.put(UPDATED_ON, NULL);
        map.put(USER_ID, BIGINTEGER_ID);
        map.put(USER_NAME, USER_FIRST_NAME);
        map.put(FIRST_NAME, USER_LAST_NAME);
        map.put(LAST_NAME, USER_FIRST_NAME);
        map.put(MOBILE_NUMBER, NUMBER);
        map.put(EMAIL_ID, MAIL_ID);
        map.put(DEPARTMENT, NULL);
        userList.add(map);
    }

    @Test
    void saveDRDDetailsTest() throws JsonProcessingException
    {
        Mockito.when(mockUserDetails.getUserDetails())
                .thenReturn(userList);
        RuleEngineDefinition ruleEngineDefinitionTest=new RuleEngineDefinition(BigInteger.valueOf(Long.parseLong(ID_VALUE)),TEST_NAME,TEST_CONTENT,TEST_VERSION,TEST_DEPLOYMENT_NAME);
        Mockito.when(mockRuleEngineRepository.save(ruleEngineDefinitionTest)).thenReturn(ruleEngineDefinitionTest);
        mockRuleEngineServiceImpl.saveDRDDetails(ID_VALUE,TEST_NAME,TEST_VERSION,TEST_CONTENT,TEST_DEPLOYMENT_NAME);
        verify(mockRuleEngineRepository, times(1)).save(any());
    }

    @Test
    void executeDMNTest() throws IOException
    {
        InputStream inputStreamTest=new ClassPathResource(RULE_ENGINE_CONTENT_1).getInputStream();
        ObjectMapper objectMapperTest=new ObjectMapper();
        RuleEngineDefinition ruleEngineDefinitionTest=objectMapperTest.readValue(inputStreamTest,RuleEngineDefinition.class);
        Map<String,Object> bodyTest =new HashMap<>();
        bodyTest.put(TEST_ID,BIGINTEGER_ID);
        Map<String,Object> variables=new HashMap<>();
        variables.put(CIBIL,CIBIL_SCORE);
        variables.put(TICKET_TYPE,INSTALL);
        bodyTest.put(VARIABLES_KEY,variables);
        bodyTest.put(CIBIL,CIBIL_SCORE);
        RuleEngineRequestDTO ruleEngineRequestDTOTest=new RuleEngineRequestDTO(ID_VALUE, bodyTest,TEST_VERSION);
        Mockito.when(mockRuleEngineRepository.findByIdAndVersion(BigInteger.valueOf(Long.parseLong(ID_VALUE)),TEST_VERSION)).thenReturn(new RuleEngineDefinition(BigInteger.valueOf(Long.parseLong(ID_VALUE)),TEST_NAME,ruleEngineDefinitionTest.getContent(),TEST_VERSION,TEST_DEPLOYMENT_NAME));
        DmnDecisionImpl dmnDecision;
        try(MockedStatic<Dmn> dmnMockedStatic= Mockito.mockStatic(Dmn.class))
        {
            dmnMockedStatic.when(()->Dmn.readModelFromStream(any())).thenReturn(mockDmnModelInstance);
            dmnDecision=new DmnDecisionImpl();
            dmnDecision.setName(TEST_NAME);
            dmnDecision.setKey(TEST_KEY);
            List<DmnDecision> dmnDecisions=new ArrayList<>();
            dmnDecisions.add(dmnDecision);
            Mockito.when(mockRuleEngineDefinition.getContent()).thenReturn(ruleEngineDefinitionTest.getContent());
            Mockito.when(mockDmnEngine.parseDecisions(mockDmnModelInstance)).thenReturn(dmnDecisions);
            Mockito.when(mockDmnDecision.isDecisionTable()).thenReturn(true);
            DmnDecisionRuleResult dmnDecisionResult=new DmnDecisionRuleResultImpl();
            List<DmnDecisionRuleResult> dmnDecisionResults=new ArrayList<>();
            dmnDecisionResults.add(dmnDecisionResult);
            DmnDecisionTableResultImpl dmnDecisionTableResult=new DmnDecisionTableResultImpl(dmnDecisionResults);
            Mockito.when(mockDmnEngine.evaluateDecisionTable(dmnDecision, bodyTest)).thenReturn(dmnDecisionTableResult);
        }
        Object rulesResult=mockRuleEngineServiceImpl.executeDMN(ruleEngineRequestDTOTest);
        Assertions.assertNotNull(rulesResult);
    }

    @Test
    void getDRDDetailsByIdTest()
    {
        RuleEngineDefinition ruleEngineDefinition=new RuleEngineDefinition(BigInteger.valueOf(Long.parseLong(ID_VALUE)),TEST_NAME,TEST_CONTENT,TEST_VERSION,TEST_DEPLOYMENT_NAME);
        Mockito.when(mockRuleEngineRepository.findById(BigInteger.valueOf(Long.parseLong(ID_VALUE)))).thenReturn(ruleEngineDefinition);
        Mockito.when(mockRuleEngineRepository.existsById(BigInteger.valueOf(Long.parseLong(ID_VALUE)))).thenReturn(true);
        mockRuleEngineServiceImpl.getDRDDetailsById(ID_VALUE);
        verify(mockRuleEngineRepository, times(1)).findById(BigInteger.valueOf(Long.parseLong(ID_VALUE)));
    }

    @Test
    void getAllDmnIncludeContentTest()
    {
        RuleEngineDefinition ruleEngineDefinitionTest =new RuleEngineDefinition(BigInteger.valueOf(Long.parseLong(ID_VALUE)),TEST_NAME,TEST_CONTENT,TEST_VERSION,TEST_DEPLOYMENT_NAME);
        Mockito.when(mockRuleEngineRepository.findAll()).thenReturn(List.of(ruleEngineDefinitionTest));
        mockRuleEngineServiceImpl.getAllDMN(true);
        verify(mockRuleEngineRepository,times(1)).findAll();
    }

    @Test
    void getAllDmnNoContentTest()
    {
        RuleEngineDefinition ruleEngineDefinitionTest =new RuleEngineDefinition(BigInteger.valueOf(Long.parseLong(ID_VALUE)),TEST_NAME,TEST_CONTENT,TEST_VERSION,TEST_DEPLOYMENT_NAME);
        Mockito.when(mockRuleEngineRepository.findAll()).thenReturn(List.of(ruleEngineDefinitionTest));
        mockRuleEngineServiceImpl.getAllDMN(false);
        verify(mockRuleEngineRepository,times(1)).findAll();
    }

    @Test
    void updateDRDDetailsTest() throws IOException
    {
        Mockito.when(mockUserDetails.getUserDetails())
                .thenReturn(userList);
        Mockito.when(mockRuleEngineRepository.existsById(BigInteger.valueOf(Long.parseLong(ID_VALUE)))).thenReturn(true);
        RuleEngineSchema ruleEngineSchemaTest =new RuleEngineSchema(ID_VALUE, TEST_NAME,TEST_CONTENT,TEST_VERSION,TEST_DEPLOYMENT_NAME,
                CREATED_BY_ID_VALUE,CREATED_ON_INSTANT,UPDATED_BY_ID_VALUE,UPDATED_ON_INSTANT);
        RuleEngineDefinition existingRuleEngineDefinitionTest=new RuleEngineDefinition(BigInteger.valueOf(Long.parseLong(ID_VALUE)),TEST_NAME,TEST_CONTENT,TEST_VERSION,TEST_DEPLOYMENT_NAME);
        Mockito.when(mockRuleEngineRepository.findById(BigInteger.valueOf(Long.parseLong(ID_VALUE)))).thenReturn(existingRuleEngineDefinitionTest);
        mockRuleEngineServiceImpl.updateDRDDetails(ruleEngineSchemaTest);
        verify(mockRuleEngineRepository, times(1)).save(any());
    }

    @Test
    void updateDRDDetailsRuleEngineNotFoundExceptionTest() throws JsonProcessingException
    {
        Mockito.when(mockUserDetails.getUserDetails())
                .thenReturn(userList);
        Mockito.when(mockRuleEngineRepository.existsById(BigInteger.valueOf(Long.parseLong(ID_VALUE)))).thenReturn(false);
        RuleEngineSchema ruleEngineSchemaTest =new RuleEngineSchema(ID_VALUE, TEST_NAME,TEST_CONTENT,TEST_VERSION,TEST_DEPLOYMENT_NAME,
                CREATED_BY_ID_VALUE,CREATED_ON_INSTANT,UPDATED_BY_ID_VALUE,UPDATED_ON_INSTANT);
        Assertions.assertThrows(RuleEngineNotFoundException.class,()-> mockRuleEngineServiceImpl.updateDRDDetails(ruleEngineSchemaTest));
    }

    @Test
    void deleteFormByIdTest()
    {
        Mockito.when(mockRuleEngineRepository.existsById(BigInteger.valueOf(Long.parseLong(ID_VALUE)))).thenReturn(true);
        mockRuleEngineServiceImpl.deleteDRDDetails(ID_VALUE);
        verify(mockRuleEngineRepository,times(1)).deleteById(BigInteger.valueOf(Long.parseLong(ID_VALUE)));
    }

    @Test
    void evaluateDMNTest() throws IOException
    {
        InputStream inputStreamTest=new ClassPathResource(RULE_ENGINE_CONTENT_1).getInputStream();
        ObjectMapper objectMapperTest=new ObjectMapper();
        RuleEngineDefinition ruleEngineDefinitionTest=objectMapperTest.readValue(inputStreamTest,RuleEngineDefinition.class);
        Map<String,Object> bodyTest =new HashMap<>();
        bodyTest.put(TEST_ID,BIGINTEGER_ID);
        Map<String,Object> variables=new HashMap<>();
        variables.put(CIBIL,CIBIL_SCORE);
        variables.put(TICKET_TYPE,INSTALL);
        bodyTest.put(VARIABLES_KEY,variables);
        bodyTest.put(CIBIL,CIBIL_SCORE);
        ListDataRequestDTO listDataRequestDTOTest=new ListDataRequestDTO(ID_VALUE,TEST_VERSION,List.of(variables));
        Mockito.when(mockRuleEngineRepository.findByIdAndVersion(BigInteger.valueOf(Long.parseLong(ID_VALUE)),TEST_VERSION)).thenReturn(new RuleEngineDefinition(BigInteger.valueOf(Long.parseLong(ID_VALUE)),TEST_NAME,ruleEngineDefinitionTest.getContent(),TEST_VERSION,TEST_DEPLOYMENT_NAME));
        DmnDecisionImpl dmnDecision;
        try(MockedStatic<Dmn> dmnMockedStatic= Mockito.mockStatic(Dmn.class))
        {
            dmnMockedStatic.when(()->Dmn.readModelFromStream(any())).thenReturn(mockDmnModelInstance);
            dmnDecision=new DmnDecisionImpl();
            dmnDecision.setName(TEST_NAME);
            dmnDecision.setKey(TEST_KEY);
            List<DmnDecision> dmnDecisions=new ArrayList<>();
            dmnDecisions.add(dmnDecision);
            Mockito.when(mockRuleEngineDefinition.getContent()).thenReturn(ruleEngineDefinitionTest.getContent());
            Mockito.when(mockDmnEngine.parseDecisions(mockDmnModelInstance)).thenReturn(dmnDecisions);
            Mockito.when(mockDmnDecision.isDecisionTable()).thenReturn(true);
            DmnDecisionRuleResult dmnDecisionResult=new DmnDecisionRuleResultImpl();
            List<DmnDecisionRuleResult> dmnDecisionResults=new ArrayList<>();
            dmnDecisionResults.add(dmnDecisionResult);
            DmnDecisionTableResultImpl dmnDecisionTableResult=new DmnDecisionTableResultImpl(dmnDecisionResults);
            Mockito.when(mockDmnEngine.evaluateDecisionTable(dmnDecision, bodyTest)).thenReturn(dmnDecisionTableResult);
        }
        Object obj=mockRuleEngineServiceImpl.evaluateDMN(listDataRequestDTOTest);
        Assertions.assertNotNull(obj);
    }
}

