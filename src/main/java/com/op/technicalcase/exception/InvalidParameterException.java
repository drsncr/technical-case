package com.op.technicalcase.exception;

import java.text.MessageFormat;

public class InvalidParameterException extends RuntimeException{

    public InvalidParameterException(String message, String parameterName){
        super(MessageFormat.format(message, parameterName));
    }

}
