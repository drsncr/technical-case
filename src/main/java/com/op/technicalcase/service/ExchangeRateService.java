package com.op.technicalcase.service;

import com.op.technicalcase.exception.ExchangeRateNotFoundException;
import com.op.technicalcase.exception.InvalidFieldException;
import com.op.technicalcase.model.ExchangeRate;
import com.op.technicalcase.validation.RatesApiValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
public class ExchangeRateService {

    @Autowired
    RatesApiValidator ratesApiValidator;

    @Autowired
    Environment environment;

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

    public BigDecimal getRateFromApi(String sourceCurrency, String targetCurrency){
        List<String> exceptionFields = ratesApiValidator.validate(sourceCurrency, targetCurrency);
        if(!exceptionFields.isEmpty())
            throw new InvalidFieldException(exceptionFields.toString());

        String formattedRatesApiUrl = String.format(this.ratesApiUrl, sourceCurrency, targetCurrency);
        ResponseEntity<ExchangeRate> responseEntity = restTemplate.exchange(formattedRatesApiUrl, HttpMethod.GET, this.request, ExchangeRate.class);
        ExchangeRate exchangeRate = responseEntity.getBody();

        if(exchangeRate != null && exchangeRate.getRates().size() > 0)
            return exchangeRate.getRates().get(targetCurrency);
        else
            throw new ExchangeRateNotFoundException(sourceCurrency, targetCurrency);
    }

}
