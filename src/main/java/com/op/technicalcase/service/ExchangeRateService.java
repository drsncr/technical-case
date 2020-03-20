package com.op.technicalcase.service;

import com.op.technicalcase.exception.CurrencyNullException;
import com.op.technicalcase.exception.ExchangeRateNotFoundException;
import com.op.technicalcase.exception.ExchangeRateServiceNotAvailableException;
import com.op.technicalcase.model.ExchangeRate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;

@Service
public class ExchangeRateService {

    @Value("${rates.api}")
    private String ratesApiUrl;

    RestTemplate restTemplate;
    HttpEntity<String> request;

    @PostConstruct
    public void setRestTemplateDefaults(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        this.request = new HttpEntity<String>(headers);
        this.restTemplate = new RestTemplate();
    }

    public BigDecimal getRateFromApi(Currency sourceCurrency, Currency targetCurrency){
        if(sourceCurrency == null || targetCurrency == null)
            throw new CurrencyNullException();

        ExchangeRate exchangeRate;
        String formattedRatesApiUrl = String.format(this.ratesApiUrl, sourceCurrency, targetCurrency);
        try{
            ResponseEntity<ExchangeRate> responseEntity = restTemplate.exchange(formattedRatesApiUrl, HttpMethod.GET, this.request, ExchangeRate.class);
            exchangeRate = responseEntity.getBody();
        }
        catch (HttpClientErrorException ex){
            throw new HttpClientErrorException(ex.getStatusCode(), ex.getMessage());
        }
        catch (Exception ex){
            throw new ExchangeRateServiceNotAvailableException();
        }

        if(exchangeRate != null && exchangeRate.getRates().size() > 0)
            return exchangeRate.getRates().get(targetCurrency.getCurrencyCode());
        else
            throw new ExchangeRateNotFoundException(sourceCurrency.getCurrencyCode(), targetCurrency.getCurrencyCode());
    }

}
