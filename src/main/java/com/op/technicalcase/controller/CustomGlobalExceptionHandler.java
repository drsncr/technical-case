package com.op.technicalcase.controller;

import com.op.technicalcase.constant.ErrorCode;
import com.op.technicalcase.constant.ErrorMessage;
import com.op.technicalcase.exception.ExchangeRateNotFoundException;
import com.op.technicalcase.exception.ExchangeRateServiceNotAvailableException;
import com.op.technicalcase.exception.InvalidParameterException;
import com.op.technicalcase.model.ExceptionObject;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.format.DateTimeParseException;

@RestControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionObject exceptionObject = new ExceptionObject(ErrorCode.INVALID_METHOD_ARGUMENT_EXCEPTION, ErrorMessage.INVALID_METHOD_ARGUMENT_MESSAGE);
        return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionObject exceptionObject = new ExceptionObject(ErrorCode.INVALID_METHOD_ARGUMENT_EXCEPTION, ErrorMessage.INVALID_METHOD_ARGUMENT_MESSAGE);
        return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionObject exceptionObject = new ExceptionObject(ErrorCode.INVALID_METHOD_ARGUMENT_EXCEPTION, ErrorMessage.INVALID_METHOD_ARGUMENT_MESSAGE);
        return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ExceptionObject> handleDateTimeParseException(DateTimeParseException ex, WebRequest webRequest){
        ExceptionObject exceptionObject = new ExceptionObject(ErrorCode.DATETIME_PARSE_EXCEPTION, ErrorMessage.INVALID_DATE_FORMAT_MESSAGE);
        return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<ExceptionObject> handleInvalidParameterExceptionn(InvalidParameterException ex, WebRequest webRequest){
        ExceptionObject exceptionObject = new ExceptionObject(ErrorCode.INVALID_PARAMETER_EXCEPTION, ex.getMessage());
        return new ResponseEntity<>(exceptionObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExchangeRateNotFoundException.class)
    public ResponseEntity<ExceptionObject> handleExchangeRateNotFoundException(ExchangeRateNotFoundException ex, WebRequest webRequest){
        ExceptionObject exceptionObject = new ExceptionObject(ErrorCode.EXCHANGE_RATE_NOT_FOUND_EXCEPTION, ex.getMessage());
        return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExchangeRateServiceNotAvailableException.class)
    public ResponseEntity<ExceptionObject> handleExchangeRateServiceNotAvailableException(ExchangeRateServiceNotAvailableException ex, WebRequest webRequest){
        ExceptionObject exceptionObject = new ExceptionObject(ErrorCode.EXCHANGE_RATE_SERVICE_NOT_AVAILABLE_EXCEPTION, ex.getMessage());
        return new ResponseEntity<>(exceptionObject, HttpStatus.NOT_FOUND);
    }
}
