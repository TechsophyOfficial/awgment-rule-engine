package com.techsophy.tsf.rule.engine.controller.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techsophy.tsf.rule.engine.config.GlobalMessageSource;
import com.techsophy.tsf.rule.engine.controller.RuleEngineController;
import com.techsophy.tsf.rule.engine.dto.ListDataRequestDTO;
import com.techsophy.tsf.rule.engine.dto.RuleEngineRequestDTO;
import com.techsophy.tsf.rule.engine.dto.RuleEngineResponse;
import com.techsophy.tsf.rule.engine.dto.RuleEngineSchema;
import com.techsophy.tsf.rule.engine.exception.DMNExecutionException;
import com.techsophy.tsf.rule.engine.exception.InvalidInputException;
import com.techsophy.tsf.rule.engine.model.ApiResponse;
import com.techsophy.tsf.rule.engine.service.RuleEngineService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;
import static com.techsophy.tsf.rule.engine.constants.ErrorConstants.*;
import static com.techsophy.tsf.rule.engine.constants.RuleEngineConstants.*;

@Slf4j
@RestController
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class RuleEngineControllerImpl  implements RuleEngineController
{
	private RuleEngineService ruleEngineService;
	private GlobalMessageSource globalMessageSource;

	@Override
	public ApiResponse<RuleEngineResponse> saveDRDDetails(String id, String name, Integer version, MultipartFile content, String deploymentName)
	{
		try
		{
			RuleEngineResponse ruleEngineResponse=ruleEngineService.saveDRDDetails(id,name,version,new String(content.getBytes()),deploymentName);
			return new ApiResponse<>(ruleEngineResponse,true,globalMessageSource.get(SAVE_DRD_SUCCESS));
		}
		catch (Exception e)
		{
			throw new InvalidInputException(INVALID_CONTENT,globalMessageSource.get(DMN_CONTENT_IS_INVALID));
		}
	}

	@Override
	public ApiResponse executeDMN(RuleEngineRequestDTO requestDTO)
	{
		Object executeDMNResponse;
		try
		{
			executeDMNResponse= ruleEngineService.executeDMN(requestDTO);
		}
		catch (Exception e)
		{
			throw new DMNExecutionException(UNABLE_TO_EXECUTE_DMN,globalMessageSource.get(UNABLE_TO_EXECUTE_DMN));
		}
		return new ApiResponse(executeDMNResponse,true,globalMessageSource.get(EXECUTE_DMN_SUCCESS));
	}

	@Override
	public ApiResponse<RuleEngineSchema> getDRDDetailsById(String id)
	{
		return  new ApiResponse<>(ruleEngineService.getDRDDetailsById(id),true,globalMessageSource.get(GET_DMN_SUCCESS));
	}

	@Override
	public ApiResponse<List<RuleEngineSchema>> getAllDMN(boolean includeRuleContent)
	{
		return new ApiResponse<>(ruleEngineService.getAllDMN(includeRuleContent),true,globalMessageSource.get(GET_DMN_SUCCESS));
	}

	@Override
	public ApiResponse<RuleEngineResponse> updateDRDDetails(RuleEngineSchema ruleEngineSchema) throws JsonProcessingException
	{
		return new ApiResponse<>(ruleEngineService.updateDRDDetails(ruleEngineSchema),true,globalMessageSource.get(UPDATE_DMN_SUCCESS));
	}

	@Override
	public ApiResponse<Void> deleteDRDDetails(String id)
	{
		ruleEngineService.deleteDRDDetails(id);
		return new ApiResponse<>(null,true,globalMessageSource.get(DRD_DELETED_SUCCESSFULLY));
	}

	@Override
	public ApiResponse<List<Map<String, Object>>> evaluateDMN(ListDataRequestDTO listDataRequestDTO)
	{
		return new ApiResponse<>(this.ruleEngineService.evaluateDMN(listDataRequestDTO), true,globalMessageSource.get(DRD_EVALUATION_SUCCESS));
	}
}

