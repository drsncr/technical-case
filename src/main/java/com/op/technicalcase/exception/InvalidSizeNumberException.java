package com.op.technicalcase.exception;

public class InvalidSizeNumberException extends RuntimeException {
    public InvalidSizeNumberException(){
        super("Invalid size parameter. Size can't be less than 1");
    }
}
