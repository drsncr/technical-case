package com.op.technicalcase.controller;

import com.op.technicalcase.model.ConversionFilterObject;
import com.op.technicalcase.model.ConversionInput;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Currency;

@Aspect
@Component
public class CurrencyConversionControllerAspect {

    @Before(value = "execution(* com.op.technicalcase.controller.CurrencyConversionController.getExchangeRate*(..)) " +
            "&& args(sourceCurrency, targetCurrency)")
    public void beforeGetExchangeRate(JoinPoint joinPoint, Currency sourceCurrency, Currency targetCurrency) {
        System.out.println("Before method:" + joinPoint.getSignature());
        System.out.println("Source Currency : " + sourceCurrency + " Target Currency : " + targetCurrency);
    }

    @Before(value = "execution(* com.op.technicalcase.controller.CurrencyConversionController.convertToTargetCurrency*(..)) " +
            "&& args(.., @RequestBody conversionInput)\")")
    public void beforeConvertToTargetCurrency(JoinPoint joinPoint, ConversionInput conversionInput) {
        System.out.println("Before method:" + joinPoint.getSignature());
        System.out.println(conversionInput.toString());
    }

    @Before(value = "execution(* com.op.technicalcase.controller.CurrencyConversionController.getConversionList*(..)) " +
            "&& args(.., @RequestBody conversionFilterObject, @RequestParam page, @RequestParam size)\")")
    public void beforeGetConversions(JoinPoint joinPoint, ConversionFilterObject conversionFilterObject, Integer page, Integer size) {
        System.out.println("Before method:" + joinPoint.getSignature());
        System.out.println(conversionFilterObject.toString() + " Page: " + page + " Size: " + size);
    }

    @AfterReturning(value = "execution(* com.op.technicalcase.controller.CurrencyConversionController.*(..))", returning="responseEntity")
    public void afterReturning(JoinPoint joinPoint, ResponseEntity responseEntity) {
        System.out.println("After method:" + joinPoint.getSignature());
        System.out.println("Return Value : " + responseEntity.getBody());
    }

    @AfterThrowing(value = "execution(* com.op.technicalcase.controller.CurrencyConversionController.*(..))", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Exception ex) {
        System.out.println("After method:" + joinPoint.getSignature());
        System.out.println("Exception received : " + ex.getMessage());
    }

}
