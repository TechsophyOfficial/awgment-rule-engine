package com.techsophy.tsf.rule.engine.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techsophy.tsf.rule.engine.config.GlobalMessageSource;
import com.techsophy.tsf.rule.engine.dto.ListDataRequestDTO;
import com.techsophy.tsf.rule.engine.dto.RuleEngineRequestDTO;
import com.techsophy.tsf.rule.engine.dto.RuleEngineResponse;
import com.techsophy.tsf.rule.engine.dto.RuleEngineSchema;
import com.techsophy.tsf.rule.engine.entity.RuleEngineDefinition;
import com.techsophy.tsf.rule.engine.exception.DMNExecutionException;
import com.techsophy.tsf.rule.engine.exception.InvalidInputException;
import com.techsophy.tsf.rule.engine.exception.RuleEngineNotFoundException;
import com.techsophy.tsf.rule.engine.exception.UserDetailsIdNotFoundException;
import com.techsophy.tsf.rule.engine.repository.RuleEngineRepository;
import com.techsophy.tsf.rule.engine.service.RuleEngineService;
import com.techsophy.tsf.rule.engine.utils.UserDetails;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.dmn.engine.*;
import org.camunda.bpm.model.dmn.Dmn;
import org.camunda.bpm.model.dmn.DmnModelInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static com.techsophy.tsf.rule.engine.constants.ErrorConstants.*;
import static com.techsophy.tsf.rule.engine.constants.RuleEngineConstants.*;

