package com.op.technicalcase.service;

import com.op.technicalcase.exception.ExchangeRateServiceNotAvailableException;
import com.op.technicalcase.exception.InvalidParameterException;
import com.op.technicalcase.model.ExchangeRate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ExchangeRateClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    ExchangeRateClient exchangeRateClient;

    @Value("${rates.api}")
    private String ratesApiUrl;

    private Currency tryCurrency;
    private Currency usdCurrency;
    private Currency sarCurrency;

    private ExchangeRate exchangeRate;

    @BeforeEach
    public void init(){
        tryCurrency = Currency.getInstance("TRY");
        usdCurrency = Currency.getInstance("USD");
        sarCurrency = Currency.getInstance("SAR");

        exchangeRate = new ExchangeRate();
        exchangeRate.setBase("USD");
        exchangeRate.setDate(LocalDate.of(2020, 3,18));

        Map<String, BigDecimal> rates = new HashMap<String, BigDecimal>();
        rates.put("TRY", new BigDecimal("6.45"));
        rates.put("EUR", new BigDecimal("0.91"));
        exchangeRate.setRates(rates);
    }

    @Test()
    public void getRateFromClient_whenCurrencyNull_throwsInvalidParameterException(){
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            ExchangeRate rate = exchangeRateClient.getRateFromClient(tryCurrency, null);
        });

        Assertions.assertThrows(InvalidParameterException.class, () -> {
            ExchangeRate rate = exchangeRateClient.getRateFromClient(null, usdCurrency);
        });

        Assertions.assertThrows(InvalidParameterException.class, () -> {
            ExchangeRate rate = exchangeRateClient.getRateFromClient(null, null);
        });
    }

    @Test()
    public void getRateFromClient_whenCurrencyValid_returnCorrectValue(){
        ReflectionTestUtils.setField(exchangeRateClient, "ratesApiUrl", ratesApiUrl);

        Mockito.when(restTemplate.exchange(Mockito.any(String.class), Mockito.<HttpMethod> any(),
                                           Mockito.<HttpEntity<String>> any(), Mockito.<Class<ExchangeRate>> any()))
               .thenReturn(new ResponseEntity(exchangeRate, HttpStatus.OK));

        ExchangeRate rate = exchangeRateClient.getRateFromClient(usdCurrency, tryCurrency);
        Assertions.assertEquals(rate.getRates().get("TRY"), exchangeRate.getRates().get("TRY"));
    }

    @Test
    public void getRateFromClient_whenExchangeRateServiceUnavailable_throwsExchangeRateServiceNotAvailableException() {
        ReflectionTestUtils.setField(exchangeRateClient, "ratesApiUrl", "https://api.ratgfdesapi.io/api/latest?base={0}&symbols={1}");

        Assertions.assertThrows(ExchangeRateServiceNotAvailableException.class, () -> {
            ExchangeRate rate = exchangeRateClient.getRateFromClient(tryCurrency, usdCurrency);
        });
    }
}
