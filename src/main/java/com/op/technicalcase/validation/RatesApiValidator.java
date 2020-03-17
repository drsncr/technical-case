package com.op.technicalcase.validation;

import com.op.technicalcase.validation.base.CurrencyValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RatesApiValidator {
    @Autowired
    CurrencyValidator currencyValidator;

    public List<String> validate(String sourceCurrency, String targetCurrency){
        List<String> exceptionFields = new ArrayList<>();

        if(!currencyValidator.validate(sourceCurrency))
            exceptionFields.add("sourceCurrency");

        if(!currencyValidator.validate(targetCurrency))
            exceptionFields.add("targetCurrency");

        return exceptionFields;
    }
}
