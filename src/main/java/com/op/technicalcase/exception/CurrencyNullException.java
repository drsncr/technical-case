package com.op.technicalcase.exception;

public class CurrencyNullException extends RuntimeException {

    public CurrencyNullException(){
        super("Currency parameters can't be null");
    }
}
