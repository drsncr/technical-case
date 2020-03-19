package com.op.technicalcase.controller;

import com.op.technicalcase.constant.ErrorCode;
import com.op.technicalcase.exception.*;
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
        ExceptionObject exceptionObject = new ExceptionObject(ErrorCode.INVALID_METHOD_ARGUMENT_EXCEPTION, "Method arguments are not valid");
        return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionObject exceptionObject = new ExceptionObject(ErrorCode.INVALID_METHOD_ARGUMENT_EXCEPTION, "Method arguments are not valid");
        return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionObject exceptionObject = new ExceptionObject(ErrorCode.INVALID_METHOD_ARGUMENT_EXCEPTION, "Method arguments are not valid");
        return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ExceptionObject> handleDateTimeParseException(DateTimeParseException ex, WebRequest webRequest){
        ExceptionObject exceptionObject = new ExceptionObject(ErrorCode.DATETIME_PARSE_EXCEPTION, "Unrecognized date format. Date format should be dd-MM-yyyy");
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

    @ExceptionHandler(ExchangeRateServiceNotAvailableException.class)
    public ResponseEntity<ExceptionObject> handleExchangeRateServiceNotAvailableException(ExchangeRateServiceNotAvailableException ex, WebRequest webRequest){
        ExceptionObject exceptionObject = new ExceptionObject(ErrorCode.EXCHANGE_RATE_SERVICE_NOT_AVAILABLE_EXCEPTION, ex.getMessage());
        return new ResponseEntity<>(exceptionObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CurrencyNullException.class)
    public ResponseEntity<ExceptionObject> handleCurrencyNullException(CurrencyNullException ex, WebRequest webRequest){
        ExceptionObject exceptionObject = new ExceptionObject(ErrorCode.CURRENCY_NULL_EXCEPTION, ex.getMessage());
        return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidConversionInputException.class)
    public ResponseEntity<ExceptionObject> handleConversionInputInvalidException(InvalidConversionInputException ex, WebRequest webRequest){
        ExceptionObject exceptionObject = new ExceptionObject(ErrorCode.INVALID_CONVERSION_INPUT_EXCEPTION, ex.getMessage());
        return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidPageNumberException.class)
    public ResponseEntity<ExceptionObject> handleInvalidPageNumberException(InvalidPageNumberException ex, WebRequest webRequest){
        ExceptionObject exceptionObject = new ExceptionObject(ErrorCode.INVALID_PAGE_NUMBER_EXCEPTION, ex.getMessage());
        return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidSizeNumberException.class)
    public ResponseEntity<ExceptionObject> handleInvalidSizeNumberException(InvalidSizeNumberException ex, WebRequest webRequest){
        ExceptionObject exceptionObject = new ExceptionObject(ErrorCode.INVALID_SIZE_NUMBER_EXCEPTION, ex.getMessage());
        return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
    }
}
