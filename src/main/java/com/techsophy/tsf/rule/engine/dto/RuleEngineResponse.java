package com.techsophy.tsf.rule.engine.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RuleEngineResponse
{
    String id;
    String name;
    Integer version;
}

