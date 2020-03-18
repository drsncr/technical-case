package com.op.technicalcase.exception;

public class ExchangeRateServiceNotAvailableException extends RuntimeException {

    public ExchangeRateServiceNotAvailableException(){
        super("Exchange rate service is unavailable");
    }
}
