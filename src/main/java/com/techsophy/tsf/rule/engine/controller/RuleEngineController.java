package com.techsophy.tsf.rule.engine.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techsophy.tsf.rule.engine.dto.ListDataRequestDTO;
import com.techsophy.tsf.rule.engine.dto.RuleEngineRequestDTO;
import com.techsophy.tsf.rule.engine.dto.RuleEngineResponse;
import com.techsophy.tsf.rule.engine.dto.RuleEngineSchema;
import com.techsophy.tsf.rule.engine.model.ApiResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import static com.techsophy.tsf.rule.engine.constants.RuleEngineConstants.*;

@RequestMapping(BASE_URL+ VERSION_V1)
@RestController
@Validated
public interface RuleEngineController
{
    @PostMapping(value = DRD_URL, produces = MEDIA_TYPE_APPLICATION_JSON)
    @PreAuthorize(CREATE_OR_ALL_ACCESS)
    ApiResponse<RuleEngineResponse> saveDRDDetails(@RequestParam(SAVE_DRD_REQUESTPARAM_ID)  @NotNull String id,
                                      @RequestParam(SAVE_DRD_REQUESTPARAM_NAME) @NotEmpty @NotNull String name,
                                      @RequestParam(SAVE_DRD_REQUESTPARAM_VERSION) @NotNull  Integer version,
                                      @RequestParam(SAVE_DRD_REQUESTPARAM_CONTENT)     MultipartFile content,
                                      @RequestParam(SAVE_DRD_REQUESTPARAM_DEPLOYMENTNAME) @NotEmpty @NotNull String deploymentName) throws IOException;

    @PostMapping(EXECUTE_DMN_URL)
    @PreAuthorize(CREATE_OR_ALL_ACCESS)
    ApiResponse executeDMN(@RequestBody RuleEngineRequestDTO requestDTO) throws IOException;

    @GetMapping(DMN_BY_ID_URL)
    @PreAuthorize(READ_OR_ALL_ACCESS)
    ApiResponse<RuleEngineSchema> getDRDDetailsById(@PathVariable String id);

    @GetMapping(DRD_URL)
    @PreAuthorize(READ_OR_ALL_ACCESS)
    ApiResponse<List<RuleEngineSchema>> getAllDMN(@RequestParam(value=INCLUDE_CONTENT,required = false,defaultValue = "true") boolean includeContent);

    @PutMapping(value = DRD_URL, produces = MEDIA_TYPE_APPLICATION_JSON)
    @PreAuthorize(CREATE_OR_ALL_ACCESS)
    ApiResponse<RuleEngineResponse> updateDRDDetails(@RequestBody RuleEngineSchema ruleEngineSchema) throws JsonProcessingException;

    @DeleteMapping(value = DMN_BY_ID_URL, produces = MEDIA_TYPE_APPLICATION_JSON)
    @PreAuthorize(DELETE_OR_ALL_ACCESS)
    ApiResponse<Void> deleteDRDDetails(@PathVariable String id);

    @PostMapping(EVALUATE)
    @PreAuthorize(CREATE_OR_ALL_ACCESS)
    ApiResponse<List<Map<String, Object>>> evaluateDMN(@RequestBody ListDataRequestDTO listDataRequestDTO);
}
