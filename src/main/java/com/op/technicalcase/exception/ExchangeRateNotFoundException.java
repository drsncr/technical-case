package com.op.technicalcase.exception;

public class ExchangeRateNotFoundException extends RuntimeException {

    public ExchangeRateNotFoundException(String sourceCurrency, String targetCurrency){
        super("Exchange rate is not found for these currency pair ==> " + sourceCurrency + " - " + targetCurrency);
    }
}
