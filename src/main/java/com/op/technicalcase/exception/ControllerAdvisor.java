package com.op.technicalcase.exception;

import com.op.technicalcase.model.ExceptionObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidConversionInputException.class)
    public ResponseEntity<ExceptionObject> handleInvalidConversionInputException(InvalidConversionInputException ex, WebRequest webRequest){
        ExceptionObject exceptionObject = new ExceptionObject(101, ex.getMessage());
        return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
    }
}
