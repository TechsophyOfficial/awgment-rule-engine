package com.techsophy.tsf.rule.engine.exception;

public class InvalidInputException extends RuntimeException
{
    final String errorCode;
    final String message;

    public InvalidInputException(String errorCode, String message)
    {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }
}
