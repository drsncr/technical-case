package com.op.technicalcase.service;

import com.op.technicalcase.exception.CurrencyNullException;
import com.op.technicalcase.exception.ExchangeRateNotFoundException;
import com.op.technicalcase.exception.ExchangeRateServiceNotAvailableException;
import com.op.technicalcase.model.ExchangeRate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ExchangeRateServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    ExchangeRateService exchangeRateService;

    @Value("${rates.api}")
    private String ratesApiUrl;

    @Test()
    public void getRateFromApi_whenCurrencyNull_throwsCurrencyNullException(){
        Currency tryCurrency = null;
        Currency usdCurrency = null;

        Assertions.assertThrows(CurrencyNullException.class, () -> {
            BigDecimal rate = exchangeRateService.getRateFromApi(tryCurrency, usdCurrency);
        });
    }

    @Test
    public void getRateFromApi_whenCurrencyValid_returnCorrectValue() {
        Map<String, BigDecimal> rates = new HashMap<String, BigDecimal>();
        rates.put("TRY", new BigDecimal("6.45"));
        rates.put("EUR", new BigDecimal("0.91"));

        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setBase("USD");
        exchangeRate.setDate(LocalDate.of(2020, 3,18));
        exchangeRate.setRates(rates);

        Currency tryCurrency = Currency.getInstance("TRY");
        Currency usdCurrency = Currency.getInstance("USD");

        ReflectionTestUtils.setField(exchangeRateService, "ratesApiUrl", this.ratesApiUrl);

        Mockito.when(restTemplate.exchange(Mockito.any(String.class), Mockito.<HttpMethod> any(),
                                           Mockito.<HttpEntity<String>> any(), Mockito.<Class<ExchangeRate>> any()))
               .thenReturn(new ResponseEntity(exchangeRate, HttpStatus.OK));

        BigDecimal rate = exchangeRateService.getRateFromApi(usdCurrency, tryCurrency);
        Assertions.assertEquals(rate, exchangeRate.getRates().get("TRY"));
    }

    @Test
    public void getRateFromApi_whenExchangeRateServiceUnavailable_throwsExchangeRateServiceNotAvailableException() {
        Map<String, BigDecimal> rates = new HashMap<String, BigDecimal>();
        rates.put("TRY", new BigDecimal("6.45"));
        rates.put("EUR", new BigDecimal("0.91"));

        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setBase("USD");
        exchangeRate.setDate(LocalDate.of(2020, 3,18));
        exchangeRate.setRates(rates);

        Currency tryCurrency = Currency.getInstance("TRY");
        Currency usdCurrency = Currency.getInstance("USD");

        ReflectionTestUtils.setField(exchangeRateService, "ratesApiUrl", "https://api.ratesapi.io/api/latesssssst?base=%s&symbols=%s");

        Assertions.assertThrows(ExchangeRateServiceNotAvailableException.class, () -> {
            BigDecimal rate = exchangeRateService.getRateFromApi(tryCurrency, usdCurrency);
        });
    }

    @Test
    public void getRateFromApi_whenExchangeRateServiceReturnEmptyRateList_throwsExchangeRateNotFoundException() {
        Map<String, BigDecimal> rates = new HashMap<String, BigDecimal>();
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setBase("USD");
        exchangeRate.setDate(LocalDate.of(2020, 3,18));
        exchangeRate.setRates(rates);

        Currency tryCurrency = Currency.getInstance("TRY");
        Currency usdCurrency = Currency.getInstance("USD");

        ReflectionTestUtils.setField(exchangeRateService, "ratesApiUrl", this.ratesApiUrl);

        Mockito.when(restTemplate.exchange(Mockito.any(String.class), Mockito.<HttpMethod> any(),
                Mockito.<HttpEntity<String>> any(), Mockito.<Class<ExchangeRate>> any()))
                .thenReturn(new ResponseEntity(exchangeRate, HttpStatus.OK));

        Assertions.assertThrows(ExchangeRateNotFoundException.class, () -> {
            BigDecimal rate = exchangeRateService.getRateFromApi(tryCurrency, usdCurrency);
        });
    }

    @Test
    public void getRateFromApi_whenExchangeRateServiceReturnNull_throwsExchangeRateNotFoundException() {
        ExchangeRate exchangeRate = null;

        Currency tryCurrency = Currency.getInstance("TRY");
        Currency usdCurrency = Currency.getInstance("USD");

        ReflectionTestUtils.setField(exchangeRateService, "ratesApiUrl", this.ratesApiUrl);

        Mockito.when(restTemplate.exchange(Mockito.any(String.class), Mockito.<HttpMethod> any(),
                Mockito.<HttpEntity<String>> any(), Mockito.<Class<ExchangeRate>> any()))
                .thenReturn(new ResponseEntity(exchangeRate, HttpStatus.OK));

        Assertions.assertThrows(ExchangeRateNotFoundException.class, () -> {
            BigDecimal rate = exchangeRateService.getRateFromApi(tryCurrency, usdCurrency);
        });
    }
}
