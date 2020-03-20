package com.op.technicalcase.exception;

import java.text.MessageFormat;

public class ExchangeRateNotFoundException extends RuntimeException {

    public ExchangeRateNotFoundException(String message, String sourceCurrency, String targetCurrency){
        super(MessageFormat.format(message, sourceCurrency, targetCurrency));
    }
}
