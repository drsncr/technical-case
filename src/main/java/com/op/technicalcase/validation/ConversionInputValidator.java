package com.op.technicalcase.validation;

import com.op.technicalcase.model.ConversionInput;
import org.springframework.stereotype.Component;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class ConversionInputValidator {
    CurrencyUnit currencyUnit;

    public List<String> validate(ConversionInput conversionInput){
        List<String> exceptionFields = new ArrayList<>();
        currencyUnit = Monetary.getCurrency(conversionInput.getSourceCurrency());
        if(currencyUnit == null)
            exceptionFields.add("sourceCurrency");

        currencyUnit = Monetary.getCurrency(conversionInput.getTargetCurrency());
        if(currencyUnit == null)
            exceptionFields.add("targetCurrency");

        if (conversionInput.getSourceAmount().compareTo(BigDecimal.ZERO) >= 0)
            exceptionFields.add("sourceAmount");

        return exceptionFields;
    }
}
