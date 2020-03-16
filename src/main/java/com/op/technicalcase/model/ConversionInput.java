package com.op.technicalcase.model;


import java.math.BigDecimal;

public class ConversionInput {
    private BigDecimal sourceAmount;
    private String sourceCurrency;
    private String targetCurrency;

    public BigDecimal getSourceAmount() {
        return sourceAmount;
    }

    public void setSourceAmount(BigDecimal sourceAmount) {
        this.sourceAmount = sourceAmount;
    }

    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(String sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    @Override
    public String toString() {
        return "ConversionInput{" +
                "sourceAmount=" + sourceAmount +
                ", sourceCurrency='" + sourceCurrency + '\'' +
                ", targetCurrency='" + targetCurrency + '\'' +
                '}';
    }
}
