package com.op.technicalcase.model;


import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Currency;

public class ConversionInput {

    @NotNull()
    @DecimalMin(value = "0")
    private BigDecimal sourceAmount;

    @NotNull
    private Currency sourceCurrency;

    @NotNull
    private Currency targetCurrency;

    public BigDecimal getSourceAmount() {
        return sourceAmount;
    }

    public void setSourceAmount(BigDecimal sourceAmount) {
        this.sourceAmount = sourceAmount;
    }

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
        return "ConversionInput{" +
                "sourceAmount=" + sourceAmount +
                ", sourceCurrency=" + sourceCurrency +
                ", targetCurrency=" + targetCurrency +
                '}';
    }
}
