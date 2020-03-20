package com.op.technicalcase.service;

import com.op.technicalcase.constant.ErrorMessage;
import com.op.technicalcase.exception.ExchangeRateNotFoundException;
import com.op.technicalcase.exception.ExchangeRateServiceNotAvailableException;
import com.op.technicalcase.exception.InvalidParameterException;
import com.op.technicalcase.model.CurrencyPair;
import com.op.technicalcase.model.ExchangeRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Currency;

@Component
public class ExchangeRateClient {

    @Value("${rates.api}")
    private String ratesApiUrl;

    @Autowired
    RestTemplate restTemplate;

    HttpEntity<String> request;

    @PostConstruct
    public void setRestTemplateDefaults(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        this.request = new HttpEntity<String>(headers);
    }

    public ExchangeRate getRateFromClient(Currency sourceCurrency, Currency targetCurrency){
        if(sourceCurrency == null)
            throw new InvalidParameterException(ErrorMessage.INVALID_PARAMETER_MESSAGE, "sourceCurrency");
        if(targetCurrency == null)
            throw new InvalidParameterException(ErrorMessage.INVALID_PARAMETER_MESSAGE, "targetCurrency");

        ExchangeRate exchangeRate;
        String formattedRatesApiUrl = MessageFormat.format(this.ratesApiUrl, sourceCurrency.getCurrencyCode(), targetCurrency.getCurrencyCode());
        try{
            ResponseEntity<ExchangeRate> responseEntity = restTemplate.exchange(formattedRatesApiUrl, HttpMethod.GET, this.request, ExchangeRate.class);
            exchangeRate = responseEntity.getBody();
            return exchangeRate;
        }
        catch (HttpClientErrorException ex){
            throw new ExchangeRateNotFoundException(ErrorMessage.EXCHANGE_RATE_NOT_FOUND_MESSAGE, sourceCurrency.getCurrencyCode(), targetCurrency.getCurrencyCode());
        }
        catch (Exception ex){
            throw new ExchangeRateServiceNotAvailableException(ErrorMessage.EXCHANGE_SERVICE_NOT_AVAILABLE_MESSAGE);
        }
    }
}
