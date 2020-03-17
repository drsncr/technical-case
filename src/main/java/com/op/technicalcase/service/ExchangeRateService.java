package com.op.technicalcase.service;

import com.op.technicalcase.exception.ExchangeRateNotFoundException;
import com.op.technicalcase.exception.InvalidFieldException;
import com.op.technicalcase.model.ExchangeRate;
import com.op.technicalcase.validation.RatesApiValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;

@Service
public class ExchangeRateService {

    @Autowired
    RatesApiValidator ratesApiValidator;

    @Autowired
    Environment environment;

    RestTemplate restTemplate;
    private String ratesApiUrl;

    @PostConstruct
    public void setRatesApiUrl(){
        this.ratesApiUrl = environment.getProperty("rates.api");
        this.restTemplate = new RestTemplate();
    }

    public BigDecimal getRateFromApi(String sourceCurrency, String targetCurrency) throws InvalidFieldException, ExchangeRateNotFoundException{
        List<String> exceptionFields = ratesApiValidator.validate(sourceCurrency, targetCurrency);
        if(!exceptionFields.isEmpty())
            throw new InvalidFieldException(exceptionFields.toString());

        String formattedRatesApiUrl = String.format(this.ratesApiUrl, sourceCurrency, targetCurrency);
        HttpHeaders headers = new HttpHeaders();
        headers.add("user-agent", "Application");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ExchangeRate exchangeRate = restTemplate.getForObject(formattedRatesApiUrl, ExchangeRate.class, entity);

        if(exchangeRate != null && exchangeRate.getRates().size() > 0)
            return exchangeRate.getRates().get(targetCurrency);
        else
            throw new ExchangeRateNotFoundException(sourceCurrency, targetCurrency);

    }

}
