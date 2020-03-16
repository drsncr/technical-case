package com.op.technicalcase.model;

import java.math.BigDecimal;

public class ConversionOutput {
    private Long id;
    private BigDecimal targetAmount;

    public ConversionOutput(Conversion conversion){
        this.id = conversion.getId();
        this.targetAmount = conversion.getTargetAmount();
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }
}
