package com.op.technicalcase.exception;

public class InvalidConversionInputException extends RuntimeException {

    public InvalidConversionInputException(String message){
        super("Message ==>" + message);
    }
}
