package com.op.technicalcase.model;

import javax.validation.constraints.NotNull;
import java.util.Currency;

public class CurrencyPair {

    @NotNull
    private Currency sourceCurrency;
    @NotNull
    private Currency targetCurrency;

    public Currency getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(Currency sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    public Currency getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(Currency targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    @Override
    public String toString() {
        return "CurrencyPair{" +
                "sourceCurrency=" + sourceCurrency +
                ", targetCurrency=" + targetCurrency +
                '}';
    }
}
