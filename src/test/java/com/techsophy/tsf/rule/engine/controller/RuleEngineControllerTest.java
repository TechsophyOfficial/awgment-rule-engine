package com.techsophy.tsf.rule.engine.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsophy.tsf.rule.engine.config.CustomFilter;
import com.techsophy.tsf.rule.engine.dto.ListDataRequestDTO;
import com.techsophy.tsf.rule.engine.dto.RuleEngineRequestDTO;
import com.techsophy.tsf.rule.engine.dto.RuleEngineResponse;
import com.techsophy.tsf.rule.engine.dto.RuleEngineSchema;
import com.techsophy.tsf.rule.engine.service.RuleEngineService;
import com.techsophy.tsf.rule.engine.utils.TokenUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import static com.techsophy.tsf.rule.engine.constants.RuleEngineConstants.*;
import static com.techsophy.tsf.rule.engine.constants.RuleEngineTestConstant.ID;
import static com.techsophy.tsf.rule.engine.constants.RuleEngineTestConstant.UPDATED_BY_NAME;
import static com.techsophy.tsf.rule.engine.constants.RuleEngineTestConstant.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles(TEST_ACTIVE_PROFILE)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class RuleEngineControllerTest
{
    private static  final SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor jwtSaveOrUpdate = jwt().authorities(new SimpleGrantedAuthority(AWGMENT_RULE_ENGINE_CREATE_OR_UPDATE));
    private static  final SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor jwtRead = jwt().authorities(new SimpleGrantedAuthority(AWGMENT_RULE_ENGINE_READ));
    private static  final SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor jwtDelete = jwt().authorities(new SimpleGrantedAuthority(AWGMENT_RULE_ENGINE_DELETE));

    @MockBean
    TokenUtils mockTokenUtils;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    RuleEngineService mockRuleEngineService;
    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    CustomFilter customFilter;

    @BeforeEach
    public void setUp()
    {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilters(customFilter)
                .apply(springSecurity())
                .build();
    }

    @Test
    void saveDRDDetailsTestWithContent() throws Exception
    {
        InputStream inputStreamTest=new ClassPathResource(RULE_ENGINE_CONTENT_1).getInputStream();
        Mockito.when(mockTokenUtils.getIssuerFromToken(TOKEN)).thenReturn(TENANT);
        Mockito.when(mockRuleEngineService.saveDRDDetails("1","test",1,"content","abc")).thenReturn(new RuleEngineResponse("1","test",1));
        MockMultipartFile mockMultipartFile=new MockMultipartFile("content",inputStreamTest);
        RequestBuilder requestBuilderTest = MockMvcRequestBuilders.multipart(BASE_URL+VERSION_V1+DRD_URL)
                .file(mockMultipartFile)
                .param(SAVE_DRD_REQUESTPARAM_ID,"1").param(SAVE_DRD_REQUESTPARAM_NAME,"test1")
                .param(SAVE_DRD_REQUESTPARAM_VERSION,"1")
                .param(SAVE_DRD_REQUESTPARAM_DEPLOYMENTNAME,"abc")
                .with(jwtSaveOrUpdate)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isOk()).andReturn();
        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    void executeDMNTest() throws Exception
    {
        RuleEngineRequestDTO ruleEngineRequestDTO = new RuleEngineRequestDTO(ID,VARIABLES,VERSION);
        ObjectMapper objectMapperTest = new ObjectMapper();
        Mockito.when(mockTokenUtils.getIssuerFromToken(TOKEN)).thenReturn(TENANT);
        Mockito.when(mockRuleEngineService.executeDMN(ruleEngineRequestDTO)).thenReturn(List.of());
        RequestBuilder requestBuilderTest = MockMvcRequestBuilders.post(BASE_URL + VERSION_V1 + EXECUTE_DMN_URL)
                .content(objectMapperTest.writeValueAsString(ruleEngineRequestDTO))
                .with(jwtSaveOrUpdate)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().is2xxSuccessful()).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void getDRDDetailsByIdTest() throws Exception
    {
        Mockito.when(mockTokenUtils.getIssuerFromToken(TOKEN)).thenReturn(TENANT);
        Mockito.when(mockRuleEngineService.getDRDDetailsById(ID)).thenReturn(new RuleEngineSchema(ID_VALUE, TEST_NAME,TEST_CONTENT,TEST_VERSION,TEST_DEPLOYMENT_NAME,
                CREATED_BY_ID_VALUE,CREATED_ON_INSTANT,CREATED_BY_NAME,UPDATED_BY_ID_VALUE,UPDATED_ON_INSTANT,UPDATED_BY_NAME));
        RequestBuilder requestBuilderTest = MockMvcRequestBuilders.get(BASE_URL + VERSION_V1 + DMN_BY_ID_URL, 1)
                .with(jwtRead)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isOk()).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void getAllDmnTest() throws Exception
    {
        Mockito.when(mockTokenUtils.getIssuerFromToken(TOKEN)).thenReturn(TENANT);
        boolean includeContent=false;
        RuleEngineSchema ruleEngineSchema=new RuleEngineSchema(ID_VALUE,TEST_NAME,TEST_CONTENT,TEST_VERSION,TEST_DEPLOYMENT_NAME,
                CREATED_BY_ID_VALUE,CREATED_ON_INSTANT,CREATED_BY_NAME,UPDATED_BY_ID_VALUE,UPDATED_ON_INSTANT,UPDATED_BY_NAME);
        Mockito.when(mockRuleEngineService.getAllDMN(includeContent)).thenReturn(List.of(ruleEngineSchema));
        RequestBuilder requestBuilderTest = MockMvcRequestBuilders.get(BASE_URL + VERSION_V1 + DRD_URL).param(INCLUDE_CONTENT,"true")
                .with(jwtRead)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isOk()).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void updateDRDDetailsTest()throws  Exception
    {
        ObjectMapper objectMapperTest = new ObjectMapper();
        InputStream inputStreamTest=new ClassPathResource(RULE_MODEL_CONTENT).getInputStream();
        RuleEngineSchema ruleEngineSchemaTest=objectMapperTest.readValue(inputStreamTest,RuleEngineSchema.class);
        Mockito.when(mockTokenUtils.getIssuerFromToken(TOKEN)).thenReturn(TENANT);
        Mockito.when(mockRuleEngineService.updateDRDDetails(ruleEngineSchemaTest)).thenReturn(new RuleEngineResponse(ID_VALUE,TEST_NAME,TEST_VERSION));
        MockHttpServletRequestBuilder requestBuilderTest = MockMvcRequestBuilders.put(BASE_URL + VERSION_V1 + DRD_URL)
                .content(objectMapperTest.writeValueAsString(ruleEngineSchemaTest))
                .contentType(MediaType.APPLICATION_JSON)
                .with(jwtSaveOrUpdate);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isOk()).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void deleteDRDDetailsTest() throws Exception
    {
        Mockito.when(mockTokenUtils.getIssuerFromToken(TOKEN)).thenReturn(TENANT);
        doNothing().when(mockRuleEngineService).deleteDRDDetails(ID_VALUE);
        RequestBuilder requestBuilderTest=MockMvcRequestBuilders.delete(BASE_URL + VERSION_V1 + DMN_BY_ID_URL,1)
                .with(jwtDelete)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isOk()).andReturn();
        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    void evaluateDMNTest() throws Exception
    {
        ListDataRequestDTO listDataRequestDTO=new ListDataRequestDTO("1",1, List.of(Map.of("cibil",900)));
        ObjectMapper objectMapperTest = new ObjectMapper();
        Mockito.when(mockTokenUtils.getIssuerFromToken(TOKEN)).thenReturn(TENANT);
        Mockito.when(mockRuleEngineService.evaluateDMN(listDataRequestDTO)).thenReturn(List.of());
        RequestBuilder requestBuilderTest = MockMvcRequestBuilders.post(BASE_URL + VERSION_V1 + EVALUATE)
                .content(objectMapperTest.writeValueAsString(listDataRequestDTO))
                .with(jwtSaveOrUpdate)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().is2xxSuccessful()).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
    }
}


