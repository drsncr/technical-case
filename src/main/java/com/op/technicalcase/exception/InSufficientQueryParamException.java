package com.op.technicalcase.exception;

public class InSufficientQueryParamException extends RuntimeException {
    public InSufficientQueryParamException(){
        super("Both id and creationDate can't be null");
    }
}
