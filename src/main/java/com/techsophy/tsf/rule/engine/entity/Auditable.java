package com.techsophy.tsf.rule.engine.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.time.Instant;
import static com.techsophy.tsf.rule.engine.constants.RuleEngineConstants.CREATED_BY_ID_NOT_NULL;
import static com.techsophy.tsf.rule.engine.constants.RuleEngineConstants.CREATED_ON_NOT_NULL;

@Data
@With
@NoArgsConstructor
@AllArgsConstructor
public class Auditable
{
    @NotNull(message = CREATED_BY_ID_NOT_NULL)
    private BigInteger createdById;
    private BigInteger updatedById;
    @NotNull(message = CREATED_ON_NOT_NULL)
    private Instant createdOn;
    private Instant updatedOn;
    private String createdByName;
    private String updatedByName;
}
