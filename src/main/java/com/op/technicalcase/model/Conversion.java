package com.op.technicalcase.model;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Conversion implements Serializable {

    public Conversion(ConversionInput conversionInput){
        this.sourceAmount = conversionInput.getSourceAmount();
        this.sourceCurrency = conversionInput.getSourceCurrency();
        this.targetCurrency = conversionInput.getTargetCurrency();
    }

    @Id
    @GeneratedValue
    private Long id;
    private BigDecimal sourceAmount;
    private String sourceCurrency;
    private BigDecimal targetAmount;
    private String targetCurrency;
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

    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(BigDecimal targetAmount) {
        this.targetAmount = targetAmount;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

}
