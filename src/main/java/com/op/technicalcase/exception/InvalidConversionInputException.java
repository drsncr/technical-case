package com.op.technicalcase.exception;

public class InvalidConversionInputException extends RuntimeException {

    public InvalidConversionInputException(String message){
        super("Following fields are in valid ==>" + message);
    }
}
