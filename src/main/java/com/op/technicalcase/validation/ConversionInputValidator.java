package com.op.technicalcase.validation;

import com.op.technicalcase.model.ConversionInput;
import com.op.technicalcase.validation.base.AmountValidator;
import com.op.technicalcase.validation.base.CurrencyValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConversionInputValidator {

    @Autowired
    CurrencyValidator currencyValidator;

    @Autowired
    AmountValidator amountValidator;

    public List<String> validate(ConversionInput conversionInput){
        List<String> exceptionFields = new ArrayList<>();

        if(!currencyValidator.validate(conversionInput.getSourceCurrency()))
            exceptionFields.add("sourceCurrency");

        if(!currencyValidator.validate(conversionInput.getTargetCurrency()))
            exceptionFields.add("targetCurrency");

        if(!amountValidator.validate(conversionInput.getSourceAmount()))
            exceptionFields.add("sourceAmount");

        return exceptionFields;
    }
}
