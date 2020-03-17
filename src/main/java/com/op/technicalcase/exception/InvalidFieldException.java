package com.op.technicalcase.exception;

public class InvalidFieldException extends RuntimeException {

    public InvalidFieldException(String message){
        super("Following fields are in valid ==>" + message);
    }
}
