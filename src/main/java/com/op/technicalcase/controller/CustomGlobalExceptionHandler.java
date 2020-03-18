package com.op.technicalcase.controller;

import com.op.technicalcase.constant.ErrorCode;
import com.op.technicalcase.exception.ExchangeRateNotFoundException;
import com.op.technicalcase.exception.InSufficientQueryParamException;
import com.op.technicalcase.exception.InvalidFieldException;
import com.op.technicalcase.model.ExceptionObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.format.DateTimeParseException;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionObject> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest webRequest){
        ExceptionObject exceptionObject = new ExceptionObject(ErrorCode.INVALID_FIELD_EXCEPTION, ex.getMessage());
        return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidFieldException.class)
    public ResponseEntity<ExceptionObject> handleInvalidConversionInputException(InvalidFieldException ex, WebRequest webRequest){
        ExceptionObject exceptionObject = new ExceptionObject(ErrorCode.INVALID_FIELD_EXCEPTION, ex.getMessage());
        return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExchangeRateNotFoundException.class)
    public ResponseEntity<ExceptionObject> handleExchangeRateNotFoundException(ExchangeRateNotFoundException ex, WebRequest webRequest){
        ExceptionObject exceptionObject = new ExceptionObject(ErrorCode.EXCHANGE_RATE_NOT_FOUND_EXCEPTION, ex.getMessage());
        return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InSufficientQueryParamException.class)
    public ResponseEntity<ExceptionObject> handleInSufficientQueryParamException(InSufficientQueryParamException ex, WebRequest webRequest){
        ExceptionObject exceptionObject = new ExceptionObject(ErrorCode.INSUFFICIENT_QUERY_PARAM_EXCEPTION, ex.getMessage());
        return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ExceptionObject> handleDateTimeParseException(DateTimeParseException ex, WebRequest webRequest){
        String message = "Unrecognized date format. Date format should be dd-MM-yyyy";
        ExceptionObject exceptionObject = new ExceptionObject(ErrorCode.DATETIME_PARSE_EXCEPTION, ex.getMessage());
        return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionObject> handleGeneralException(Exception ex, WebRequest webRequest){
        String message = "error";
        ExceptionObject exceptionObject = new ExceptionObject(209, ex.getMessage());
        return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
    }
}
