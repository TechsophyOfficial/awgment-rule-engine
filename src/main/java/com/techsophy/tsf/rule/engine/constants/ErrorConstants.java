package com.techsophy.tsf.rule.engine.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorConstants
{
    public static final String UNABLE_TO_EXECUTE_DMN="AWGMENT-RULE-ENGINE-1001";
    public static final String INVALID_TOKEN="AWGMENT-RULE-ENGINE-1002";
    public static final String AUTHENTICATION_FAILED="AWGMENT-RULE-ENGINE-1003";
    public static final String DMN_ID_NOT_FOUND ="AWGMENT-RULE-ENGINE-1004";
    public static final String USER_DETAILS_NOT_FOUND_EXCEPTION="AWGMENT-RULE-ENGINE-1005";
    public static final String LOGGED_IN_USER_ID_NOT_FOUND ="AWGMENT-RULE-ENGINE-1006";
    public static final String TOKEN_NOT_NULL="AWGMENT-RULE-ENGINE-1007";
    public static final String DMN_CONTENT_IS_INVALID="AWGMENT-RULE-ENGINE-1008";
    public static final String UNABLE_TO_EVALUATE_DMN="AWGMENT-RULE-ENGINE-1009";
    public static final String INVALID_CONTENT="AWGMENT-RULE-ENGINE-1010";
}
