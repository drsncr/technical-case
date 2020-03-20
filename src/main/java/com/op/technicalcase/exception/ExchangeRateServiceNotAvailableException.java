package com.op.technicalcase.exception;

public class ExchangeRateServiceNotAvailableException extends RuntimeException {

    public ExchangeRateServiceNotAvailableException(String message){
        super(message);
    }
}
