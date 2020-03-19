package com.op.technicalcase.exception;

public class InvalidConversionInputException extends RuntimeException {

    public InvalidConversionInputException(){
        super("Conversion input or one of its attributes can't be null");
    }
}
