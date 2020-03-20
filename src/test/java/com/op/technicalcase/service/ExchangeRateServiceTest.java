package com.op.technicalcase.service;

import com.op.technicalcase.exception.ExchangeRateNotFoundException;
import com.op.technicalcase.model.ExchangeRate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ExchangeRateServiceTest {

    @Mock
    ExchangeRateClient exchangeRateClient;

    @InjectMocks
    ExchangeRateService exchangeRateService;

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

    @Test
    public void getExchangeRate_whenCurrencyValid_returnCorrectValue() {
        Mockito.when(exchangeRateClient.getRateFromClient(Mockito.any(Currency.class), Mockito.any(Currency.class)))
               .thenReturn(exchangeRate);

        BigDecimal rate = exchangeRateService.getExchangeRate(usdCurrency, tryCurrency);
        Assertions.assertEquals(rate, exchangeRate.getRates().get("TRY"));
    }

    @Test
    public void getExchangeRate_whenExchangeRateServiceReturnEmptyRateList_throwsExchangeRateNotFoundException() {
        exchangeRate.getRates().clear();
        Mockito.when(exchangeRateClient.getRateFromClient(Mockito.any(Currency.class), Mockito.any(Currency.class)))
                .thenReturn(exchangeRate);

        Assertions.assertThrows(ExchangeRateNotFoundException.class, () -> {
            BigDecimal rate = exchangeRateService.getExchangeRate(tryCurrency, usdCurrency);
        });
    }

    @Test
    public void getExchangeRate_whenExchangeRateServiceReturnNull_throwsExchangeRateNotFoundException() {
        exchangeRate = null;
        Mockito.when(exchangeRateClient.getRateFromClient(Mockito.any(Currency.class), Mockito.any(Currency.class)))
                .thenReturn(exchangeRate);

        Assertions.assertThrows(ExchangeRateNotFoundException.class, () -> {
            BigDecimal rate = exchangeRateService.getExchangeRate(tryCurrency, usdCurrency);
        });
    }
}