@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class RuleEngineServiceImpl implements RuleEngineService
{
	private RuleEngineRepository ruleEngineRepository;
	private GlobalMessageSource globalMessageSource;
	private final UserDetails userDetails;

	public RuleEngineResponse saveDRDDetails(String id, String name, Integer version, String content, String deploymentName) throws JsonProcessingException
	{
		Map<String,Object> loggedInUserDetails =userDetails.getUserDetails().get(0);
		if (StringUtils.isEmpty(loggedInUserDetails.get(ID).toString()))
		{
			throw new UserDetailsIdNotFoundException(LOGGED_IN_USER_ID_NOT_FOUND,globalMessageSource.get(LOGGED_IN_USER_ID_NOT_FOUND,loggedInUserDetails.get(ID).toString()));
		}
		BigInteger loggedInUserId = BigInteger.valueOf(Long.parseLong(loggedInUserDetails.get(ID).toString()));
		RuleEngineDefinition ruleEngineDefinition=new RuleEngineDefinition(BigInteger.valueOf(Long.parseLong(id)),
				name,content,version,deploymentName);
		ruleEngineDefinition.setCreatedById(loggedInUserId);
		ruleEngineDefinition.setCreatedOn(Instant.now());
		ruleEngineDefinition.setUpdatedById(loggedInUserId);
		ruleEngineDefinition.setUpdatedOn(Instant.now());
		ruleEngineDefinition.setCreatedByName(loggedInUserDetails.get(USER_DEFINITION_FIRST_NAME).toString() + SPACE + loggedInUserDetails.get(USER_DEFINITION_LAST_NAME).toString());
		ruleEngineDefinition.setUpdatedByName(loggedInUserDetails.get(USER_DEFINITION_FIRST_NAME).toString() + SPACE + loggedInUserDetails.get(USER_DEFINITION_LAST_NAME).toString());
		this.ruleEngineRepository.save(ruleEngineDefinition);
		return new RuleEngineResponse(String.valueOf(ruleEngineDefinition.getId()),ruleEngineDefinition.getDeploymentName(),ruleEngineDefinition.getVersion());
	}

	public Object executeDMN(RuleEngineRequestDTO requestDTO)
	{
		RuleEngineDefinition ruleEngineDefinition;
		if(StringUtils.isNotEmpty(requestDTO.getId()))
		{
			ruleEngineDefinition =(requestDTO.getVersion()!=null)?
				(ruleEngineRepository.findByIdAndVersion(BigInteger.valueOf(Long.parseLong(requestDTO.getId())),requestDTO.getVersion())):
				(ruleEngineRepository.findById(BigInteger.valueOf(Long.parseLong(requestDTO.getId()))));
		}
		else
		{
			throw new DMNExecutionException(UNABLE_TO_EXECUTE_DMN,globalMessageSource.get(UNABLE_TO_EXECUTE_DMN));
		}
		Object rulesResult=null;
		if(!ruleEngineDefinition.getId().equals(BigInteger.ZERO) && StringUtils.isNotEmpty(ruleEngineDefinition.getContent()))
		{
			String dmnContent= ruleEngineDefinition.getContent();
			DmnEngine dmnEngine= DmnEngineConfiguration.createDefaultDmnEngineConfiguration().buildEngine();
			try(InputStream inputStream = new ByteArrayInputStream(dmnContent.getBytes(StandardCharsets.UTF_8)))
			{
				DmnModelInstance dmnModelInstance = Dmn.readModelFromStream(inputStream);
				List<DmnDecision> decisions = dmnEngine.parseDecisions(dmnModelInstance);
				for(DmnDecision decision:decisions)
				{
					if (decision.isDecisionTable())
					{
						DmnDecisionTableResult result = dmnEngine.evaluateDecisionTable(decision,requestDTO.getVariables());
						if(result!= null)
						{
							rulesResult = result.getResultList();
						}
					}
					else
						{
						DmnDecisionResult result=dmnEngine.evaluateDecision(decision,requestDTO.getVariables());
						rulesResult = result.getSingleResult().getSingleEntry();
						}
				}
				}
			catch (Exception e)
			{
				throw new InvalidInputException(DMN_CONTENT_IS_INVALID,globalMessageSource.get(DMN_CONTENT_IS_INVALID));
			}
			}
		return rulesResult;
	}

	public RuleEngineSchema getDRDDetailsById(String id)
	{
		if(!ruleEngineRepository.existsById(BigInteger.valueOf(Long.parseLong(id))))
		{
			throw  new RuleEngineNotFoundException(DMN_ID_NOT_FOUND,globalMessageSource.get(DMN_ID_NOT_FOUND,id));
		}
		RuleEngineDefinition ruleEngineDefinition = this.ruleEngineRepository.findById(BigInteger.valueOf(Long.parseLong(id)));
		return new RuleEngineSchema(String.valueOf(ruleEngineDefinition.getId())
				,ruleEngineDefinition.getName(),ruleEngineDefinition.getContent()
				,ruleEngineDefinition.getVersion(),ruleEngineDefinition.getDeploymentName()
				,String.valueOf(ruleEngineDefinition.getCreatedById()),ruleEngineDefinition.getCreatedOn(),ruleEngineDefinition.getCreatedByName()
		,String.valueOf(ruleEngineDefinition.getUpdatedById()),ruleEngineDefinition.getUpdatedOn(),ruleEngineDefinition.getUpdatedByName());
	}

	public List<RuleEngineSchema> getAllDMN(boolean includeRuleContent)
	{
		List<RuleEngineSchema> ruleEngineResponseList=new ArrayList<>();
		if(includeRuleContent)
		{
			List<RuleEngineDefinition> ruleEngineDefinitions= ruleEngineRepository.findAll();
			for(RuleEngineDefinition ruleEngineDefinition:ruleEngineDefinitions)
			{
				RuleEngineSchema ruleEngineSchema=new RuleEngineSchema(String.valueOf(ruleEngineDefinition.getId()),
						ruleEngineDefinition.getName(),ruleEngineDefinition.getContent()
						,ruleEngineDefinition.getVersion(),ruleEngineDefinition.getDeploymentName()
				,String.valueOf(ruleEngineDefinition.getCreatedById()),ruleEngineDefinition.getCreatedOn(),ruleEngineDefinition.getCreatedByName()
						,String.valueOf(ruleEngineDefinition.getUpdatedById()),ruleEngineDefinition.getUpdatedOn(),ruleEngineDefinition.getUpdatedByName());
				ruleEngineResponseList.add(ruleEngineSchema);
			}
			return ruleEngineResponseList;
		}
		else
		{
			List<RuleEngineDefinition> ruleEngineDefRepositories= ruleEngineRepository.findAll();
			for(RuleEngineDefinition ruleEngineDefinition:ruleEngineDefRepositories)
			{
				RuleEngineSchema ruleEngineSchema=new RuleEngineSchema(String.valueOf(ruleEngineDefinition.getId()),ruleEngineDefinition.getName(),null,
						ruleEngineDefinition.getVersion(),ruleEngineDefinition.getDeploymentName()
				,String.valueOf(ruleEngineDefinition.getCreatedById()),ruleEngineDefinition.getCreatedOn(),ruleEngineDefinition.getCreatedByName()
						,String.valueOf(ruleEngineDefinition.getUpdatedById()),ruleEngineDefinition.getUpdatedOn(),ruleEngineDefinition.getUpdatedByName());
				ruleEngineResponseList.add(ruleEngineSchema);
			}
			return  ruleEngineResponseList;
		}
	}

	public RuleEngineResponse updateDRDDetails(RuleEngineSchema ruleEngineSchema) throws JsonProcessingException
	{
		Map<String,Object> loggedInUserDetails =userDetails.getUserDetails().get(0);
		if (StringUtils.isEmpty(loggedInUserDetails.get(ID).toString()))
		{
			throw new UserDetailsIdNotFoundException(LOGGED_IN_USER_ID_NOT_FOUND,globalMessageSource.get(LOGGED_IN_USER_ID_NOT_FOUND,loggedInUserDetails.get(ID).toString()));
		}
		BigInteger loggedInUserId = BigInteger.valueOf(Long.parseLong(loggedInUserDetails.get(ID).toString()));
		String id=ruleEngineSchema.getId();
		if(!ruleEngineRepository.existsById(BigInteger.valueOf(Long.parseLong(id))))
		{
			throw new RuleEngineNotFoundException(DMN_ID_NOT_FOUND,globalMessageSource.get(DMN_ID_NOT_FOUND));
		}
		RuleEngineDefinition existingRuleDefinition=ruleEngineRepository.findById(BigInteger.valueOf(Long.parseLong(id)));
		RuleEngineDefinition modifiedRuleDefinition=new RuleEngineDefinition(existingRuleDefinition.getId()
				,ruleEngineSchema.getName(),ruleEngineSchema.getContent(),ruleEngineSchema.getVersion(),ruleEngineSchema.getDeploymentName());
		modifiedRuleDefinition.setCreatedById(existingRuleDefinition.getCreatedById());
		modifiedRuleDefinition.setCreatedOn(existingRuleDefinition.getCreatedOn());
		modifiedRuleDefinition.setCreatedByName(existingRuleDefinition.getCreatedByName());
		modifiedRuleDefinition.setUpdatedById(loggedInUserId);
		modifiedRuleDefinition.setUpdatedOn(Instant.now());
		modifiedRuleDefinition.setUpdatedByName(loggedInUserDetails.get(USER_DEFINITION_FIRST_NAME).toString() + SPACE + loggedInUserDetails.get(USER_DEFINITION_LAST_NAME).toString());
		ruleEngineRepository.save(modifiedRuleDefinition);
		return new RuleEngineResponse(String.valueOf(modifiedRuleDefinition.getId()),modifiedRuleDefinition.getName(),modifiedRuleDefinition.getVersion());
	}

	public void deleteDRDDetails(String id)
	{
		if(!ruleEngineRepository.existsById(BigInteger.valueOf(Long.parseLong(id))))
		{
			throw new RuleEngineNotFoundException(DMN_ID_NOT_FOUND,globalMessageSource.get(DMN_ID_NOT_FOUND));
		}
		  this.ruleEngineRepository.deleteById(BigInteger.valueOf(Long.parseLong(id)));
	}

	@Override
	public List<Map<String, Object>> evaluateDMN(ListDataRequestDTO listDataRequestDTO)
	{
		listDataRequestDTO.getVariables().stream().forEach(data ->
		{
			try
			{
				Object output = this.executeDMN(new RuleEngineRequestDTO(listDataRequestDTO.getId(),data, listDataRequestDTO.getVersion()));
				data.put(OUTPUT,output);
			}
			catch (Exception e)
			{
				throw new DMNExecutionException(UNABLE_TO_EVALUATE_DMN,globalMessageSource.get(UNABLE_TO_EVALUATE_DMN));
			}
		});
		return listDataRequestDTO.getVariables();
	}
}
