package com.op.technicalcase.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

@Entity
public class Conversion implements Serializable {

    public Conversion(){}

    public Conversion(ConversionInput conversionInput){
        this.sourceAmount = conversionInput.getSourceAmount();
        this.sourceCurrency = conversionInput.getSourceCurrency();
        this.targetCurrency = conversionInput.getTargetCurrency();
    }

    @Id
    @GeneratedValue()
    private Long id;
    private BigDecimal sourceAmount;
    private Currency sourceCurrency;
    private BigDecimal targetAmount;
    private Currency targetCurrency;
    private LocalDate creationDate;

    @PrePersist
    private void setCreationDate(){
        this.creationDate = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getSourceAmount() {
        return sourceAmount;
    }

    public Currency getSourceCurrency() {
        return sourceCurrency;
    }

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(BigDecimal targetAmount) {
        this.targetAmount = targetAmount;
    }

    public Currency getTargetCurrency() {
        return targetCurrency;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    @Override
    public String toString() {
        return "Conversion{" +
                "id=" + id +
                ", sourceAmount=" + sourceAmount +
                ", sourceCurrency=" + sourceCurrency +
                ", targetAmount=" + targetAmount +
                ", targetCurrency=" + targetCurrency +
                ", creationDate=" + creationDate +
                '}';
    }
}
