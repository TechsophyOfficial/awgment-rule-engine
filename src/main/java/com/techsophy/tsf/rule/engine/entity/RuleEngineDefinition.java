package com.techsophy.tsf.rule.engine.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serializable;
import java.math.BigInteger;
import static com.techsophy.tsf.rule.engine.constants.RuleEngineConstants.TP_RULE_ENGINE_COLLECTION;

@EqualsAndHashCode(callSuper = true)
@Data
@With
@AllArgsConstructor
@Document(collection = TP_RULE_ENGINE_COLLECTION)
public class RuleEngineDefinition extends Auditable implements Serializable
{
	@Id
	private BigInteger id;
	private String name;
	private String content;
	private Integer version;
	private String deploymentName;
}
