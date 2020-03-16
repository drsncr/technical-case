package com.op.technicalcase.validation;

import com.op.technicalcase.model.ConversionInput;
import org.springframework.stereotype.Component;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.UnknownCurrencyException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class ConversionInputValidator {
    CurrencyUnit currencyUnit;

    public List<String> validate(ConversionInput conversionInput){
        List<String> exceptionFields = new ArrayList<>();

        try{ currencyUnit = Monetary.getCurrency(conversionInput.getSourceCurrency()); }
        catch (UnknownCurrencyException ex) { exceptionFields.add("sourceCurrency"); }

        try{ currencyUnit = Monetary.getCurrency(conversionInput.getTargetCurrency()); }
        catch (UnknownCurrencyException ex) { exceptionFields.add("targetCurrency"); }

        if (conversionInput.getSourceAmount() == null) {
            exceptionFields.add("sourceAmount");
        }
        else{
            if(conversionInput.getSourceAmount().compareTo(BigDecimal.ZERO) < 0){
                exceptionFields.add("sourceAmount");
            }
        }

        return exceptionFields;
    }
}
