package com.techsophy.tsf.rule.engine.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.Instant;

@Data
@AllArgsConstructor
public class RuleEngineSchema
{
    String id;
    String name;
    String content;
    Integer version;
    String deploymentName;
    String  createdById;
    Instant createdOn;
    String createdByName;
    String updatedById;
    Instant updatedOn;
    String updatedByName;
}
