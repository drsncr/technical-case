package com.op.technicalcase.service;

import com.op.technicalcase.constant.ErrorMessage;
import com.op.technicalcase.exception.ExchangeRateNotFoundException;
import com.op.technicalcase.model.ExchangeRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Currency;

@Service
public class ExchangeRateService {

    @Autowired
    ExchangeRateClient exchangeRateClient;

    public BigDecimal getExchangeRate(Currency sourceCurrency, Currency targetCurrency){
        ExchangeRate exchangeRate = exchangeRateClient.getRateFromClient(sourceCurrency, targetCurrency);
        if(exchangeRate != null && exchangeRate.getRates().size() > 0)
            return exchangeRate.getRates().get(targetCurrency.getCurrencyCode());
        else
            throw new ExchangeRateNotFoundException(ErrorMessage.EXCHANGE_RATE_NOT_FOUND_MESSAGE,
                    sourceCurrency.getCurrencyCode(), targetCurrency.getCurrencyCode());
    }
}
