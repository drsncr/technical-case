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

    private Currency tryCurrency;
    private Currency usdCurrency;

    private ExchangeRate exchangeRate;

    @BeforeEach
    public void init(){
        tryCurrency = Currency.getInstance("TRY");
        usdCurrency = Currency.getInstance("USD");

        exchangeRate = new ExchangeRate();
        exchangeRate.setBase("USD");
        exchangeRate.setDate(LocalDate.of(2020, 3,18));

        Map<String, BigDecimal> rates = new HashMap<String, BigDecimal>();
        rates.put("TRY", new BigDecimal("6.45"));
        rates.put("EUR", new BigDecimal("0.91"));
        exchangeRate.setRates(rates);
    }

    @Test()
    public void getRateFromApi_whenConversionInputNull_throwsCurrencyNullException(){
        Assertions.assertThrows(CurrencyNullException.class, () -> {
            BigDecimal rate = exchangeRateService.getRateFromApi(tryCurrency, null);
        });

        Assertions.assertThrows(CurrencyNullException.class, () -> {
            BigDecimal rate = exchangeRateService.getRateFromApi(null, usdCurrency);
        });

        Assertions.assertThrows(CurrencyNullException.class, () -> {
            BigDecimal rate = exchangeRateService.getRateFromApi(null, null);
        });
    }

    @Test
    public void getRateFromApi_whenCurrencyValid_returnCorrectValue() {
        ReflectionTestUtils.setField(exchangeRateService, "ratesApiUrl", ratesApiUrl);

        Mockito.when(restTemplate.exchange(Mockito.any(String.class), Mockito.<HttpMethod> any(),
                                           Mockito.<HttpEntity<String>> any(), Mockito.<Class<ExchangeRate>> any()))
               .thenReturn(new ResponseEntity(exchangeRate, HttpStatus.OK));

        BigDecimal rate = exchangeRateService.getRateFromApi(usdCurrency, tryCurrency);
        Assertions.assertEquals(rate, exchangeRate.getRates().get("TRY"));
    }

    @Test
    public void getRateFromApi_whenExchangeRateServiceUnavailable_throwsExchangeRateServiceNotAvailableException() {
        ReflectionTestUtils.setField(exchangeRateService, "ratesApiUrl", "https://api.ratesapi.io/api/latesssssst?base=%s&symbols=%s");

        Assertions.assertThrows(ExchangeRateServiceNotAvailableException.class, () -> {
            BigDecimal rate = exchangeRateService.getRateFromApi(tryCurrency, usdCurrency);
        });
    }

    @Test
    public void getRateFromApi_whenExchangeRateServiceReturnEmptyRateList_throwsExchangeRateNotFoundException() {
        exchangeRate.getRates().clear();
        ReflectionTestUtils.setField(exchangeRateService, "ratesApiUrl", ratesApiUrl);

        Mockito.when(restTemplate.exchange(Mockito.any(String.class), Mockito.<HttpMethod> any(),
                Mockito.<HttpEntity<String>> any(), Mockito.<Class<ExchangeRate>> any()))
                .thenReturn(new ResponseEntity(exchangeRate, HttpStatus.OK));

        Assertions.assertThrows(ExchangeRateNotFoundException.class, () -> {
            BigDecimal rate = exchangeRateService.getRateFromApi(tryCurrency, usdCurrency);
        });
    }

    @Test
    public void getRateFromApi_whenExchangeRateServiceReturnNull_throwsExchangeRateNotFoundException() {
        exchangeRate = null;

        ReflectionTestUtils.setField(exchangeRateService, "ratesApiUrl", ratesApiUrl);

        Mockito.when(restTemplate.exchange(Mockito.any(String.class), Mockito.<HttpMethod> any(),
                Mockito.<HttpEntity<String>> any(), Mockito.<Class<ExchangeRate>> any()))
                .thenReturn(new ResponseEntity(exchangeRate, HttpStatus.OK));

        Assertions.assertThrows(ExchangeRateNotFoundException.class, () -> {
            BigDecimal rate = exchangeRateService.getRateFromApi(tryCurrency, usdCurrency);
        });
    }
}
