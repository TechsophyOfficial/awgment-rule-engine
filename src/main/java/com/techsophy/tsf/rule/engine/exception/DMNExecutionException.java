package com.techsophy.tsf.rule.engine.exception;

public class DMNExecutionException extends RuntimeException
{
    final String errorCode;
    final String message;

    public DMNExecutionException(String errorCode, String message)
    {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }
}
