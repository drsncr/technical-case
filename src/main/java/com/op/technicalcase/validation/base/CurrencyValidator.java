package com.op.technicalcase.validation.base;

import org.springframework.stereotype.Component;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.UnknownCurrencyException;
import java.util.List;

@Component
public class CurrencyValidator {
    CurrencyUnit currencyUnit;

    public boolean validate(String currency){
        try{
            currencyUnit = Monetary.getCurrency(currency);
            return true;
        }
        catch (UnknownCurrencyException ex) {
            return false;
        }
    }
}
