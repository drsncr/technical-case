package com.op.technicalcase.controller;

import com.op.technicalcase.exception.InvalidConversionInputException;
import com.op.technicalcase.model.ConversionInput;
import com.op.technicalcase.service.CurrencyConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/conversion")
public class CurrencyConversionController {

    @Autowired
    CurrencyConversionService conversionService;

    @PostMapping(value="/target", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public void convertToTargetCurrency(@RequestBody ConversionInput conversionInput) throws InvalidConversionInputException {
        conversionService.convertToTargetCurrency(conversionInput);
    }
}
