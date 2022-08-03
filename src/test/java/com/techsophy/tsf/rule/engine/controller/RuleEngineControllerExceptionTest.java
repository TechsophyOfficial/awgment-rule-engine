package com.techsophy.tsf.rule.engine.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsophy.tsf.rule.engine.config.CustomFilter;
import com.techsophy.tsf.rule.engine.dto.RuleEngineRequestDTO;
import com.techsophy.tsf.rule.engine.exception.DMNExecutionException;
import com.techsophy.tsf.rule.engine.exception.GlobalExceptionHandler;
import com.techsophy.tsf.rule.engine.exception.InvalidInputException;
import com.techsophy.tsf.rule.engine.exception.UserDetailsIdNotFoundException;
import com.techsophy.tsf.rule.engine.utils.TokenUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.io.InputStream;
import static com.techsophy.tsf.rule.engine.constants.ErrorConstants.*;
import static com.techsophy.tsf.rule.engine.constants.RuleEngineConstants.TEST_ID;
import static com.techsophy.tsf.rule.engine.constants.RuleEngineConstants.*;
import static com.techsophy.tsf.rule.engine.constants.RuleEngineTestConstant.ID;
import static com.techsophy.tsf.rule.engine.constants.RuleEngineTestConstant.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles(TEST_ACTIVE_PROFILE)
@ExtendWith({MockitoExtension.class})
@AutoConfigureMockMvc(addFilters = false)
class RuleEngineControllerExceptionTest
{
    @MockBean
    TokenUtils mockTokenUtils;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    CustomFilter customFilter;
    @Mock
    private RuleEngineController mockRuleEngineController;

    @BeforeEach
    public void setUp()
    {
        mockMvc = MockMvcBuilders.standaloneSetup(new GlobalExceptionHandler(),mockRuleEngineController).addFilters(customFilter).build();
    }

   @Test
    void DMNExecutionExceptionTest() throws Exception
   {
       ObjectMapper objectMapperTest=new ObjectMapper();
       Mockito.when(mockTokenUtils.getIssuerFromToken(TOKEN)).thenReturn(TENANT);
       RuleEngineRequestDTO ruleEngineRequestDTO = new RuleEngineRequestDTO(ID,VARIABLES,VERSION);
       Mockito.when(mockRuleEngineController.executeDMN(ruleEngineRequestDTO)).thenThrow(new DMNExecutionException(DMN_ID_NOT_FOUND,DMN_ID_NOT_FOUND));
       RequestBuilder requestBuilderTest = MockMvcRequestBuilders.post(BASE_URL + VERSION_V1 + EXECUTE_DMN_URL)
               .content(objectMapperTest.writeValueAsString(ruleEngineRequestDTO))
               .contentType(MediaType.APPLICATION_JSON);
       MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isInternalServerError()).andReturn();
       assertEquals(500,mvcResult.getResponse().getStatus());
   }

    @Test
    void userDetailsNotFoundExceptionTest() throws Exception
    {
        InputStream inputStreamTest=new ClassPathResource(RULE_ENGINE_CONTENT_1).getInputStream();
        Mockito.when(mockTokenUtils.getIssuerFromToken(TOKEN)).thenReturn(TENANT);
        MockMultipartFile mockMultipartFile=new MockMultipartFile(CONTENT_VALUE,inputStreamTest);
        Mockito.when(mockRuleEngineController.saveDRDDetails(ID_VALUE,"test1",TEST_VERSION,mockMultipartFile,TEST_DEPLOYMENT_NAME)).thenThrow(new UserDetailsIdNotFoundException(USER_DETAILS_NOT_FOUND_EXCEPTION,USER_DETAILS_NOT_FOUND_EXCEPTION));
        RequestBuilder requestBuilderTest = MockMvcRequestBuilders.multipart(BASE_URL+VERSION_V1+DRD_URL)
                .file(mockMultipartFile)
                .param(SAVE_DRD_REQUESTPARAM_ID, TEST_ID).param(SAVE_DRD_REQUESTPARAM_NAME,"test1")
                .param(SAVE_DRD_REQUESTPARAM_VERSION,TEST_ID)
                .param(SAVE_DRD_REQUESTPARAM_DEPLOYMENTNAME,TEST_DEPLOYMENT_NAME)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isInternalServerError()).andReturn();
        assertEquals(500,mvcResult.getResponse().getStatus());
    }

    @Test
    void invalidInputExceptionTest() throws Exception
    {
        RuleEngineRequestDTO ruleEngineRequestDTO = new RuleEngineRequestDTO(ID,VARIABLES,VERSION);
        ObjectMapper objectMapperTest = new ObjectMapper();
        Mockito.when(mockTokenUtils.getIssuerFromToken(TOKEN)).thenReturn(TENANT);
        Mockito.when(mockRuleEngineController.executeDMN(ruleEngineRequestDTO)).thenThrow(new InvalidInputException(INVALID_CONTENT,INVALID_CONTENT));
        RequestBuilder requestBuilderTest = MockMvcRequestBuilders.post(BASE_URL + VERSION_V1+EXECUTE_DMN_URL)
                .content(objectMapperTest.writeValueAsString(ruleEngineRequestDTO))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isInternalServerError()).andReturn();
        assertEquals(500,mvcResult.getResponse().getStatus());
    }
}
