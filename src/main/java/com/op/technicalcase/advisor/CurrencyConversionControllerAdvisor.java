package com.op.technicalcase.advisor;

import com.op.technicalcase.constant.ErrorCode;
import com.op.technicalcase.exception.ExchangeRateNotFoundException;
import com.op.technicalcase.exception.InvalidFieldException;
import com.op.technicalcase.model.ExceptionObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CurrencyConversionControllerAdvisor extends ResponseEntityExceptionHandler {

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
}
