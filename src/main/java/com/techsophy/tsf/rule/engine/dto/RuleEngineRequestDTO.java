package com.techsophy.tsf.rule.engine.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Map;

@AllArgsConstructor
@EqualsAndHashCode
@Data
public class RuleEngineRequestDTO
{
	String id;
	Map<String,Object> variables;
	Integer version;
}
