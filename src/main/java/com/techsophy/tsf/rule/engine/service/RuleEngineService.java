package com.techsophy.tsf.rule.engine.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techsophy.tsf.rule.engine.dto.ListDataRequestDTO;
import com.techsophy.tsf.rule.engine.dto.RuleEngineRequestDTO;
import com.techsophy.tsf.rule.engine.dto.RuleEngineResponse;
import com.techsophy.tsf.rule.engine.dto.RuleEngineSchema;
import java.util.List;
import java.util.Map;

public interface RuleEngineService
{
    RuleEngineResponse saveDRDDetails(String id, String name, Integer version, String content, String deploymentName) throws JsonProcessingException;
    Object executeDMN(RuleEngineRequestDTO requestDTO);
    RuleEngineSchema getDRDDetailsById(String id);
    List<RuleEngineSchema> getAllDMN(boolean includeRuleContent);
    RuleEngineResponse updateDRDDetails(RuleEngineSchema ruleEngineSchema) throws JsonProcessingException;
    void deleteDRDDetails(String id);
    List<Map<String, Object>> evaluateDMN(ListDataRequestDTO listDataRequestDTO);
}
