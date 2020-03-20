package com.op.technicalcase.exception;

import com.op.technicalcase.constant.ErrorMessage;

public class ExchangeRateServiceNotAvailableException extends RuntimeException {

    public ExchangeRateServiceNotAvailableException(String message){
        super(message);
    }
}
