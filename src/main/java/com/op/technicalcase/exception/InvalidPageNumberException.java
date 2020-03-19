package com.op.technicalcase.exception;

public class InvalidPageNumberException extends RuntimeException {
    public InvalidPageNumberException(){
        super("Invalid page parameter. Page can't be less than 0");
    }
}
