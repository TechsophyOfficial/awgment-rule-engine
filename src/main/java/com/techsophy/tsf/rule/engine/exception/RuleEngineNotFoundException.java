package com.techsophy.tsf.rule.engine.exception;

public class RuleEngineNotFoundException extends RuntimeException
{
    final String errorCode;
    final String message;

    public RuleEngineNotFoundException(String errorCode, String message)
    {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }
}
