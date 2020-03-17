package com.op.technicalcase.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

public class ExchangeRate {
    private String base;
    private Map<String, BigDecimal> rates;
    private LocalDateTime date;

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Map<String, BigDecimal> getRates() {
        return rates;
    }

    public void setRates(Map<String, BigDecimal> rates) {
        this.rates = rates;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
